package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.appraisal.domain.EvaluationCompetency;
import com.appraisal.domain.PerformancePlanComments;
import com.appraisal.domain.Period;


public interface PerformancePlanCommentsRepository extends CrudRepository<PerformancePlanComments, Long> {
	  
	@Query(value ="SELECT top 1 * FROM PerformancePlanComments where moduleOperationId = ?1 order by dateUpdated desc", nativeQuery = true) 
	PerformancePlanComments getComments(long evaluationId); 
	
	@Query(value ="SELECT top 1 * FROM PerformancePlanComments where moduleOperationId = ?1 and  employeeid = ?2 order by dateUpdated desc", nativeQuery = true) 
	PerformancePlanComments getComment(String moduleOperationId, String employeeid);
	
	@Query(value ="select * from PerformancePlanComments join hr_Employee on PerformancePlanComments.employeeId = hr_Employee.employee_id where moduleOperationId = ?1 order by dateUpdated desc", nativeQuery = true) 
	List<PerformancePlanComments> showComments(String moduleOperationId);
	/*@Query(value ="select * from PerformancePlanComments inner join hr_Employee on PerformancePlanComments.employeeid = hr_Employee.employee_id where PerformancePlanComments.moduleOperationId =?1 order by dateUpdated desc", nativeQuery = true) 
	List<PerformancePlanComments> showComments(String moduleOperationId);*/
	
	/*@Query("SELECT e.firstName, e.lastName, e.middleName, p.staffComments, p.dateUpdated FROM PerformancePlanComments p " +
	           "JOIN p.employee e " +
	           "WHERE p.moduleOperationId =?1 " +
	           "ORDER BY p.dateUpdated DESC")
	    List<PerformancePlanComments> showComments( String moduleOperationId);*/
	
}
/*select * from performance_plan_obj inner join SubmitPerformancePlan on performance_plan_obj.user_id = SubmitPerformancePlan.user_id where SubmitPerformancePlan.level =?1 and SubmitPerformancePlan.user_id =?2  and SubmitPerformancePlan.performace_Plan_Period =?3", nativeQuery = true*/