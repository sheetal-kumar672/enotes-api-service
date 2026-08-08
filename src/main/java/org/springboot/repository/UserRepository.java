package org.springboot.repository;

import org.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

	Boolean existsByEmail(String email);

	User findByEmail(String username);

}
