package com.example.webdevsummer1zhaohuang2018.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdevsummer1zhaohuang2018.models.Assignment;
import com.example.webdevsummer1zhaohuang2018.models.Lesson;
import com.example.webdevsummer1zhaohuang2018.models.Widget;
import com.example.webdevsummer1zhaohuang2018.repositories.AssignmentRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.LessonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentServices {

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@GetMapping("/api/assignment")
	public List<Assignment> findAllLessons()
	{
		return (List<Assignment>) assignmentRepository.findAll();
	}
	
	@GetMapping("/api/assignment/{assignmentId}")
	public Assignment getLessonById(@PathVariable("assignmentId") int id, HttpServletResponse response) {
		Optional<Assignment> assignment = assignmentRepository.findById(id);
		if(assignment.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return assignment.get(); 
	}

	@DeleteMapping("/api/assignment/{assignmentId}")
	public String deleteAssignmentById(@PathVariable("assignmentId") int id, HttpServletResponse response) {
		Optional<Assignment> assignment = assignmentRepository.findById(id);
		if(assignment.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A Assignment does not exist with that id\"}";
		}
		assignmentRepository.deleteById(id);
		return "{\"success\":\"Assignment Deleted\"}";
	}
	
	@GetMapping("/api/lesson/{lessonId}/assignment")
	public List<Widget> findAllAssignmentsForLesson(
			@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> lData = lessonRepository.findById(lessonId);
		if(lData.isPresent()) {		
			List<Widget> toReturn = lData.get().getWidgets();
			List<Widget> answerArray = new ArrayList<Widget>();
			for(Widget widgetItem: toReturn) {
				if (widgetItem.getClassName() == "assignment") {
					answerArray.add(widgetItem);
				}
			}
			Collections.sort(toReturn);
			return answerArray;							
		}
		
		return null;
	}
	
	@PostMapping("/api/lesson/{lessonId}/assignment")
	public String createAssignment(
			@PathVariable("lessonId") int lessonId,
			@RequestBody Assignment assignment , HttpServletResponse response ) {
		Optional<Lesson> lData = lessonRepository.findById(lessonId);
		if(lData.isPresent()) {		
			Lesson lesson = lData.get();
			assignment.setLesson(lesson);
			assignmentRepository.save(assignment);
			return"{\"Success\":\"Assignment Created!\"}" ;
				
			}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Lesson With That Id Not Found\"}";		
	}
	
}
