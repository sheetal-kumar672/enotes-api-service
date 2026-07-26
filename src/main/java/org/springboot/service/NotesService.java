package org.springboot.service;

import java.util.List;

import org.springboot.dto.NotesDto;
import org.springboot.entity.FileDetails;
import org.springframework.web.multipart.MultipartFile;

public interface NotesService {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDto> getAllNotes();

	public byte[] downloadFile(FileDetails fileDetails) throws Exception;

	public FileDetails getFileDetails(Integer id) throws Exception;



}
