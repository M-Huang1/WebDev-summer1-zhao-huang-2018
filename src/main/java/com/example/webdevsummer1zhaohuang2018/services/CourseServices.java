package com.example.webdevsummer1zhaohuang2018.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.webdevsummer1zhaohuang2018.models.Course;
import com.example.webdevsummer1zhaohuang2018.repositories.CourseRepository;

public class CourseServices {
	
	@Autowired
	CourseRepository courseRepository;
	
	@GetMapping("/api/course")
	public List<Course> findAllCourses() {
		return (List<Course>) courseRepository.findAll(); 
	}
	
	@PostMapping("/api/course")
	@ResponseBody
	public String createCourse(@RequestBody Course course, HttpServletResponse response) {
		Optional<Course> duplicateTitle = this.courseRepository.findCourseByTitle(course.getTitle());
		if(duplicateTitle.isPresent()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return "{\"error\":\"A course already exists with that title\"}";
		}
		courseRepository.save(course);
		return"{\"Success\":\"User Created!\"}" ;
		
		
	}
	
	@PutMapping("/api/course")
	@ResponseBody
	public String updateCourse(@RequestBody Course course, HttpServletResponse response) {
		Optional<Course> foundCourse = this.courseRepository.findCourseByTitle(course.getTitle());
		if(foundCourse.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A course does not exist with that id\"}";
		}
		courseRepository.save(course);
		return"{\"Success\":\"User Updated!\"}" ;
		
		
	}
	
	@GetMapping("/api/course/{Id}")
	@ResponseBody
	public Course findCourseById(@PathVariable("Id") int id, HttpServletResponse response) {
		Optional<Course> course = courseRepository.findById(id);
		if(course.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return course.get();
	}
	
	@DeleteMapping("/api/course/{Id}")
	@ResponseBody
	public String deleteCourse(@PathVariable("Id") int id, HttpServletResponse response) {
		Optional<Course> course = courseRepository.findById(id);
		if(course.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A course does not exist with that id\"}";
		}
		courseRepository.deleteById(id);
		return"{\"Success\":\"User Deleted\"}"; 
	}

}
