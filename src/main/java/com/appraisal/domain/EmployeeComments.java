package com.appraisal.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmployeeComments {
	@Id
	private int id;
	private String first_name;
	private String middle_name;
	private String last_name;
	private Timestamp dateUpdated;
	private String staffComments;
	
	public EmployeeComments() {
		super();
	}

	public EmployeeComments(int id, String first_name, String middle_name, String last_name, Timestamp dateUpdated,
			String staffComments) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.dateUpdated = dateUpdated;
		this.staffComments = staffComments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Timestamp getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getStaffComments() {
		return staffComments;
	}

	public void setStaffComments(String staffComments) {
		this.staffComments = staffComments;
	}
	
	
}
