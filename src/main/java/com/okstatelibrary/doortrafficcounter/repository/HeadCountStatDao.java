package com.okstatelibrary.doortrafficcounter.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.okstatelibrary.doortrafficcounter.entity.HeadCountStat;

public interface HeadCountStatDao extends CrudRepository<HeadCountStat, Long> {

    List<HeadCountStat> findAll();
    
    @Query(value = "SELECT count(1) FROM head_count_stat WHERE date >= :startDate AND date <= :endDate", nativeQuery = true)
	Integer findCountByDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM head_count_stat WHERE date >= :startDate AND date <= :endDate", nativeQuery = true)
	List<HeadCountStat> findByDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


	@Modifying
	@Query(value = "DELETE FROM head_count_stat WHERE date > :startDate AND date < :endDate", nativeQuery = true)
	void deleteAll(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}