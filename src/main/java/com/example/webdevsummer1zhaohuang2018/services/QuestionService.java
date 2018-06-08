package com.example.webdevsummer1zhaohuang2018.services;


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
import com.example.webdevsummer1zhaohuang2018.models.BaseExamQuestion;
import com.example.webdevsummer1zhaohuang2018.models.EssayExamQuestion;
import com.example.webdevsummer1zhaohuang2018.models.Exam;
import com.example.webdevsummer1zhaohuang2018.models.FillInTheBlanksExamQuestion;
import com.example.webdevsummer1zhaohuang2018.models.MultipleChoiceExamQuestion;
import com.example.webdevsummer1zhaohuang2018.models.TrueOrFalseExamQuestion;
import com.example.webdevsummer1zhaohuang2018.repositories.BaseExamQuestionRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.EssayExamQuestionRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.ExamRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.FillInTheBlanksExamQuestionRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.MultipleChoiceExamQuestionRepository;
import com.example.webdevsummer1zhaohuang2018.repositories.TrueOrFalseExamQuestionRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuestionService {
	@Autowired
	BaseExamQuestionRepository baseRepository;
	
	@Autowired
	EssayExamQuestionRepository essayRepository;
	
	@Autowired
	FillInTheBlanksExamQuestionRepository fillRepository;
	
	@Autowired
	MultipleChoiceExamQuestionRepository multipleRepository;
	
	@Autowired
	TrueOrFalseExamQuestionRepository trueRepository;
	
	@Autowired 
	ExamRepository examRepository;
	
	
	@GetMapping("/api/question/{questionId}")
	public BaseExamQuestion getQuestionById(@PathVariable("questionId") int id, HttpServletResponse response) {
		Optional<BaseExamQuestion> base = baseRepository.findById(id);
		if(base.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		return base.get(); 
	}

	@DeleteMapping("/api/question/{questionId}")
	public String deleteQuestionById(@PathVariable("questionId") int id, HttpServletResponse response) {
		Optional<BaseExamQuestion> question = baseRepository.findById(id);
		if(question.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"error\":\"A Question does not exist with that id\"}";
		}
		baseRepository.deleteById(id);
		return "{\"success\":\"Question Deleted\"}";
	}
	
	@GetMapping("/api/exam/{examId}/question")
	public List<BaseExamQuestion> findAllQuestionForExam(
			@PathVariable("examId") int examId) {
		Optional<Exam> data = examRepository.findById(examId);
		if(data.isPresent()) {
			Exam exam = data.get();
			
			List<BaseExamQuestion> toReturn = exam.getQuestions();
			Collections.sort(toReturn);
			return toReturn;
		}
		return null;		
	}
	
	@PostMapping("/api/exam/{examId}/essay")
	public String createEssayQuestion(
			@PathVariable("examId") int examId,
			@RequestBody EssayExamQuestion essayQuestion , HttpServletResponse response ) {
		Optional<Exam> eData = examRepository.findById(examId);
		if(eData.isPresent()) {		
			Exam exam = eData.get();
			essayQuestion.setExam(exam);
			essayRepository.save(essayQuestion);
			return"{\"Success\":\"Essay Question Created!\"}" ;
				
			}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Exam With That Id Not Found\"}";		
	}
	
	@PostMapping("/api/exam/{examId}/fill")
	public String createFillQuestion(
			@PathVariable("examId") int examId,
			@RequestBody FillInTheBlanksExamQuestion fillQuestion , HttpServletResponse response ) {
		Optional<Exam> eData = examRepository.findById(examId);
		if(eData.isPresent()) {		
			Exam exam = eData.get();
			fillQuestion.setExam(exam);
			fillRepository.save(fillQuestion);
			return"{\"Success\":\"Fill In the Blanks Question Created!\"}" ;
				
			}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Exam With That Id Not Found\"}";		
	}
	
	@PostMapping("/api/exam/{examId}/multiple")
	public String createMultipleQuestion(
			@PathVariable("examId") int examId,
			@RequestBody MultipleChoiceExamQuestion multipleQuestion , HttpServletResponse response ) {
		Optional<Exam> eData = examRepository.findById(examId);
		if(eData.isPresent()) {		
			Exam exam = eData.get();
			multipleQuestion.setExam(exam);
			multipleRepository.save(multipleQuestion);
			return"{\"Success\":\"Multiple Choice Question Created!\"}" ;
				
			}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Exam With That Id Not Found\"}";		
	}
	
	@PostMapping("/api/exam/{examId}/true")
	public String createTrueQuestion(
			@PathVariable("examId") int examId,
			@RequestBody TrueOrFalseExamQuestion trueQuestion , HttpServletResponse response ) {
		Optional<Exam> eData = examRepository.findById(examId);
		if(eData.isPresent()) {		
			Exam exam = eData.get();
			trueQuestion.setExam(exam);
			trueRepository.save(trueQuestion);
			return"{\"Success\":\"True Or False Question Created!\"}" ;
				
			}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return "{\"Error\":\"Exam With That Id Not Found\"}";		
	}
	
	@PutMapping("/api/question/{questionId}/essay")
	public String updateEssayQuestion(@PathVariable("questionId") int questionId,
			@RequestBody EssayExamQuestion essayQuestion , HttpServletResponse response) {
		Optional<EssayExamQuestion> qData = essayRepository.findById(questionId);
		if(qData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Question With That Id Not Found\"}";
		}
		essayQuestion.setId(questionId);
		essayQuestion.setExam(qData.get().getExam());
		essayRepository.save(essayQuestion);
		return"{\"Success\":\"Question Updated\"}";
	}
	
	@PutMapping("/api/question/{questionId}/fill")
	public String updateFillQuestion(@PathVariable("questionId") int questionId,
			@RequestBody FillInTheBlanksExamQuestion fillQuestion , HttpServletResponse response) {
		Optional<FillInTheBlanksExamQuestion> qData = fillRepository.findById(questionId);
		if(qData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Question With That Id Not Found\"}";
		}
		fillQuestion.setId(questionId);
		fillQuestion.setExam(qData.get().getExam());
		fillRepository.save(fillQuestion);
		return"{\"Success\":\"Question Updated\"}";
	}
	
	@PutMapping("/api/question/{questionId}/multiple")
	public String updateMultipleQuestion(@PathVariable("questionId") int questionId,
			@RequestBody MultipleChoiceExamQuestion multiQuestion , HttpServletResponse response) {
		Optional<MultipleChoiceExamQuestion> qData = multipleRepository.findById(questionId);
		if(qData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Question With That Id Not Found\"}";
		}
		multiQuestion.setId(questionId);
		multiQuestion.setExam(qData.get().getExam());
		multipleRepository.save(multiQuestion);
		return"{\"Success\":\"Question Updated\"}";
	}
	
	@PutMapping("/api/question/{questionId}/true")
	public String updateTrueQuestion(@PathVariable("questionId") int questionId,
			@RequestBody TrueOrFalseExamQuestion trueQuestion , HttpServletResponse response) {
		Optional<TrueOrFalseExamQuestion> qData = trueRepository.findById(questionId);
		if(qData.isPresent() == false) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return"{\"error\":\"Question With That Id Not Found\"}";
		}
		trueQuestion.setId(questionId);
		trueQuestion.setExam(qData.get().getExam());
		trueRepository.save(trueQuestion);
		return"{\"Success\":\"Question Updated\"}";
	}
	
}
