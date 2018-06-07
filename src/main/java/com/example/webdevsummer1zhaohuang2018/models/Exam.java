package com.example.webdevsummer1zhaohuang2018.models;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Exam extends Widget {

	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String description;
	
	private int points;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="exam")
	private List<BaseExamQuestion> questions;

	
	public String getTitle() {
		return title;
	}

	public List<BaseExamQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<BaseExamQuestion> questions) {
		this.questions = questions;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}


}

