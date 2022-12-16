package com.okstatelibrary.doortrafficcounter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import com.okstatelibrary.doortrafficcounter.entity.HeadCountReport;

public interface ReportDao {

	@Query(nativeQuery = true, value = "call getDetails(:start_date ,end_date )")
	List<HeadCountReport> getDetails(String start_date, String end_date);

	@Query(nativeQuery = true, value = "call getStatByDate(:start_date ,end_date )")
	List<HeadCountReport> getStatByDate(String sel_date);

}