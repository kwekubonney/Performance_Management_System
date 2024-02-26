package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.PerformancePlanObj;
import com.appraisal.domain.SubmitEvaluation;
import com.appraisal.domain.SubmitPerformancePlan;


public interface SubmitEvaluationRepository extends CrudRepository<SubmitEvaluation, Long> {

	@Query(value ="SELECT top 1 * FROM SubmitEvaluation where user_id = ?1 order by submitted_time desc", nativeQuery = true) 
	SubmitEvaluation getSubmittedAppraisal(String employee_id);
	
	@Query(value = "SELECT * FROM SubmitEvaluation where evaluation_period = ?1 and USER_ID = ?2", nativeQuery = true)
	SubmitEvaluation getSubmittedEvaluation(String period, String employee_id);
	
	@Query(value = "SELECT * FROM SubmitEvaluation where evaluation_period = ?1", nativeQuery = true)
	SubmitEvaluation getSubmittedEvaluationByPeriod(String period);
	
	@Query(value = "SELECT * FROM SubmitEvaluation where USER_ID = ?1 and level = ?2  order by performance_plan_period desc", nativeQuery = true)
	List<SubmitEvaluation> getAllSubmittedEvaluation(String employee_id, int level);
	
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE SubmitEvaluation SET level = level + 1 WHERE id = ?1", nativeQuery = true)
	void approveEvaluation(Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE SubmitEvaluation SET level = level - 1 WHERE id = ?1", nativeQuery = true)
	void returnEvaluation(Long id);

}
