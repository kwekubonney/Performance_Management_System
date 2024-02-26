package com.appraisal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.appraisal.domain.Period;


public interface PeriodRepository extends CrudRepository<Period, Long> {
	

	  @Query(value ="select top 1 period from [Period] where status = 'Open' and moduleId=?1 order by dateUpdated desc", nativeQuery = true) 
	  String getOpenModule(int module);
	  
	  
	
}
