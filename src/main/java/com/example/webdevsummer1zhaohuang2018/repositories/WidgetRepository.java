package com.example.webdevsummer1zhaohuang2018.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.webdevsummer1zhaohuang2018.models.Lesson;

import com.example.webdevsummer1zhaohuang2018.models.Widget;

public interface WidgetRepository
extends CrudRepository<Widget, Integer>{
	
	@Query("SELECT u FROM Widget u WHERE u.name=:name AND u.lesson.id=:lessonId")
	Optional<Widget> findWidgetByNameAndLesson(
		@Param("name") String name, 
		@Param("lessonId") int lessonId);
	
	@Query("SELECT u FROM Widget u WHERE u.lesson.id=:lessonId")
	Optional<List<Widget>> findWidgetByLesson( 
		@Param("lessonId") int lessonId);
}
