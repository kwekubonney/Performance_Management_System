package com.appraisal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.appraisal.domain.Staffcompetency;
import com.appraisal.domain.Supervisorcompetency;

public interface SupervisorcompetencyRepository extends JpaRepository<Supervisorcompetency, Long>{
	@Query(value ="select * from supervisorcompetency where supervisorid = ?1", nativeQuery = true) 
	  Supervisorcompetency findSupervisorCompetency(String userid); 
	
	 @Query(value ="select * from supervisorcompetency where supervisorid = ?1 and period = ?2", nativeQuery = true)
	 String checkSupervisorCompetency(String userid, String period);
}
