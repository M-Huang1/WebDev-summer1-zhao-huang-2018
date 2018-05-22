package com.example.webdevsummer1zhaohuang2018.services;

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
import com.example.webdevsummer1zhaohuang2018.models.Module;
import com.example.webdevsummer1zhaohuang2018.repositories.CourseRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.ModuleRepository;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleServices {
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ModuleRepository moduleRepository;
	
	@PostMapping("/api/course/{courseId}/module")
	public String createModule(
			@PathVariable("courseId") int courseId,
			@RequestBody Module newModule, HttpServletResponse response) {
	
		Optional<Course> data = courseRepository.findById(courseId);
		
		if(data.isPresent()) {
			Course course = data.get();
			course.setModified(new Date());
			courseRepository.save(course);
			newModule.setCourse(course);
			
			moduleRepository.save(newModule);
			return"{\"Success\":\"Module Created!\"}" ;
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Course Not Found\"}";		
	}
	
	@GetMapping("/api/course/{courseId}/module")
	public List<Module> findAllModulesForCourse(
			@PathVariable("courseId") int courseId) {
		Optional<Course> data = courseRepository.findById(courseId);
		if(data.isPresent()) {
			Course course = data.get();
			return course.getModules();
		}
		return null;		
	}
	
	@DeleteMapping("/api/module/{moduleId}")
	public String deleteModule(@PathVariable("moduleId") int id, HttpServletResponse response) {
		Optional<Module> module = moduleRepository.findById(id);
		if(module.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A module does not exist with that id\"}";
		}
		moduleRepository.deleteById(id);
		return"{\"Success\":\"Module Deleted\"}"; 
	}
	
	@GetMapping("/api/module/{moduleId}")
	public Module getModuleById(@PathVariable("moduleId") int id, HttpServletResponse response) {
		Optional<Module> module = moduleRepository.findById(id);
		if(module.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return module.get(); 
	}
	
	@PutMapping("/api/module/{moduleId}")
	public String updateModule(@RequestBody Module module, @PathVariable("moduleId") int id, HttpServletResponse response) {
		Optional<Module> moduleData = moduleRepository.findById(id);
		if(moduleData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Module With That Id Not Found\"}";
		}
		module.setId(id);
		moduleRepository.save(module);
		return"{\"Success\":\"Module Updated\"}";
	}
	@GetMapping("/api/module")
	public List<Module> findAllModules()
	{
		return (List<Module>) moduleRepository.findAll();
	}
}
