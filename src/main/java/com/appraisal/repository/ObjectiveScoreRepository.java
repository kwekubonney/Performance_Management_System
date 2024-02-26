package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.appraisal.domain.ObjectiveScore;
import com.appraisal.domain.PerformancePlanObj;

public interface ObjectiveScoreRepository  extends CrudRepository<ObjectiveScore, Long>  {
	@Query(value="select performance_plan_obj.id, performance_plan_obj.weight_assigned as weightassigned, performance_plan_obj.planYear as planyear, performance_plan_obj.performance_objective as performanceobjective, performance_plan_obj.performance_indicators as performanceindicators, performance_plan_obj.midYear as midyear, performance_plan_obj.finalyear as finalyear, performance_plan_obj.plan_id as planid,  performance_plan_obj.user_id as userid, PlanScore.empactiontaken, PlanScore.supactiontaken, PlanScore.actual, PlanScore.total, planScore.performancePeriod, PlanScore.perfromanceobjid, PlanScore.finalScore as finalscore, SubmitPerformancePlan.submitted_time as submittedtime, SubmitPerformancePlan.level  from performance_plan_obj left join PlanScore on performance_plan_obj.id = PlanScore.perfromanceobjid inner join SubmitPerformancePlan on performance_plan_obj.plan_id = SubmitPerformancePlan.plan_id where performance_plan_obj.planYear =?1 AND performance_plan_obj.user_id =?2 AND SubmitPerformancePlan.level =?3", nativeQuery = true)
	List<ObjectiveScore> getSubmitedPerformanceObjForStaff(String planYear, String userId, int level);
	
	@Query(value="select performance_plan_obj.id, performance_plan_obj.weight_assigned as weightassigned, performance_plan_obj.planYear as planyear, performance_plan_obj.performance_objective as performanceobjective, performance_plan_obj.performance_indicators as performanceindicators, performance_plan_obj.midYear as midyear, performance_plan_obj.finalyear as finalyear, performance_plan_obj.plan_id as planid, performance_plan_obj.user_id as userid, PlanScore.empactiontaken, PlanScore.supactiontaken, PlanScore.actual, PlanScore.total, planScore.performancePeriod, PlanScore.perfromanceobjid, PlanScore.finalScore as finalscore, SubmitPerformancePlan.submitted_time as submittedtime, SubmitPerformancePlan.level from performance_plan_obj left join PlanScore on performance_plan_obj.id = PlanScore.perfromanceobjid inner join SubmitPerformancePlan on performance_plan_obj.plan_id = SubmitPerformancePlan.plan_id where performance_plan_obj.plan_id =?1 AND SubmitPerformancePlan.level =?2", nativeQuery = true)
	List<ObjectiveScore> getSubmitedPerformanceObjForStaff1( String plan_id, int level);
}
