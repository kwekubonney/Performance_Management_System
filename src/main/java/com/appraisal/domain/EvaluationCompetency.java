package com.appraisal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="evaluation_competency")
public class EvaluationCompetency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String position_id;
	private int competency_id;
	private String competency_title;
	private double required;
	private double assigned;
	private double score;
	private double attained;
	private double actual_proficiency;
	private String remarks;
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
	public String getCompetency_title() {
		return competency_title;
	}
	public void setCompetency_title(String competency_title) {
		this.competency_title = competency_title;
	}
	public double getRequired() {
		return required;
	}
	public void setRequired(double required) {
		this.required = required;
	}
	public double getAssigned() {
		return assigned;
	}
	public void setAssigned(double assigned) {
		this.assigned = assigned;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public double getAttained() {
		return attained;
	}
	public void setAttained(double attained) {
		this.attained = attained;
	}
	public double getActual_proficiency() {
		return actual_proficiency;
	}
	public void setActual_proficiency(double actual_proficiency) {
		this.actual_proficiency = actual_proficiency;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
