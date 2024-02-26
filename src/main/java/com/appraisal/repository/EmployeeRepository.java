package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.Employee;
import com.appraisal.domain.EvaluationPlanObj;
import com.appraisal.domain.PerformancePlanComments;
import com.appraisal.domain.PerformancePlanObj;


public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	  @Query(value ="SELECT * FROM hr_Employee where first_name = ?1 and last_name=?2", nativeQuery = true) 
	  Employee getEmployeeDetails(String first_name, String last_name); 
	  
	  @Query(value ="SELECT * FROM hr_Employee where email = ?1", nativeQuery = true) 
	  Employee getEmployeeDetailsByEmail(String email); 
	  
	  @Query(value ="SELECT * FROM hr_Employee where employee_id = ?1", nativeQuery = true) 
	  Employee getEmployeeByEmployeeId(String employee_id);
	  
	  @Query(value ="SELECT * FROM hr_Employee where employee_id in (select distinct(supervisor_id) from hr_Employee) and employee_id = ?1", nativeQuery = true) 
	  Employee checkIfEmployeeIsSupervisor(String employee_id);
	  
//	  @Query(value ="SELECT * FROM hr_Employee where supervisor_id =?1 and employee_id in (select distinct user_id from appraisal_post_all_plan_competencies) ", nativeQuery = true) 
	  @Query(value ="SELECT * FROM hr_Employee where supervisor_id =?1 ", nativeQuery = true) 
	  List<Employee> getEmployeeBySupervisorId(String supervisor_id);
	  
	  @Query(value ="select * from hr_Employee e where e.employee_id in (select employee_id from StaffReviewer where reviewer_id = ?1 and status = 'Active')", nativeQuery = true) 
	  List<Employee> getReviewerListByReviewerId(String reviewer_id);
	  
	  @Query(value ="SELECT * FROM hr_Employee where employee_id = ?1", nativeQuery = true) 
	  Employee supervisorCheck(String employee_id);
	  
	  @Query(value = "SELECT * FROM hr_Employee e WHERE e.employee_id = (SELECT e2.supervisor_id FROM hr_Employee e2 WHERE e2.employee_id = ?1)",
	           nativeQuery = true)
	    Employee findSupervisorDetail(String employee_id);
	  
	  @Query(value = " select * from hr_employee order by id desc", nativeQuery = true)
	  List<Employee> allUser();
}
