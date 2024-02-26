package com.appraisal.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class SubmitPerformancePlan {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String plan_id;
	/*private double sup_staff_guidiance;
	private double sup_planning_organizing;
	private double sup_performance_mgt;
	private double sup_staff_development;
	private double sup_coaching_mentoring;*/
	
	/*private double tot_obj;
	private double tot_core_competences;
	private double tot_sup_competences;*/
	private Timestamp submitted_time;
	private Timestamp approved_time;
	private int level;
	private String user_id;
	private String performace_Plan_Period;
	private String status;
	
	public SubmitPerformancePlan() {
		super();
	}

	public SubmitPerformancePlan(long id, String plan_id, Timestamp submitted_time, Timestamp approved_time, int level,
			String user_id, String performace_Plan_Period, String status) {
		super();
		Id = id;
		this.plan_id = plan_id;
		this.submitted_time = submitted_time;
		this.approved_time = approved_time;
		this.level = level;
		this.user_id = user_id;
		this.performace_Plan_Period = performace_Plan_Period;
		this.status = status;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public Timestamp getSubmitted_time() {
		return submitted_time;
	}

	public void setSubmitted_time(Timestamp submitted_time) {
		this.submitted_time = submitted_time;
	}

	public Timestamp getApproved_time() {
		return approved_time;
	}

	public void setApproved_time(Timestamp approved_time) {
		this.approved_time = approved_time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPerformace_Plan_Period() {
		return performace_Plan_Period;
	}

	public void setPerformace_Plan_Period(String period) {
		this.performace_Plan_Period = period;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
