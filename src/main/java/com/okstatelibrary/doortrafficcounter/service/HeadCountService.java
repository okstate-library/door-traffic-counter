package com.okstatelibrary.doortrafficcounter.service;

import java.util.Date;
import java.util.List;

import com.okstatelibrary.doortrafficcounter.entity.HeadCount;

public interface HeadCountService {

	Integer getCount(Date date);

	void Increment(Date date, Integer recordCount);

	void Decrement(Date date, Integer recordCount);

	void Reset(Date date);
	
	Integer findAllCount(Date startDate, Date endDate);
	
	List<HeadCount> findAll(Date startDate, Date endDate);

}