package org.springboot.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springboot.dto.FavoriteNoteDto;
import org.springboot.dto.NotesDto;
import org.springboot.dto.NotesDto.FileDto;
import org.springboot.dto.NotesResponse;
import org.springboot.entity.Category;
import org.springboot.entity.FavoriteNote;
import org.springboot.entity.FileDetails;
import org.springboot.entity.Notes;
import org.springboot.exception.ResourceNotFoundException;
import org.springboot.repository.CategoryRepository;
import org.springboot.repository.FavoriteNoteRepository;
import org.springboot.repository.FileRepository;
import org.springboot.repository.NotesRepository;
import org.springboot.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private FavoriteNoteRepository favouriteNoteRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Value("${file.upload.path}")
	private String uploadpath;
	
	@Autowired
	private FileRepository fileRepo;

	@Override
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
		
		ObjectMapper ob = new ObjectMapper();
		NotesDto notesDto = ob.readValue(notes, NotesDto.class);
		
		notesDto.setIsDeleted(false);
		notesDto.setDeletedOn(null);
        
		// update notes if id is given in request
        if(!ObjectUtils.isEmpty(notesDto.getId()))
        {
        	updateNotes(notesDto,file);
        }
		
		
		//category validation 
		
		checkCategoryExist(notesDto.getCategory());
		
		Notes notesMap = mapper.map(notesDto, Notes.class);
		
		FileDetails fileDetails = saveFileDetails(file);
		
		if(!ObjectUtils.isEmpty(fileDetails))	
		{
			notesMap.setFileDetails(fileDetails);
		}
		else
		{
			 if(ObjectUtils.isEmpty(notesDto.getId()))
		        {
				 notesMap.setFileDetails(null);
		        }
		}
		
		Notes saveNotes = notesRepo.save(notesMap);
		if(!ObjectUtils.isEmpty(saveNotes))
		{
			return true;
		}
		
		return false;
	}

	private void updateNotes(NotesDto notesDto, MultipartFile file) throws Exception {
		
		Notes existNotes = notesRepo.findById(notesDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Invalid Notes Id"));
		
		
		// user not choose any file at update time 
		if(ObjectUtils.isEmpty(file))
		{
			notesDto.setFileDetails(mapper.map(existNotes.getFileDetails(),FileDto.class));
		}
		
	}

	private FileDetails saveFileDetails(MultipartFile file) throws IOException {
		
		if(!ObjectUtils.isEmpty(file) && !file.isEmpty())
		{
			
			
			String originalFileName = file.getOriginalFilename();
			
			
			
			String rndString = UUID.randomUUID().toString();
			String extension = FilenameUtils.getExtension(originalFileName);
			String uploadfileName = rndString +"."+ extension;
			
			
			
			File saveFile = new File(uploadpath);
				if(!saveFile.exists())
				{
					saveFile.mkdir();
				}
				// path : enotesapiservice/notes/java.pdf
				String storePath = uploadpath.concat(uploadfileName);
				
				
				
				// upload file
				long upload = Files.copy(file.getInputStream(),Paths.get(storePath));
			    if(upload!=0)
			    {
			    	FileDetails fileDetails = new FileDetails();
			    	fileDetails.setOriginalFileName(originalFileName);
					fileDetails.setDisplayFileName(getDisplayName(originalFileName));
					fileDetails.setUploadFileName(uploadfileName);
					fileDetails.setFileSize(file.getSize());
					fileDetails.setPath(storePath);
			       FileDetails saveFileDetails = fileRepo.save(fileDetails);
			       return saveFileDetails;
			    }
		}
		
		return null;
	}


	private String getDisplayName(String originalFileName) {
		// java_programming_tutorials.pdf
		
		String extension = FilenameUtils.getExtension(originalFileName);
		String fileName = FilenameUtils.removeExtension(originalFileName);
		
		if(fileName.length()>8)
		{
			fileName = fileName.substring(0, 7);
		}
		fileName = fileName + "." + extension;
		return fileName;
	
	}

	private void checkCategoryExist(Category category) throws Exception {
		
		categoryRepo.findById(category.getId()).orElseThrow(()->new ResourceNotFoundException("category id invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream().map(note->mapper.map(note, NotesDto.class)).toList();
		
	}

	@Override
	public byte[] downloadFile(FileDetails fileDetails) throws Exception {
		
	    InputStream io = new FileInputStream( fileDetails.getPath());
	    
	    return  StreamUtils.copyToByteArray(io);
	}

	@Override
	public FileDetails getFileDetails(Integer id) throws Exception {
		
		FileDetails fileDetails  =fileRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("File is not avilable"));
		
		return fileDetails;
	}

	@Override
	public NotesResponse getAllNotesByUser(Integer userId, Integer pageNo, Integer pageSize) {
		
		Pageable pageable= PageRequest.of(pageNo, pageSize);
		
		Page<Notes> pageNotes= notesRepo.findByCreatedByAndIsDeletedFalse(userId, pageable);
		
	    List<NotesDto>	notesDto = pageNotes.get().map(n ->mapper.map(n, NotesDto.class)).toList();
		
		NotesResponse notes = NotesResponse.builder()
				.notes(notesDto)
				.pageNo(pageNotes.getNumber())
				.pageSize(pageNotes.getSize())
				.totalElements(pageNotes.getTotalElements())
				.totalPages(pageNotes.getTotalPages())
				.isFirst(pageNotes.isFirst())
				.isLast(pageNotes.isLast())
		         .build();
		
		return notes;
	}

	@Override
	public void softDeletenotes(Integer id) throws Exception {
		
		Notes notes = notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
		notes.setIsDeleted(true);
		notes.setDeletedOn(LocalDateTime.now());
		notesRepo.save(notes);
		
	}

	@Override
	public void restorenotes(Integer id) throws Exception {
		
		Notes notes = notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes id invalid ! Not Found"));
		
		notes.setIsDeleted(false);
		notes.setDeletedOn(null);
		notesRepo.save(notes);
	}

	@Override
	public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		List<NotesDto> noteDtoList = recycleNotes.stream().map(note->mapper.map(note, NotesDto.class)).toList();
		return noteDtoList;
	}

	@Override
	public void hardDeletenotes(Integer id) throws Exception {
		Notes notes = notesRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Notes not found"));
		
		if(notes.getIsDeleted())
		{
			notesRepo.delete(notes);
		}
		else
		{
			throw new IllegalArgumentException("Sorry You cant hard delete Directly");
		}
	}

	@Override
	public void emptyRecycleBin(int userId) {
		
		List<Notes> recycleNotes = notesRepo.findByCreatedByAndIsDeletedTrue(userId);
		if(!CollectionUtils.isEmpty(recycleNotes))
		{
			notesRepo.deleteAll(recycleNotes);
		}
	}

	@Override
	public void favoriteNote(Integer noteId) throws Exception {
		int userId = 1;
		Notes notes = notesRepo.findById(noteId).orElseThrow(()-> new ResourceNotFoundException("Notes Not found & Id invalid "));
		
		FavoriteNote favoriteNote = FavoriteNote.builder()
				.note(notes)
				.userId(userId)
				.build();
		
		favouriteNoteRepo.save(favoriteNote);
	}

	@Override
	public void unFavoriteNote(Integer favoriteNoteId) throws Exception  {
	 
		FavoriteNote favNote = favouriteNoteRepo.findById(favoriteNoteId)
				.orElseThrow(()-> new ResourceNotFoundException("Favourite Note Not found & Id invalid "));
		
		favouriteNoteRepo.delete(favNote);
	}

	@Override
	public List<FavoriteNoteDto> getUserFavoriteNotes() throws Exception {
		
		int userId = 1;
		
		List<FavoriteNote> favoriteNotes = favouriteNoteRepo.findByUserId(userId);
		return favoriteNotes.stream().map(fn->mapper.map(fn, FavoriteNoteDto.class)).toList();
		
	}

	@Override
	public Boolean copyNotes(Integer id) throws Exception {
		
		Notes notes = notesRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notes Not found ! Id invalid "));
		
		Notes copyNote = Notes.builder()
				.title(notes.getTitle())
				.description(notes.getDescription())
				.category(notes.getCategory())
				.isDeleted(false)
				.fileDetails(null)
				.build();
		
		// TODO : Need to check Validation
		Notes saveCopyNote = notesRepo.save(copyNote);
		
		if(!ObjectUtils.isEmpty(saveCopyNote))
		{
			return true;
		}
		return false;
		
	}

}
	
	


