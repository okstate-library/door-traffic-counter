package com.okstatelibrary.doortrafficcounter.service;

import java.util.Date;

public interface HeadCountService {

	Integer getCount(Date date);

	void Increment(Date date, Integer recordCount);

	void Decrement(Date date, Integer recordCount);

	void Reset(Date date);

}