package com.appraisal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.appraisal.services.impl.UserSecurityService;
import com.appraisal.utility.SecurityUtility;

                                                                                                                                                                                       

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;

	@Autowired
	private UserSecurityService userSecurityService;

	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	private static final String[] PUBLIC_MATCHERS = {
			"/userAppl",
			"/userConfirm",
			"/userAppl",
			"/userConfirm",
			"/submit",
            "/continueWithPayment",
            "/payment",
            "/cashPayment",
            "/cardPayment",
            "/comfirmPassword",
			"/css/**",
			"/js/**",
			"/image/**",
			"/img/**",
			"/vendor/**",
			"/error",
			"/data/**",
			"/icons-reference/**",
			"/assets/**",
			"/forgetPassword",
			"/login",
			"/registration",
			"/tinFetch",
			"/add_registration_Organ",
			"/registration_user",
			"/fonts/**",
			"/common/**",
			"/validateNir",
			"/lnpClearance", 
			"/printCert",
			"/userCheckStatus",
			"/verifyClearanceCertificate",
			"/userpassword"
			
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests().
			antMatchers(PUBLIC_MATCHERS).
			permitAll().anyRequest().authenticated();

		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/login?error")
			.loginPage("/login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login")
			.and()
			.rememberMe();  
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}

}
