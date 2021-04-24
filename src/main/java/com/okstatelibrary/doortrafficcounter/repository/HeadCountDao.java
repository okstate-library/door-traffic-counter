package com.okstatelibrary.doortrafficcounter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.okstatelibrary.doortrafficcounter.entity.HeadCount;

public interface HeadCountDao extends CrudRepository<HeadCount, Long> {
    
	HeadCount findByDate(Date date);
	
	@Query(value = "SELECT count(1) FROM head_count WHERE date >= :startDate AND date <= :endDate", nativeQuery = true)
	Integer findCountByDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM head_count WHERE date >= :startDate AND date <= :endDate", nativeQuery = true)
	List<HeadCount> findByDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}