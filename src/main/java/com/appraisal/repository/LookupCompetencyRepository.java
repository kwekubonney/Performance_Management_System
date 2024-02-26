package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.LookupCompetency;
import com.appraisal.domain.Employee;
import com.appraisal.domain.EvaluationPlanObj;
import com.appraisal.domain.PerformancePlanObj;


public interface LookupCompetencyRepository extends CrudRepository<LookupCompetency, Long> {
	
	
	@Query(value ="SELECT * FROM JobCompetency where JobCatalogID = ?1", nativeQuery = true) 
	  List<LookupCompetency> getCompetency(String positionId); 

/*	  @Query(value ="SELECT * FROM hr_Employee where first_name = ?1 and last_name=?2", nativeQuery = true) 
	  Employee getEmployeeDetails(String first_name, String last_name); 
	  
	  @Query(value ="SELECT * FROM hr_Employee where employee_id = ?1", nativeQuery = true) 
	  Employee getEmployeeByEmployeeId(String employee_id);
	  
	  @Query(value ="SELECT * FROM hr_Employee where supervisor_id =?1 and employee_id in (select distinct user_id from appraisal_post_all_plan_competencies) ", nativeQuery = true) 
	  List<Employee> getEmployeeBySupervisorId(String supervisor_id); */
	
}
