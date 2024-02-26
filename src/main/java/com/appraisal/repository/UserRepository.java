package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	@Query(value = "SELECT * from users where email=?1", nativeQuery = true)
	User getStaffByEmail(String email);
	
	@Query(value = "SELECT id,email,enabled,first_name,last_name,phone,username,r.name as password FROM usr u join user_role ur on u.id = ur.user_id join role r on r.role_id = ur.role_id order by id desc", nativeQuery = true)
	List<User> allAppUser();
	
	@Query(value = "SELECT * FROM usr order by id desc", nativeQuery = true)
	List<User> getFiltersAllUsers();
	
	@Query(value = "SELECT * FROM usr where id = ?1", nativeQuery = true)
	User editUser(String userId);
	
	@Query(value = "SELECT * FROM usr where password = ?1", nativeQuery = true)
	List<User> passwordUnique(String password);
	
	@Query(value = "select * from usr where organization_id = ?1", nativeQuery = true)
	List<User> orgUsers(int orgId);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE usr SET password = ?1 WHERE id = ?2", nativeQuery = true)
	void changePassword(String password, Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET password = ?1 WHERE email = ?2", nativeQuery = true)
	void newPassword(String password, String email);

	
	/*@Transactional
	@Modifying
	@Query(value = "Delete from [dutyfree].[dbo].[user_role] where id = ?1 ", nativeQuery = true)
	void deleteByid(String id);*/
}

                                                                                                                                                                                  