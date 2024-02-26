package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.appraisal.domain.Workplan;


public interface WorkplanRepository extends CrudRepository<Workplan, Long> {
	
	 @Query(value ="select * from workplan where unit_id = ?1 ", nativeQuery = true)
	 List<Workplan> worplanActivityLis( String employeeId);
	 
	 
	
}
