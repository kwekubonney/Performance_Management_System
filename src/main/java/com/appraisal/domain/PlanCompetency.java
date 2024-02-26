package com.appraisal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="plan_competency")
public class PlanCompetency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String position_id;
	private int competency_id;
	private String competency_title;
	private String required_point;
	private double actual_point;
	private String employee_id;
	private String period;
	private String level;
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public int getCompetency_id() {
		return competency_id;
	}
	public void setCompetency_id(int competency_id) {
		this.competency_id = competency_id;
	}
	public String getRequired_point() {
		return required_point;
	}
	public void setRequired_point(String required_point) {
		this.required_point = required_point;
	}

	public double getActual_point() {
		return actual_point;
	}
	public void setActual_point(double actual_point) {
		this.actual_point = actual_point;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getCompetency_title() {
		return competency_title;
	}
	public void setCompetency_title(String competency_title) {
		this.competency_title = competency_title;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

	
	
	
}
