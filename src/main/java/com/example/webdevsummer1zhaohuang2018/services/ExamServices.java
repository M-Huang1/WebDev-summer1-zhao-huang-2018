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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdevsummer1zhaohuang2018.models.Assignment;
import com.example.webdevsummer1zhaohuang2018.models.Exam;
import com.example.webdevsummer1zhaohuang2018.models.Lesson;
import com.example.webdevsummer1zhaohuang2018.models.Widget;
import com.example.webdevsummer1zhaohuang2018.repositories.ExamRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.LessonRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ExamServices {

	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@GetMapping("/api/exam")
	public List<Exam> findAllExams()
	{
		return (List<Exam>) examRepository.findAll();
	}
	
	@GetMapping("/api/exam/{examId}")
	public Exam getExamById(@PathVariable("examId") int id, HttpServletResponse response) {
		Optional<Exam> exam = examRepository.findById(id);
		if(exam.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return exam.get(); 
	}

	@DeleteMapping("/api/exam/{examId}")
	public String deleteExamById(@PathVariable("examId") int id, HttpServletResponse response) {
		Optional<Exam> exam = examRepository.findById(id);
		if(exam.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A Exam does not exist with that id\"}";
		}
		examRepository.deleteById(id);
		return "{\"success\":\"Exam Deleted\"}";
	}
	@PutMapping("/api/exam/{examId}")
	public String updateExam(@RequestBody Exam exam, @PathVariable("examId") int id, HttpServletResponse response) {
		Optional<Exam> examData = examRepository.findById(id);
		if(examData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Exam With That Id Not Found\"}";
		}
		exam.setId(id);
		exam.setLesson(examData.get().getLesson());
		exam.setQuestions(examData.get().getQuestions());
		examRepository.save(exam);
		return"{\"Success\":\"Exam Updated\"}";
	}
	
	@GetMapping("/api/lesson/{lessonId}/exam")
	public List<Widget> findAllExamsForLesson(
			@PathVariable("lessonId") int lessonId) {
		Optional<Lesson> lData = lessonRepository.findById(lessonId);
		if(lData.isPresent()) {		
			List<Widget> toReturn = lData.get().getWidgets();
			List<Widget> answerArray = new ArrayList<Widget>();
			for(Widget widgetItem: toReturn) {
				if (widgetItem.getClassName() == "exam") {
					answerArray.add(widgetItem);
				}
			}
			Collections.sort(toReturn);
			return answerArray;								
		}
		
		return null;
	}
	
	@PostMapping("/api/lesson/{lessonId}/exam")
	public String createExam(
			@PathVariable("lessonId") int lessonId,
			@RequestBody Exam exam , HttpServletResponse response ) {
		Optional<Lesson> lData = lessonRepository.findById(lessonId);
		if(lData.isPresent()) {		
			Lesson lesson = lData.get();
			exam.setLesson(lesson);
			examRepository.save(exam);
			return"{\"Success\":\"Exam Created!\"}" ;
				
			}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Lesson With That Id Not Found\"}";		
	}
	
}
