package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hrperiod {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private long id;
	private String updated;
	private String employee_id;
	private String periods;
	private String description;
	private String planYear;
	private String status;
	
	public Hrperiod() {
		super();
	}

	public Hrperiod(long id, String updated, String employee_id, String periods, String description, String planYear,
			String status) {
		super();
		this.id = id;
		this.updated = updated;
		this.employee_id = employee_id;
		this.periods = periods;
		this.description = description;
		this.planYear = planYear;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlanYear() {
		return planYear;
	}

	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
