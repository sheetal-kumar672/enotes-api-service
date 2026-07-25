package org.springboot.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springboot.dto.NotesDto;
import org.springboot.entity.Category;
import org.springboot.entity.FileDetails;
import org.springboot.entity.Notes;
import org.springboot.exception.ResourceNotFoundException;
import org.springboot.repository.CategoryRepository;
import org.springboot.repository.FileRepository;
import org.springboot.repository.NotesRepository;
import org.springboot.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import tools.jackson.databind.ObjectMapper;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository notesRepo;
	
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
			notesMap.setFileDetails(fileDetails);
		}
		
		Notes saveNotes = notesRepo.save(notesMap);
		if(!ObjectUtils.isEmpty(saveNotes))
		{
			return true;
		}
		
		return false;
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

}
	
	


