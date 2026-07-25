package org.springboot.service;

import java.util.List;

import org.springboot.dto.NotesDto;

public interface NotesService {
	
	public Boolean saveNotes(NotesDto notesDto) throws Exception;
	
	public List<NotesDto> getAllNotes();

}
