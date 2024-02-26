package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.Nullable;

@Entity
@Table(name="performance_plan_obj")
public class PerformancePlanObj {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String performance_objective;
	private String performance_indicators;
	private String midYear;
	private String finalYear;
	//private String supActionTaken;
	//private String staffActionTaken;
	//private String actual;
	//private String evaluationStatus;
	//private String fScore;
	//private int total;
	private String plan_id;
	@Nullable
	private double weight_assigned;
	private String planYear;
	private String user_id;
	
	
	public PerformancePlanObj() {
		super();
	}


	public PerformancePlanObj(long id, String performance_objective, String performance_indicators, String midYear,
			String finalYear, String plan_id, double weight_assigned, String planYear, String user_id) {
		super();
		Id = id;
		this.performance_objective = performance_objective;
		this.performance_indicators = performance_indicators;
		this.midYear = midYear;
		this.finalYear = finalYear;
		this.plan_id = plan_id;
		this.weight_assigned = weight_assigned;
		this.planYear = planYear;
		this.user_id = user_id;
	}


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


	public String getMidYear() {
		return midYear;
	}


	public void setMidYear(String midYear) {
		this.midYear = midYear;
	}


	public String getFinalYear() {
		return finalYear;
	}


	public void setFinalYear(String finalYear) {
		this.finalYear = finalYear;
	}


	public String getPlan_id() {
		return plan_id;
	}


	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}


	public double getWeight_assigned() {
		return weight_assigned;
	}


	public void setWeight_assigned(double weight_assigned) {
		this.weight_assigned = weight_assigned;
	}


	public String getPlanYear() {
		return planYear;
	}


	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}


	public String getUser_id() {
		return user_id;
	}


	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	
}
