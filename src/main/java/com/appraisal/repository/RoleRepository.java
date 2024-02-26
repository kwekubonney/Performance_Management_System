package com.appraisal.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.security.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByname(String name);
	
	@Query(value = "SELECT r.Role_id, name FROM Role R join User_Role U on r.role_id = u.role_id where u.user_id = ?1", nativeQuery = true)
	Role findRole(String id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE User_Role SET role_id = ?1  WHERE user_id = ?2", nativeQuery = true)
	void modifyRole(String roleId, String userId);
}
