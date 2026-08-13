package org.springboot.controller;

import java.util.List;

import org.springboot.dto.FavoriteNoteDto;
import org.springboot.dto.NotesDto;
import org.springboot.dto.NotesResponse;
import org.springboot.endpoint.NotesEndpoint;
import org.springboot.entity.FileDetails;
import org.springboot.service.NotesService;
import org.springboot.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class NotesController implements NotesEndpoint {

	@Autowired
	private NotesService notesService;
	
	@Override
	public ResponseEntity<?> saveNotes(String notes,MultipartFile file) throws Exception
	{
		Boolean saveNotes = notesService.saveNotes(notes,file);
		if(saveNotes)
		{
			return CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<?> downloadFile(Integer id) throws Exception
	{
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data =notesService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment", fileDetails.getOriginalFileName());
		
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	@Override
	public ResponseEntity<?> getAllNotes()
	{
		List<NotesDto> notes = notesService.getAllNotes();
		if(CollectionUtils.isEmpty(notes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getAllNotesByUser(
			Integer pageNo,
			Integer pageSize)
	{
		NotesResponse notes = notesService.getAllNotesByUser( pageNo,pageSize);
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteNotes(Integer id) throws Exception
	{
		notesService.softDeletenotes(id);
		return CommonUtil.createBuildResponse("Delete success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> restoreNotes(Integer id) throws Exception
	{
		notesService.restorenotes(id);
		return CommonUtil.createBuildResponse("Notes restore success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception
	{
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		
		if(CollectionUtils.isEmpty(notes))
		{
			return CommonUtil.createBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
		}
		
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> hardDeleteNotes(Integer id) throws Exception
	{
		notesService.hardDeletenotes(id);
		return CommonUtil.createBuildResponse("Delete success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception
	{
		notesService.emptyRecycleBin();
		return CommonUtil.createBuildResponse("Delete success", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> favoriteNote(Integer noteId) throws Exception
	{
		notesService.favoriteNote(noteId);
		return CommonUtil.createBuildResponse("Notes added Favorite", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> unFavoriteNote(Integer favNoteId) throws Exception
	{
		notesService.unFavoriteNote(favNoteId);
		return CommonUtil.createBuildResponseMessage("Remove Favorite", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getUserFavoriteNote() throws Exception
	{
		List<FavoriteNoteDto> userFavoriteNotes = notesService.getUserFavoriteNotes();
		if(CollectionUtils.isEmpty(userFavoriteNotes))
		{
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(userFavoriteNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> copyNotes(Integer id) throws Exception
	{
		Boolean copyNotes = notesService.copyNotes(id);
		if(copyNotes)
		{
			return CommonUtil.createBuildResponseMessage("Copied success", HttpStatus.CREATED);
		}
		
		return CommonUtil.createErrorResponseMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<?> searchNotes(String key,
			Integer pageNo,
			Integer pageSize)
	{
		NotesResponse notes = notesService.getAllNotesByUserSearch( pageNo,pageSize,key);
		return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
}
