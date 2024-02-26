package com.appraisal.repository;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.appraisal.security.UserRole;


public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
	
	@Query(value = "Select role_id from user_role WHERE user_id = ?1", nativeQuery = true)
	Integer findRole(Long id);
	
	@Query(value = "Select user_id from user_role WHERE role_id = ?1 + 1", nativeQuery = true)
	List<Long> findNextRoleUser(Long  id);
	
	@Query(value = "Select user_id from user_role WHERE role_id = ?1 + 1", nativeQuery = true)
	List<BigInteger> findNextRoleUserTest(Long  id);
	
	@Query(value = "Select user_id from user_role WHERE role_id = ?1 - 1", nativeQuery = true)
	List<BigInteger> findPreviousRole(Long id);

	 @Transactional
	 @Modifying
	 @Query(value = "INSERT INTO user_role (role_id, user_id) Values(?1, ?2)", nativeQuery = true)
	void saveUserRole(int roleid, Long userid);

	
}
