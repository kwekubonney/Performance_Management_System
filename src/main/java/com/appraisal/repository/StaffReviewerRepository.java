package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.appraisal.domain.EvaluationCompetency;
import com.appraisal.domain.Period;
import com.appraisal.domain.StaffReviewer;


public interface StaffReviewerRepository extends CrudRepository<StaffReviewer, Long> {
	  
	@Query(value ="select * from StaffReviewer where employee_id = ?1 and reviewer_id = ?2 and status = 'Active'", nativeQuery = true) 
	StaffReviewer reviewerCheck(String employee_id,String reviewer_id ); 
	
	  
	@Query(value ="select * from StaffReviewer where employee_id = ?1 and status = 'Active'", nativeQuery = true) 
	StaffReviewer reviewerCheckByEmployeeId(String employee_id); 
	
}
