package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.appraisal.domain.EvaluationComments;

public interface EvaluationCommentsRepository extends CrudRepository<EvaluationComments, Long>{

	@Query(value ="select * from EvaluationComments join hr_Employee on EvaluationComments.employeeId = hr_Employee.employee_id where moduleOperationId=?1 order by dateUpdated desc", nativeQuery = true) 
	List<EvaluationComments> showEvaluationComments(String moduleOperationId);
}						
