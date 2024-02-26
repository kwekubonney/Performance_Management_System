package com.appraisal.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appraisal.repository.RoleRepository;
import com.appraisal.security.Role;
import com.appraisal.services.RoleService;



@Service
public class RoleServiceImpl implements RoleService {
	@Autowired 
	private RoleRepository roleRepository;

	@Override
	public List<Role> findAllRoles() {
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public void modifyRole(String roleId, String userId) {
		roleRepository.modifyRole(roleId, userId);
		
	}

	@Override
	public Role findRole(Long id) {
		return roleRepository.findOne(id);
	}


}
