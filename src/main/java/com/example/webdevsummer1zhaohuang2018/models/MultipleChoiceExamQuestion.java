package com.example.webdevsummer1zhaohuang2018.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name ="MULTIPLE_CHOICE_QUESTION")
public class MultipleChoiceExamQuestion
  extends BaseExamQuestion {
  
	
	@Column(name = "CORRECT_OPTION", nullable = false)
	private int correctOption;

	public int getCorrectOption() {
		return correctOption;
	}
	public void setCorrectOption(int correctOption) {
		this.correctOption = correctOption;
	}
}

