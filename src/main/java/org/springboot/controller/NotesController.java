package org.springboot.controller;

import java.util.List;

import org.springboot.dto.FavoriteNoteDto;
import org.springboot.dto.NotesDto;
import org.springboot.dto.NotesResponse;
import org.springboot.entity.FavoriteNote;
import org.springboot.entity.FileDetails;
import org.springboot.service.NotesService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notes")

public class NotesController {

	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file) throws Exception
	{
		Boolean saveNotes = notesService.saveNotes(notes,file);
		if(saveNotes)
		{
			return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception
	{
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data =notesService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes()
	{
		List<NotesDto> notes = notesService.getAllNotes();
		if(CollectionUtils.isEmpty(notes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/user-notes")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name = "pageNo",defaultValue = "0") Integer pageNo,
			@RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize)
	{
		Integer UserId=1;
		NotesResponse notes = notesService.getAllNotesByUser(UserId, pageNo,pageSize);
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception
	{
		notesService.softDeletenotes(id);
		return CommonUtil.createBuildResponse("Delete success", HttpStatus.OK);
	}
	
	@GetMapping("/restore/{id}")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception
	{
		notesService.restorenotes(id);
		return CommonUtil.createBuildResponse("Notes restore success", HttpStatus.OK);
	}
	
	@GetMapping("/recycle-bin")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception
	{
		Integer userId = 1;
		List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
		
		if(CollectionUtils.isEmpty(notes))
		{
			return CommonUtil.createBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception
	{
		notesService.hardDeletenotes(id);
		return CommonUtil.createBuildResponse("Delete success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> emptyRecycleBin() throws Exception
	{
		int userId = 1;
		notesService.emptyRecycleBin(userId);
		return CommonUtil.createBuildResponse("Delete success", HttpStatus.OK);
	}
	
	@GetMapping("/fav/{noteId}")
	public ResponseEntity<?> favoriteNote(@PathVariable Integer noteId) throws Exception
	{
		notesService.favoriteNote(noteId);
		return CommonUtil.createBuildResponse("Notes added Favorite", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/un-fav/{favNoteId}")
	public ResponseEntity<?> unFavoriteNote(@PathVariable Integer favNoteId) throws Exception
	{
		notesService.unFavoriteNote(favNoteId);
		return CommonUtil.createBuildResponseMessage("Remove Favorite", HttpStatus.OK);
	}
	
	@GetMapping("/fav-note")
	public ResponseEntity<?> getUserFavoriteNote() throws Exception
	{
		List<FavoriteNoteDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
		if(CollectionUtils.isEmpty(userFavoriteNotes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(userFavoriteNotes, HttpStatus.OK);
	}
	
}
