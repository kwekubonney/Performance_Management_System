package com.appraisal.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.PlanScore;

public interface PlanScoreRepository extends CrudRepository<PlanScore, Long> {
		@Query(value="select SUM(total) from PlanScore where staffid =?1 and performanceperiod =?2 and appraisalYear =?3", nativeQuery = true)
		  String totalPlanScore(String staffid, String peformanceperiod, String appraisalyear);
		
		@Query(value="select * from PlanScore where perfromanceobjid =?1 and performancePeriod=?2", nativeQuery = true)
		PlanScore checkplanscore(long myid, String performanceperiod);
		
		@Transactional
		  @Modifying
		  @Query(value="update PlanScore set  empActionTaken = ?1 where perfromanceobjid =?2", nativeQuery = true)
		  void updateSuperActionTaken(String empActionTaken,  long myid);
		
		@Transactional
		  @Modifying
		  @Query(value="update PlanScore set  actual = ?2, total=?3, finalScore=?4 where perfromanceobjid =?1", nativeQuery = true)
		  void updatePlanScore(long myid, int actual, int total, double finalScore);
		
		 
	
}
