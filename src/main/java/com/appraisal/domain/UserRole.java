package com.appraisal.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRole {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userRoleId;
	private int roleId;
	private Long userId;
	
	public UserRole() {
		super();
	}

	public UserRole(Long userRoleId, int roleId, Long userId) {
		super();
		this.userRoleId = userRoleId;
		this.roleId = roleId;
		this.userId = userId;
	}

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	
}
