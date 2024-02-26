package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PlanScore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String empActionTaken;
	private String supActionTaken;
	private int actual;
	private String staffid;
	//private String weightAssign;
	private int total;
	private long perfromanceobjid;
	private String performancePeriod;
	private double finalScore;
	private String appraisalYear;
	
	public PlanScore() {
		super();
	}

	public PlanScore(long id, String empActionTaken, String supActionTaken, int actual, String staffid, int total,
			long perfromanceobjid, String performancePeriod, double finalScore, String appraisalYear) {
		super();
		Id = id;
		this.empActionTaken = empActionTaken;
		this.supActionTaken = supActionTaken;
		this.actual = actual;
		this.staffid = staffid;
		this.total = total;
		this.perfromanceobjid = perfromanceobjid;
		this.performancePeriod = performancePeriod;
		this.finalScore = finalScore;
		this.appraisalYear = appraisalYear;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getEmpActionTaken() {
		return empActionTaken;
	}

	public void setEmpActionTaken(String empActionTaken) {
		this.empActionTaken = empActionTaken;
	}

	public String getSupActionTaken() {
		return supActionTaken;
	}

	public void setSupActionTaken(String supActionTaken) {
		this.supActionTaken = supActionTaken;
	}

	public int getActual() {
		return actual;
	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public long getPerfromanceobjid() {
		return perfromanceobjid;
	}

	public void setPerfromanceobjid(long perfromanceobjid) {
		this.perfromanceobjid = perfromanceobjid;
	}

	public String getPerformancePeriod() {
		return performancePeriod;
	}

	public void setPerformancePeriod(String performancePeriod) {
		this.performancePeriod = performancePeriod;
	}

	public double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}

	public String getAppraisalYear() {
		return appraisalYear;
	}

	public void setAppraisalYear(String appraisalYear) {
		this.appraisalYear = appraisalYear;
	}

}
