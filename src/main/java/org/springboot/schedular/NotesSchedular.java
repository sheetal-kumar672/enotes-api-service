package org.springboot.schedular;

import java.time.LocalDateTime;
import java.util.List;

import org.springboot.entity.Notes;
import org.springboot.repository.NotesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotesSchedular {
	
	private NotesRepository notesRepo;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNotesSchedular()
	{
	  LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
	 List<Notes> deletedNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
	 notesRepo.deleteAll(deletedNotes);
	}

}
