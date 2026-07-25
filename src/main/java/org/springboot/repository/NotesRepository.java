package org.springboot.repository;

import org.springboot.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

}
