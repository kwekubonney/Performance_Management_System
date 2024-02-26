package com.appraisal.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.appraisal.domain.User;
import com.appraisal.security.PasswordResetToken;
import com.appraisal.security.Role;
import com.appraisal.security.UserRole;


/*import lr.gov.mfdp.models.UserRegistrationDto;*/

public interface UserService {
	
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User save(User user);
	
	User findByUsername(String username);
	
	List<User> passwordUnique(String password);
	
	User findByEmail (String email);
	
	Integer findUserRole(Long role);
	
	/*List<Integer> findNextRoleUser(Integer role);*/
	List<Long> findNextRoleUser(Long role);
	
	List<BigInteger> findNextRoleUserTest(Long role);
	
	List<BigInteger> findPreviousRole(Long role);
	
	User findById(Long id);
	
	List<User> findAllUser();
	
	List<User> orgUsers(int orgId);
	
	List<User> allAppUser();
	
	void deleteById(Long id);
	
	Role findByRoleName  (String name);
	
	/*void saveUser(UserRegistrationDto userRegistrationDto);*/
}
