package com.example.webdevsummer1zhaohuang2018.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "BASE_QUESTION")
@Inheritance(strategy=InheritanceType.JOINED)
public class BaseExamQuestion implements Comparable<BaseExamQuestion>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
  
	private int points;
  

	private String title, description, instructions;
  
	@ManyToOne
	@JsonIgnore
	private Exam exam;
  

	
	public Exam getExam() {
		return exam;
		}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
		}
	
	public int getPoints() {
		return points;
		}
		
	public void setPoints(int points) {
		this.points = points;
		}
	
	public String getTitle() {
		return title;
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
	
	public String getInstructions() {
		return instructions;
		}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
		}

	public int compareTo(BaseExamQuestion base) {
		if (base.getId() > this.getId()) {
			return -1;
		}
		else {
			return 1;
		}
	}
}

