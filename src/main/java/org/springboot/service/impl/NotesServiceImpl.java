package org.springboot.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springboot.dto.NotesDto;
import org.springboot.entity.Category;
import org.springboot.entity.Notes;
import org.springboot.exception.ResourceNotFoundException;
import org.springboot.repository.CategoryRepository;
import org.springboot.repository.NotesRepository;
import org.springboot.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class NotesServiceImpl implements NotesService{
	
	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Boolean saveNotes(NotesDto notesDto) throws Exception {
		
		//category validation 
		
		checkCategoryExist(notesDto.getCategory());
		
		Notes notes = mapper.map(notesDto, Notes.class);
		
		Notes saveNotes = notesRepo.save(notes);
		if(!ObjectUtils.isEmpty(saveNotes))
		{
			return true;
		}
		
		return false;
	}

	private void checkCategoryExist(Category category) throws Exception {
		
		categoryRepo.findById(category.getId()).orElseThrow(()->new ResourceNotFoundException("category id invalid"));
		
	}

	@Override
	public List<NotesDto> getAllNotes() {
		
		return notesRepo.findAll().stream().map(note->mapper.map(note, NotesDto.class)).toList();
		
	}
	
	

}
