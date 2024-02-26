package com.appraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.appraisal.domain.Hrperiod;

public interface HrperiodRepository extends CrudRepository<Hrperiod, Long> {
	@Query(value="select * from Hrperiod where id =?1", nativeQuery = true)
	Hrperiod planPeriodCheck(int id);
	
	@Query(value="select * from Hrperiod where status='open'", nativeQuery = true)
	Hrperiod getperiod();
	
	@Query(value="select * from Hrperiod ", nativeQuery = true)
	List<Hrperiod> getallperiod();
	
	@Transactional
	@Modifying
	@Query(value="update Hrperiod set planYear = ?1", nativeQuery = true)
	void updateperiodyear(String planYear);
	
	@Transactional
	@Modifying
	@Query(value="update Hrperiod set status = ?1", nativeQuery = true)
	void updateallstatus(String status);
	
	@Transactional
	@Modifying
	@Query(value="update Hrperiod set status = ?1 where id = ?2", nativeQuery = true)
	void updateastatus(String status, int id);
}
