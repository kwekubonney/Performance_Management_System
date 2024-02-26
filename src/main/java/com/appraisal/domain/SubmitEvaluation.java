package com.appraisal.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class SubmitEvaluation {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	
	private double sup_staff_guidiance;
	private double sup_planning_organizing;
	private double sup_performance_mgt;
	private double sup_staff_development;
	private double sup_coaching_mentoring;
	
	private double tot_obj;
	private double tot_core_competences;
	private double tot_sup_competences;
	private double final_appraisal_score;
	private double total_scores;
	private Timestamp submitted_time;
	private Timestamp approved_time;
	private int level;
	private String user_id;
	private String evaluation_Period;
	private String status;
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public double getSup_staff_guidiance() {
		return sup_staff_guidiance;
	}
	public void setSup_staff_guidiance(double sup_staff_guidiance) {
		this.sup_staff_guidiance = sup_staff_guidiance;
	}
	public double getSup_planning_organizing() {
		return sup_planning_organizing;
	}
	public void setSup_planning_organizing(double sup_planning_organizing) {
		this.sup_planning_organizing = sup_planning_organizing;
	}
	public double getSup_performance_mgt() {
		return sup_performance_mgt;
	}
	public void setSup_performance_mgt(double sup_performance_mgt) {
		this.sup_performance_mgt = sup_performance_mgt;
	}
	public double getSup_staff_development() {
		return sup_staff_development;
	}
	public void setSup_staff_development(double sup_staff_development) {
		this.sup_staff_development = sup_staff_development;
	}
	public double getSup_coaching_mentoring() {
		return sup_coaching_mentoring;
	}
	public void setSup_coaching_mentoring(double sup_coaching_mentoring) {
		this.sup_coaching_mentoring = sup_coaching_mentoring;
	}
	public double getTot_obj() {
		return tot_obj;
	}
	public void setTot_obj(double tot_obj) {
		this.tot_obj = tot_obj;
	}
	public double getTot_core_competences() {
		return tot_core_competences;
	}
	public void setTot_core_competences(double tot_core_competences) {
		this.tot_core_competences = tot_core_competences;
	}
	public double getTot_sup_competences() {
		return tot_sup_competences;
	}
	public void setTot_sup_competences(double tot_sup_competences) {
		this.tot_sup_competences = tot_sup_competences;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getEvaluation_Period() {
		return evaluation_Period;
	}
	public void setEvaluation_Period(String evaluation_Period) {
		this.evaluation_Period = evaluation_Period;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getFinal_appraisal_score() {
		return final_appraisal_score;
	}
	public void setFinal_appraisal_score(double final_appraisal_score) {
		this.final_appraisal_score = final_appraisal_score;
	}
	public double getTotal_scores() {
		return total_scores;
	}
	public void setTotal_scores(double total_scores) {
		this.total_scores = total_scores;
	}
	
}
