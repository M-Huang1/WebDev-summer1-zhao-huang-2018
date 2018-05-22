package com.example.webdevsummer1zhaohuang2018.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.webdevsummer1zhaohuang2018.models.Course;


public interface CourseRepository
extends CrudRepository<Course, Integer> {
	
	@Query("SELECT u FROM Course u WHERE u.title=:title")
	Optional<Course> findCourseByTitle(
			@Param("title") String title);

	

	
}
