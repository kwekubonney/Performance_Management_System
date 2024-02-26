package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.LookupCompetency;
import com.appraisal.domain.Employee;
import com.appraisal.domain.EvaluationCompetency;
import com.appraisal.domain.EvaluationPlanObj;
import com.appraisal.domain.PerformancePlanObj;
import com.appraisal.domain.PlanCompetency;


public interface EvaluationCompetencyRepository extends CrudRepository<EvaluationCompetency, Long> {
	
	
	@Query(value ="SELECT * FROM evaluation_competency where period = ?1 and employee_id =?2 order by attained Asc", nativeQuery = true) 
	  List<EvaluationCompetency> getCompetency(String period, String employee_id); 
	
	 @Transactional
	 @Modifying
	 @Query(value = "UPDATE evaluation_competency SET score = ?1,attained=?2,actual_proficiency=?3,remarks=?4  WHERE id = ?5", nativeQuery = true) 
	 void scoreEvaluationCompetency(double score,double attained_weight,double actual_profeciency,String remark, int id);
	 

/*	  @Query(value ="SELECT * FROM hr_Employee where first_name = ?1 and last_name=?2", nativeQuery = true) 
	  Employee getEmployeeDetails(String first_name, String last_name); 
	  
	  @Query(value ="SELECT * FROM hr_Employee where employee_id = ?1", nativeQuery = true) 
	  Employee getEmployeeByEmployeeId(String employee_id);
	  
	  @Query(value ="SELECT * FROM hr_Employee where supervisor_id =?1 and employee_id in (select distinct user_id from appraisal_post_all_plan_competencies) ", nativeQuery = true) 
	  List<Employee> getEmployeeBySupervisorId(String supervisor_id); */
	
}
