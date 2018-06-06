package com.example.webdevsummer1zhaohuang2018.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="MULTIPLE_CHOICE_QUESTION")
public class MultipleChoiceExamQuestion
  extends BaseExamQuestion {
  
	@Column(name = "OPTIONS", nullable = false)
	private String options;
	
	@Column(name = "CORRECT_OPTION", nullable = false)
	private int correctOption;
	
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public int getCorrectOption() {
		return correctOption;
	}
	public void setCorrectOption(int correctOption) {
		this.correctOption = correctOption;
	}
}

