package com.example.webdevsummer1zhaohuang2018.repositories;

import org.springframework.data.repository.query.Param;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.webdevsummer1zhaohuang2018.models.User;


public interface UserRepository
	extends CrudRepository<User, Integer>{
	@Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
	Iterable<User> findUserByCredentials(
		@Param("username") String username, 
		@Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.username=:username")
	Optional<User> findUserByUsername(
		@Param("username") String username); 
	
	@Query("SELECT u FROM User u WHERE u.email=:email")
	Optional<User> findUserByEmail(
		@Param("email") String email); 
}
