package com.appraisal.domain;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class StaffReviewer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String employee_id;
	private String reviewer_id;
	private String rank;
	private String status;
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getReviewer_id() {
		return reviewer_id;
	}
	public void setReviewer_id(String reviewer_id) {
		this.reviewer_id = reviewer_id;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

	
	
	
	
}
