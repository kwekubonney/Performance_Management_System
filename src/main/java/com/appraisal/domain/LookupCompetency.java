package com.appraisal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="JobCompetency")
public class LookupCompetency {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="JobCompID")
	private long Id;
	@Column(name="JobCatalogID")
	private String position_id;
	@Column(name="CompetencyTitle")
	private String competency_title;
	@Column(name="RequiredPoints")
	private String required_point;
	@Column(name="Actual_Points")
	private String actual_point;
	@Column(name="period")
	private String period;
	
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	public String getPosition_id() {
		return position_id;
	}
	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}
	public String getCompetency_title() {
		return competency_title;
	}
	public void setCompetency_title(String competency_title) {
		this.competency_title = competency_title;
	}
	public String getRequired_point() {
		return required_point;
	}
	public void setRequired_point(String required_point) {
		this.required_point = required_point;
	}
	public String getActual_point() {
		return actual_point;
	}
	public void setActual_point(String actual_point) {
		this.actual_point = actual_point;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	
	
	
	
	
}
