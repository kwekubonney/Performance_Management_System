package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Supervisorcompetency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	private String userid;
	private String supervisorid;
	private String manlabel1;
	private String manlabel2;
	private String manlabel3;
	private String manlabel4;
	private String manlabel5;
	private String period;
	private String competencyYear;
	
	public Supervisorcompetency() {
		super();
	}

	public Supervisorcompetency(Long id, String userid, String supervisorid, String manlabel1, String manlabel2,
			String manlabel3, String manlabel4, String manlabel5, String period, String competencyYear) {
		super();
		this.id = id;
		this.userid = userid;
		this.supervisorid = supervisorid;
		this.manlabel1 = manlabel1;
		this.manlabel2 = manlabel2;
		this.manlabel3 = manlabel3;
		this.manlabel4 = manlabel4;
		this.manlabel5 = manlabel5;
		this.period = period;
		this.competencyYear = competencyYear;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSupervisorid() {
		return supervisorid;
	}

	public void setSupervisorid(String supervisorid) {
		this.supervisorid = supervisorid;
	}

	public String getManlabel1() {
		return manlabel1;
	}

	public void setManlabel1(String manlabel1) {
		this.manlabel1 = manlabel1;
	}

	public String getManlabel2() {
		return manlabel2;
	}

	public void setManlabel2(String manlabel2) {
		this.manlabel2 = manlabel2;
	}

	public String getManlabel3() {
		return manlabel3;
	}

	public void setManlabel3(String manlabel3) {
		this.manlabel3 = manlabel3;
	}

	public String getManlabel4() {
		return manlabel4;
	}

	public void setManlabel4(String manlabel4) {
		this.manlabel4 = manlabel4;
	}

	public String getManlabel5() {
		return manlabel5;
	}

	public void setManlabel5(String manlabel5) {
		this.manlabel5 = manlabel5;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCompetencyYear() {
		return competencyYear;
	}

	public void setCompetencyYear(String competencyYear) {
		this.competencyYear = competencyYear;
	}

	
}
