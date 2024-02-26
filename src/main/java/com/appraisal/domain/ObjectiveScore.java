package com.appraisal.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.Nullable;

@Entity
public class ObjectiveScore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String performanceobjective;
	private String performanceindicators;
	private String midyear;
	private String finalyear;
	private String planid;
	private String weightassigned;
	private String planyear;
	private String userid;
	
	private String empactiontaken;
	private String supactiontaken;
	private String actual;
	private String performancePeriod;
	private String total;
	private String perfromanceobjid;
	private String finalScore;
	
	
	
	private Timestamp submittedtime;
	private int level;
	
	public ObjectiveScore() {
		super();
	}

	public ObjectiveScore(long id, String performanceobjective, String performanceindicators, String midyear,
			String finalyear, String planid, String weightassigned, String planyear, String userid,
			String empactiontaken, String supactiontaken, String actual, String performancePeriod, String total,
			String perfromanceobjid, String finalScore, Timestamp submittedtime, int level) {
		super();
		this.id = id;
		this.performanceobjective = performanceobjective;
		this.performanceindicators = performanceindicators;
		this.midyear = midyear;
		this.finalyear = finalyear;
		this.planid = planid;
		this.weightassigned = weightassigned;
		this.planyear = planyear;
		this.userid = userid;
		this.empactiontaken = empactiontaken;
		this.supactiontaken = supactiontaken;
		this.actual = actual;
		this.performancePeriod = performancePeriod;
		this.total = total;
		this.perfromanceobjid = perfromanceobjid;
		this.finalScore = finalScore;
		this.submittedtime = submittedtime;
		this.level = level;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPerformanceobjective() {
		return performanceobjective;
	}

	public void setPerformanceobjective(String performanceobjective) {
		this.performanceobjective = performanceobjective;
	}

	public String getPerformanceindicators() {
		return performanceindicators;
	}

	public void setPerformanceindicators(String performanceindicators) {
		this.performanceindicators = performanceindicators;
	}

	public String getMidyear() {
		return midyear;
	}

	public void setMidyear(String midyear) {
		this.midyear = midyear;
	}

	public String getFinalyear() {
		return finalyear;
	}

	public void setFinalyear(String finalyear) {
		this.finalyear = finalyear;
	}

	public String getPlanid() {
		return planid;
	}

	public void setPlanid(String planid) {
		this.planid = planid;
	}

	public String getWeightassigned() {
		return weightassigned;
	}

	public void setWeightassigned(String weightassigned) {
		this.weightassigned = weightassigned;
	}

	public String getPlanyear() {
		return planyear;
	}

	public void setPlanyear(String planyear) {
		this.planyear = planyear;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmpactiontaken() {
		return empactiontaken;
	}

	public void setEmpactiontaken(String empactiontaken) {
		this.empactiontaken = empactiontaken;
	}

	public String getSupactiontaken() {
		return supactiontaken;
	}

	public void setSupactiontaken(String supactiontaken) {
		this.supactiontaken = supactiontaken;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getPerformancePeriod() {
		return performancePeriod;
	}

	public void setPerformancePeriod(String performancePeriod) {
		this.performancePeriod = performancePeriod;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPerfromanceobjid() {
		return perfromanceobjid;
	}

	public void setPerfromanceobjid(String perfromanceobjid) {
		this.perfromanceobjid = perfromanceobjid;
	}

	public String getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}

	public Timestamp getSubmittedtime() {
		return submittedtime;
	}

	public void setSubmittedtime(Timestamp submittedtime) {
		this.submittedtime = submittedtime;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
