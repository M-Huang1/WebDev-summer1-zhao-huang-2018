package com.example.webdevsummer1zhaohuang2018.models;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;


@Entity
public class Assignment extends Widget  {

	
	@NotEmpty
	private String title;
	
	@NotEmpty
	private String description;

	private int points;

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

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	
}

