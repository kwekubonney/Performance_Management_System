package com.appraisal.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class PerformancePlanComments {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String staffComments;
	private Timestamp dateUpdated;
	private String employeeId;
	private String moduleOperationId;
	public PerformancePlanComments() {
		super();
	}
	public PerformancePlanComments(long id, String staffComments, Timestamp dateUpdated, String employeeId,
			String moduleOperationId) {
		super();
		Id = id;
		this.staffComments = staffComments;
		this.dateUpdated = dateUpdated;
		this.employeeId = employeeId;
		this.moduleOperationId = moduleOperationId;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getStaffComments() {
		return staffComments;
	}
	public void setStaffComments(String staffComments) {
		this.staffComments = staffComments;
	}
	public Timestamp getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(Timestamp dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getModuleOperationId() {
		return moduleOperationId;
	}
	public void setModuleOperationId(String moduleOperationId) {
		this.moduleOperationId = moduleOperationId;
	}
	
	
	
	
}
