package com.example.webdevsummer1zhaohuang2018.services;

import java.util.ArrayList;
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

import com.example.webdevsummer1zhaohuang2018.repositories.CourseRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.LessonRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.ModuleRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.WidgetRepository;
import com.example.webdevsummer1zhaohuang2018.models.Course;
import com.example.webdevsummer1zhaohuang2018.models.Lesson;
import com.example.webdevsummer1zhaohuang2018.models.Module;
import com.example.webdevsummer1zhaohuang2018.models.Widget;

@RestController
@CrossOrigin(origins="*", maxAge = 3600)
public class WidgetService {
	
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ModuleRepository moduleRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	WidgetRepository repository;
	
	
	
	
	@GetMapping("/api/widget/{widgetId}")
	public Widget getWidgetById(@PathVariable("widgetId") int id, HttpServletResponse response) {
		Optional<Widget> widget = repository.findById(id);
		if(widget.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return widget.get();
		
	}
	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets(){
		return (List<Widget>) repository.findAll();
	}
		
	@GetMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/widget")
	public List<Widget> findAllWidgetsForLesson(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@PathVariable("lessonId") int lessonId) {
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {		
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Optional<Lesson> lData = lessonRepository.findById(lessonId);
				if(lData.isPresent()) {
					List<Widget> toReturn = lData.get().getWidgets();
					Collections.sort(toReturn);
					int index = 1;
					while(index < toReturn.size() + 1) {
						toReturn.get(index - 1).setWidgetOrder(index);
						repository.save(toReturn.get(index - 1));
						index += 1;
					}
					return lData.get().getWidgets();
				}
				return null;
				
				
			}
			return null;
		}
		
		return null;		
	}
	

	@PostMapping("/api/widget/lesson/{lessonId}/save")
	public String saveWidgetList(
			@PathVariable("lessonId") int lessonId,
			@RequestBody List<Widget> widgets) {
		
		Optional<Lesson> lData = lessonRepository.findById(lessonId);
		if(lData.isPresent()) {
			Lesson widgetLesson = lData.get();
			Optional<List<Widget>> existingWidgets = repository.findWidgetByLesson(lessonId);
			if(existingWidgets.isPresent()) {
				for (Widget widget: existingWidgets.get()) {
					boolean found = false;
					for (Widget widget1: widgets) {
						if(widget1.getId() == widget.getId()) {
							found = true;
							break;
						}
					}
					if (found == false) {
						repository.deleteById(widget.getId());
					}
				}
			}
			for (Widget widget: widgets) {
				widget.setLesson(widgetLesson);
				repository.save(widget);
			}
		}
		return"{\"Success\":\"Widgets Saved!\"}";
	}
	@PostMapping("/api/course/{courseId}/module/{moduleId}/lesson/{lessonId}/widget")
	public String createWidget(
			@PathVariable("courseId") int courseId,
			@PathVariable("moduleId") int moduleId,
			@PathVariable("lessonId") int lessonId,
			@RequestBody Widget widget, HttpServletResponse response ) {
		Optional<Course> cData = courseRepository.findById(courseId);
		if(cData.isPresent()) {		
			Optional<Module> mData = moduleRepository.findById(moduleId);
			if(mData.isPresent()) {
				Optional<Lesson> lData = lessonRepository.findById(lessonId);
					if(lData.isPresent()) {
						Course course = cData.get();
						course.setModified(new Date());
						courseRepository.save(course);
						widget.setLesson(lData.get());
						repository.save(widget);
						
						return"{\"Success\":\"Widget Created!\"}" ;
					}
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					return "{\"Error\":\"Lesson With That Id Not Found\"}";	
				
			}
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"Error\":\"Module With That Id Not Found\"}";
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Course With That Id Not Found\"}";		
	}
	
	@PutMapping("/api/widget/{widgetId}")
	public String updateWidget(@RequestBody Widget widget, @PathVariable("widgetId") int id, HttpServletResponse response) {
		Optional<Widget> widgetData = repository.findById(id);
		if(widgetData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Widget With That Id Not Found\"}";
		}
		widget.setId(id);
		repository.save(widget);
		return"{\"Success\":\"Widget Updated\"}";
	}
	
	@DeleteMapping("/api/widget/{widgetId}")
	public String deleteWidget(@PathVariable("widgetId") int id, HttpServletResponse response) {
		Optional<Widget> widgetData = repository.findById(id);
		if(widgetData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Widget With That Id Not Found\"}";
		}
		repository.deleteById(id);
		return"{\"Success\":\"Widget Deleted\"}";
	}
}


