package com.okstatelibrary.doortrafficcounter.service;

import java.util.Date;
import java.util.List;

import com.okstatelibrary.doortrafficcounter.entity.HeadCountStat;

public interface HeadCountStatService {

	List<HeadCountStat> findByDate(Date date);

	HeadCountStat createHeadCountStat(HeadCountStat headCountStat);

	Integer findAllCount(Date startDate, Date endDate);
	
	List<HeadCountStat> findAll(Date startDate, Date endDate);
	
	void deleteAll(Date startDate, Date endDate);
}