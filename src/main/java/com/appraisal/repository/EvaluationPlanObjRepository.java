package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.EvaluationPlanObj;
import com.appraisal.domain.PerformancePlanObj;


public interface EvaluationPlanObjRepository extends CrudRepository<EvaluationPlanObj, Long> {
	

	  @Query(value ="select * from evaluation_plan_obj where user_id = ?1", nativeQuery = true) 
	  List<EvaluationPlanObj> userEvaluationPlanObj(String id);
	  
	
	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value = "delete from evaluation_plan_obj where id = ?1", nativeQuery =
	 * true) void removeObj(Long id);
	 */

	/*
	 * @Transactional
	 * 
	 * @Modifying
	 * 
	 * @Query(value = "UPDATE appl SET status = 'A' WHERE id = ?1", nativeQuery =
	 * true) void activateAppl(Long id);
	 * 
	 * @Query(value =
	 * "SELECT * FROM appl WHERE appl_code = ?1 and (status is null or status = 'R') "
	 * , nativeQuery = true)
	 * 
	 * @Query(value = "SELECT * FROM appl WHERE appl_code = ?1 and status is null ",
	 * nativeQuery = true) Appl findByApplCode(String id);
	 * 
	 * 
	 * @Query(value =
	 * "SELECT * FROM appl WHERE appl_code = ?1 and APP_LEVEL = 2 and status = 'G' "
	 * , nativeQuery = true) Appl verifyAppl(String id);
	 * 
	 * @Query(value =
	 * "SELECT * FROM appl WHERE beneficiary_id = ?1 order by id desc", nativeQuery
	 * = true) List<Appl> myAppl(Long id);
	 */
}
