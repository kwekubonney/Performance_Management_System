package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="evaluation_plan_obj")
public class EvaluationPlanObj {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String performance_objective;
	private String performance_indicators;
	private double quarter1;
	private double quarter2;
	private double quarter3;
	private double quarter4;
	private double actual_in_percentage;
	private double Score;
	private double weight_assigned;
	private double tot;
	private String appraisal_quarter;
	private String appraisal_year;
	private String user_id;
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getPerformance_objective() {
		return performance_objective;
	}
	public void setPerformance_objective(String performance_objective) {
		this.performance_objective = performance_objective;
	}
	public String getPerformance_indicators() {
		return performance_indicators;
	}
	public void setPerformance_indicators(String performance_indicators) {
		this.performance_indicators = performance_indicators;
	}
	public double getQuarter1() {
		return quarter1;
	}
	public void setQuarter1(double quarter1) {
		this.quarter1 = quarter1;
	}
	public double getQuarter2() {
		return quarter2;
	}
	public void setQuarter2(double quarter2) {
		this.quarter2 = quarter2;
	}
	public double getQuarter3() {
		return quarter3;
	}
	public void setQuarter3(double quarter3) {
		this.quarter3 = quarter3;
	}
	public double getQuarter4() {
		return quarter4;
	}
	public void setQuarter4(double quarter4) {
		this.quarter4 = quarter4;
	}
	public double getActual_in_percentage() {
		return actual_in_percentage;
	}
	public void setActual_in_percentage(double actual_in_percentage) {
		this.actual_in_percentage = actual_in_percentage;
	}
	public double getScore() {
		return Score;
	}
	public void setScore(double score) {
		Score = score;
	}
	public double getWeight_assigned() {
		return weight_assigned;
	}
	public void setWeight_assigned(double weight_assigned) {
		this.weight_assigned = weight_assigned;
	}
	public double getTot() {
		return tot;
	}
	public void setTot(double tot) {
		this.tot = tot;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAppraisal_quarter() {
		return appraisal_quarter;
	}
	public void setAppraisal_quarter(String appraisal_quarter) {
		this.appraisal_quarter = appraisal_quarter;
	}
	public String getAppraisal_year() {
		return appraisal_year;
	}
	public void setAppraisal_year(String appraisal_year) {
		this.appraisal_year = appraisal_year;
	}
	
}
