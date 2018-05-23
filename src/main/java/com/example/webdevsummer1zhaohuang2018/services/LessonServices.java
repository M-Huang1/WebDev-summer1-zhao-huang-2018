package com.example.webdevsummer1zhaohuang2018.services;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdevsummer1zhaohuang2018.models.Course;
import com.example.webdevsummer1zhaohuang2018.models.Lesson;
import com.example.webdevsummer1zhaohuang2018.models.Module;
import com.example.webdevsummer1zhaohuang2018.repositories.CourseRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.LessonRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonServices {
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@GetMapping("/api/lesson")
	public List<Lesson> findAllLessons()
	{
		return (List<Lesson>) lessonRepository.findAll();
	}
	
	@GetMapping("/api/lesson/{lessonId}")
	public Lesson getLessonById(@PathVariable("lessonId") int id, HttpServletResponse response) {
		Optional<Lesson> lesson = lessonRepository.findById(id);
		if(lesson.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return lesson.get(); 
	}

	@DeleteMapping("/api/lesson/{lessonId}")
	public String deleteLessonById(@PathVariable("lessonId") int id, HttpServletResponse response) {
		Optional<Lesson> lesson = lessonRepository.findById(id);
		if(lesson.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A Lesson does not exist with that id\"}";
		}
		lessonRepository.deleteById(id);
		return "{\"success\":\"Lesson Deleted\"}";
	}
	
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public List<Lesson> findAllLessonsForModule(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId) {
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {		
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				List<Lesson> toReturn = mData.get().getLessons();
				Collections.sort(toReturn);
				return toReturn;
				
			}
			return null;
		}
		
		return null;		
	}
	
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson")
	public String createLesson(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@RequestBody Lesson lesson, HttpServletResponse response ) {
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {		
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Course course = cData.get();
				course.setModified(new Date());
				courseRepository.save(course);
				mData.get().setCourse(course);
				moduleRepository.save(mData.get());
				
				lesson.setModule(mData.get());
				lessonRepository.save(lesson);
				return"{\"Success\":\"Lesson Created!\"}" ;
				
			}
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"Error\":\"Module With That Id Not Found\"}";
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Course With That Id Not Found\"}";		
	}
	
	@PutMapping("/api/lesson/{lessonId}")
	public String updateLesson(@RequestBody Lesson lesson, @PathVariable("lessonId") int id, HttpServletResponse response) {
		Optional<Lesson> lessonData = lessonRepository.findById(id);
		if(lessonData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Lesson With That Id Not Found\"}";
		}
		lesson.setId(id);
		lessonRepository.save(lesson);
		return"{\"Success\":\"Lesson Updated\"}";
	}
	
}
