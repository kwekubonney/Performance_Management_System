package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.PerformancePlanObj;
import com.appraisal.domain.SubmitEvaluation;
import com.appraisal.domain.SubmitPerformancePlan;

public interface SubmitPerformancePlanRepository extends CrudRepository<SubmitPerformancePlan, Long> {

	@Query(value = "SELECT * FROM SubmitPerformancePlan where user_id = ?1 order by submitted_time desc", nativeQuery = true)
	SubmitPerformancePlan getSubmittedPlanByEmployeeId(String employee_id);
	

	@Query(value = "SELECT * FROM SubmitPerformancePlan where performace_plan_period = ?1 and USER_ID = ?2 ", nativeQuery = true)
	SubmitPerformancePlan getSubmittedPlan(String period, String employee_id);
	
	@Query(value = "SELECT * FROM SubmitPerformancePlan where id = ?1", nativeQuery = true)
	SubmitPerformancePlan getSubmittedPlan1(int id);
	
	@Query(value = "SELECT * FROM SubmitPerformancePlan where performace_plan_period = ?1 and USER_ID = ?2 and level = 2", nativeQuery = true)
	SubmitPerformancePlan getApprovedPlan(String period, String employee_id);
	
	@Query(value = "SELECT * FROM SubmitPerformancePlan where USER_ID = ?1 and status is not null order by status desc", nativeQuery = true)
	List<SubmitPerformancePlan> getAllSubmittedPlan(String employee_id);
	
	@Query(value = "SELECT * FROM SubmitPerformancePlan where performace_plan_period = ?1", nativeQuery = true)
	SubmitPerformancePlan getAllSubmittedPlanByPeriod(String period);

	@Query(value = "SELECT * FROM SubmitPerformancePlan where performace_plan_period = ?1 and USER_ID = ?2 and status is not null", nativeQuery = true)
	SubmitPerformancePlan getSubmittedPlanByStaffForPlanningOperation(String period, String employee_id);
	
	@Query(value = "SELECT * FROM SubmitPerformancePlan where performace_plan_period = ?1 and USER_ID = ?2 and status = 'Submitted'", nativeQuery = true)
	SubmitPerformancePlan getSubmittedPlanBySupervisorForPlanningOperation(String period, String employee_id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE SubmitPerformancePlan SET level =?1 WHERE plan_id = ?2", nativeQuery = true)
	void approvePerformancePlan(int level, String plan_id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE SubmitPerformancePlan SET level = level - 1 WHERE id = ?1", nativeQuery = true)
	void returnPerformancePlan(Long id);
	
	

	@Query(value = "SELECT * FROM SubmitPerformancePlan where user_id =?1 and level =?2 order by performace_plan_period desc;", nativeQuery = true)
	List<SubmitPerformancePlan> getAllStaffPlan(String user_id, int level);
	
	@Query(value = "select * from SubmitPerformancePlan where user_id = ?1 order by performace_Plan_Period desc", nativeQuery = true)
	List<SubmitPerformancePlan> staffPlanningList(String user_id);

	@Query(value = "select * from SubmitPerformancePlan where plan_id = ?1", nativeQuery = true)
	SubmitPerformancePlan checkIfPlanExit (String planid);
}
