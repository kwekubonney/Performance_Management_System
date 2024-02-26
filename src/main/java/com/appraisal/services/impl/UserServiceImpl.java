package com.appraisal.services.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.appraisal.domain.User;
import com.appraisal.repository.PasswordResetTokenRepository;
import com.appraisal.repository.RoleRepository;
import com.appraisal.repository.UserRepository;
import com.appraisal.repository.UserRoleRepository;
import com.appraisal.security.PasswordResetToken;
import com.appraisal.security.Role;
import com.appraisal.security.UserRole;
import com.appraisal.services.UserService;

/*import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.management.relation.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lnp.lr.com.domain.security.PasswordResetToken;
import lnp.lr.com.domain.security.UserRole;
import lnp.lr.com.model.User;
import lnp.lr.com.repository.PasswordResetTokenRepository;
import lnp.lr.com.repository.RoleRepository;
import lnp.lr.com.repository.UserRepository;
import lnp.lr.com.repository.UserRoleRepository;
import lnp.lr.com.services.UserService;*/




@Service
public class UserServiceImpl implements UserService {

	private static final org.jboss.logging.Logger LOG = LoggerFactory.logger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.");
		} else {
			for (UserRole ur : userRoles) {
				roleRepository.save(ur.getRole());
			}

			user.getUserRoles().addAll(userRoles);

			localUser = userRepository.save(user);
		}

		return localUser;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(User user) {

		return userRepository.save(user);
	}

	@Override
	public Integer findUserRole(Long role) {
		return userRoleRepository.findRole(role);
	}

	@Override
	public List<BigInteger> findNextRoleUserTest(Long role) {
		return userRoleRepository.findNextRoleUserTest(role);
	}

	@Override
	public List<Long> findNextRoleUser(Long role) {
		return userRoleRepository.findNextRoleUser(role);
	}

	@Override
	public List<BigInteger> findPreviousRole(Long role) {
		return userRoleRepository.findPreviousRole(role);
	}

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAllUser() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> allAppUser() {
		return userRepository.allAppUser();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.delete(id);
		;

	}

	@Override
	public Role findByRoleName(String name) {
		return roleRepository.findByname(name);
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public List<User> passwordUnique(String password) {

		return this.userRepository.passwordUnique(password);
	}

	@Override
	public List<User> orgUsers(int orgId) {
	
		return userRepository.orgUsers(orgId);
	}

}
