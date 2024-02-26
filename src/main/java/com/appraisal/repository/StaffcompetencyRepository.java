package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.Staffcompetency;
import com.appraisal.domain.SubmitEvaluation;

public interface StaffcompetencyRepository extends JpaRepository<Staffcompetency, Long> {

	 @Query(value ="select * from Staffcompetency where staffid = ?1 and competencyYear = ?2", nativeQuery = true) 
	  Staffcompetency findStaffCompetency(String userid, String period); 
	 
	 @Query(value ="select * from staffcompetency where staffid = ?1 and competencyYear = ?2 and period=?3", nativeQuery = true)
	 Staffcompetency checkCompetency(String userid, String period, String performanceperiod);
	 
	 @Query(value = "SELECT * FROM Staffcompetency where competencyYear = ?1 and staffid = ?2", nativeQuery = true)
		Staffcompetency getSubmittedEvaluation(String period, String employee_id);
	 
	 @Query(value ="SELECT CAST(label1 AS INT) + CAST(label2 AS INT) + CAST(label3 AS INT) + CAST(label4 AS INT) + CAST(label5 AS INT) + CAST(label6 AS INT) + CAST(label7 AS INT) + CAST(label8 AS INT) + CAST(labeL9 AS INT) + CAST(label10 AS INT) AS total\r\n" + 
	 		"FROM Staffcompetency where staffid =?1 and competencyYear =?2 and period=?3", nativeQuery = true)
	 String staffCompetencyTotal(String userid, String competencyyear, String period);
	 
	 @Query(value="select * from Staffcompetency where id =?1", nativeQuery = true)
	  Staffcompetency getStaffCompetencyById(long id);
	 
	 
	 @Transactional
	  @Modifying
	  @Query(value="update Staffcompetency set level =?1 where id =?2", nativeQuery = true)
	  void staffEvaluationUpdatLevel(int level, long id);
	 
	 @Transactional
	 @Modifying
	 @Query(value="delete Staffcompetency where id=?1", nativeQuery = true)
	 void resetCompetency(Long id);
	 
	 
}
