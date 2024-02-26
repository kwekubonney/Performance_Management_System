package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.appraisal.domain.EmployeeComments;
import com.appraisal.domain.EvaluationComments;

public interface EmployeeCommentsRepository extends JpaRepository<EmployeeComments, Integer>{
	//@Query(value "select  ROW_NUMBER() OVER (ORDER BY first_name) row_num,hr_Employee.first_name, hr_Employee.last_name, hr_Employee.middle_name, PerformancePlanComments.staffComments, PerformancePlanComments.dateUpdated from PerformancePlanComments join hr_Employee on PerformancePlanComments.employeeId = hr_Employee.employee_id where moduleOperationId =?1 order by dateUpdated desc", nativeQuery = true)
	
	@Query(value ="select  * from PerformancePlanComments join hr_Employee on PerformancePlanComments.employeeId = hr_Employee.employee_id where moduleOperationId =?1 order by dateUpdated desc", nativeQuery = true) 
	List<EmployeeComments> showComments(String moduleOperationId);
	
	@Query(value ="select * from EvaluationComments join hr_Employee on EvaluationComments.employeeId = hr_Employee.employee_id where moduleOperationId=?1 order by dateUpdated desc", nativeQuery = true) 
	List<EmployeeComments> showEvaluationComments(String moduleOperationId);
	
	
}
