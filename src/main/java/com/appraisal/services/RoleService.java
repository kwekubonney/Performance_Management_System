package com.appraisal.services;

import java.util.List;

import com.appraisal.security.Role;

public interface RoleService {

	List<Role> findAllRoles();

	void modifyRole(String roleId, String userId);

	Role findRole(Long id);

}
