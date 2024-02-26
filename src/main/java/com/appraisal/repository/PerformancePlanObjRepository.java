package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.ObjectiveScore;
import com.appraisal.domain.PerformancePlanObj;
import com.appraisal.domain.PlanCompetency;


public interface PerformancePlanObjRepository extends CrudRepository<PerformancePlanObj, Long> {

	  @Query(value ="select * from performance_plan_obj where user_id = ?1 and planYear = ?2", nativeQuery = true) 
	  List<PerformancePlanObj> userPerformancePlanObj(String id, String period);
	  
	  @Query(value ="select * from performance_plan_obj where user_id = ?1 and planYear = ?2 and performance_objective = ?3", nativeQuery = true) 
	   PerformancePlanObj validateIfUserAddedObjective(String id, String period, String objectName); 
	  
	  @Query(value ="SELECT * FROM performance_plan_obj where planYear = ?1 and user_id = ?2 and planYear is not null and id != ?3", nativeQuery = true) 
	  List<PerformancePlanObj> getObjectiveTempTotalExcludeObjectiveId(String period, String employee_id, long competencyId); 
	
	 /* @Query(value="select * from performance_plan_obj inner join SubmitPerformancePlan on performance_plan_obj.user_id = SubmitPerformancePlan.user_id where SubmitPerformancePlan.level =?1 and SubmitPerformancePlan.user_id =?2  and SubmitPerformancePlan.performace_Plan_Period =?3", nativeQuery = true)
	  List<PerformancePlanObj> getSubmitedPerformanceObjForStaff(String level, String user_id, String performace_Plan_Period);*/
	  
	  /*@Query(value="select  * from performance_plan_obj left join PlanScore on performance_plan_obj.id = PlanScore.perfromanceobjid inner join SubmitPerformancePlan on performance_plan_obj.plan_id = SubmitPerformancePlan.plan_id where performance_plan_obj.planYear =?1 AND performance_plan_obj.user_id =?2 AND SubmitPerformancePlan.level =?3", nativeQuery = true)
	  List<PerformancePlanObj> getSubmitedPerformanceObjForStaff(String planYear, String userId, int level);*/
	  
	  //@Query(value="select performance_plan_obj.id, performance_plan_obj.performance_objective, performance_plan_obj.performance_indicators, performance_plan_obj.midYear, performance_plan_obj.finalYear, performance_plan_obj.plan_id,  performance_plan_obj.user_id, PlanScore.empActionTaken, PlanScore.supActionTaken, PlanScore.actual, PlanScore.total, PlanScore.perfromanceobjid, PlanScore.finalScore, SubmitPerformancePlan.submitted_time, SubmitPerformancePlan.level  from performance_plan_obj left join PlanScore on performance_plan_obj.id = PlanScore.perfromanceobjid inner join SubmitPerformancePlan on performance_plan_obj.plan_id = SubmitPerformancePlan.plan_id where performance_plan_obj.planYear =?1 AND performance_plan_obj.user_id =?2 AND SubmitPerformancePlan.level =?3", nativeQuery = true)
	 // List<ObjectiveScore> getSubmitedPerformanceObjForStaff(String planYear, String userId, int level);
	  /////below is not in use
	  @Query(value="select * from performance_plan_obj inner join SubmitPerformancePlan on performance_plan_obj.plan_id = SubmitPerformancePlan.plan_id where SubmitPerformancePlan.level =?1 and SubmitPerformancePlan.plan_id =?2", nativeQuery = true)
	  List<PerformancePlanObj> getSubmitedPerformanceObjForStaffView(String level, String plain_id);
	  
	  @Transactional
	  @Modifying
	  @Query(value="update performance_plan_obj set actual =?1,  supActionTaken = ?2, fScore =?3, total =?4 where id =?5", nativeQuery = true)
	  void updatescore(String actual, String staffActionTaken, String fScore, String total, long id);
	   
	  /*@Query(value="select SUM(total) from performance_plan_obj where user_id =?1", nativeQuery = true)
	  int totalPlanScore(String userid);*/
	  
	  @Query(value="select SUM(weight_assigned) from performance_plan_obj where user_id =?1 and planYear=?2", nativeQuery = true)
	  int totalweightassign(String userid, String period);
	  
	  @Transactional
	  @Modifying
	  @Query(value="update performance_plan_obj set weight_assigned =?1 where id =?2", nativeQuery = true)
	  void updateweightassign(int weight_assigned, long id);
	  
	  @Query(value="select SUM(weight_assigned) from performance_plan_obj where plan_id =?1", nativeQuery = true)
	  int countweightassign(String plan_id);
	  
	  @Query(value ="select * from performance_plan_obj where id = ?1", nativeQuery = true) 
	  PerformancePlanObj findPlanById(long id);

	  
	 
}
