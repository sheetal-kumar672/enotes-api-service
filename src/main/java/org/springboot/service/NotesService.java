package org.springboot.service;

import java.util.List;

import org.springboot.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();



}
