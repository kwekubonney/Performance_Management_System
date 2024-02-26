package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hr_Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long Id;
	private String employee_id;
	private String last_name;
	private String first_name;
	private String middle_name;
	private String email;
	private String gender;
	private String job_grade;
	private String duty_location;
	private String position;
	private String Department;
	private String position_id;
	private String phone;
	private String supervisor_id;
	private String supervisor_name;
	private String supervisor_title;
	private String supervisor_phone;
	
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
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getJob_grade() {
		return job_grade;
	}
	public void setJob_grade(String job_grade) {
		this.job_grade = job_grade;
	}
	public String getDuty_location() {
		return duty_location;
	}
	public void setDuty_location(String duty_location) {
		this.duty_location = duty_location;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSupervisor_id() {
		return supervisor_id;
	}
	public void setSupervisor_id(String supervisor_id) {
		this.supervisor_id = supervisor_id;
	}
	public String getSupervisor_name() {
		return supervisor_name;
	}
	public void setSupervisor_name(String supervisor_name) {
		this.supervisor_name = supervisor_name;
	}
	public String getSupervisor_title() {
		return supervisor_title;
	}
	public void setSupervisor_title(String supervisor_title) {
		this.supervisor_title = supervisor_title;
	}
	public String getSupervisor_phone() {
		return supervisor_phone;
	}
	public void setSupervisor_phone(String supervisor_phone) {
		this.supervisor_phone = supervisor_phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}

	
}
