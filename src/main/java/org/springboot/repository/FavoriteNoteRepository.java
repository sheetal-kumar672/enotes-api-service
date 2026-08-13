package org.springboot.repository;

import java.util.List;

import org.springboot.entity.FavoriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteNoteRepository extends JpaRepository<FavoriteNote, Integer> {

	List<FavoriteNote> findByUserId(int userId);

}
