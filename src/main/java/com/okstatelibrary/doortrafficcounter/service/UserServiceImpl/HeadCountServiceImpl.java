package com.okstatelibrary.doortrafficcounter.service.UserServiceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okstatelibrary.doortrafficcounter.entity.HeadCount;
import com.okstatelibrary.doortrafficcounter.repository.HeadCountDao;
import com.okstatelibrary.doortrafficcounter.service.HeadCountService;
import com.okstatelibrary.doortrafficcounter.util.DateUtil;

@Service
public class HeadCountServiceImpl implements HeadCountService {

	// Integer headCount = -1;

	@Autowired
	private HeadCountDao headCountDao;

	@Override
	public Integer getCount(Date date) {

		return getHeadCount(date).getCount();
//		if (headCount == -1)
//			headCount = getHeadCount(date).getCount();
//
//		return headCount;
	}

	@Override
	public void Increment(Date date, Integer recordCount) {
		// initGetCount();

		HeadCount headCount = getHeadCount(date);

		headCount.incrementCount(recordCount);

		headCountDao.save(headCount);
	}

	@Override
	public void Decrement(Date date, Integer recordCount) {
		// initGetCount();
		HeadCount headCount = getHeadCount(date);

		headCount.decrementCount(recordCount);

		headCountDao.save(headCount);
	}

	private HeadCount getHeadCount(Date date) {

		HeadCount headcount = headCountDao.findByDate(date);

		if (headcount == null) {
			
			Date oneDayBefore = DateUtil.getPreviousDate(date);

			HeadCount headcounYesterday = headCountDao.findByDate(oneDayBefore);

			Integer previousDayCount = 0;

			if (headcounYesterday != null) {
				previousDayCount = headcounYesterday.getCount();
			}

			headcount = new HeadCount();
			headcount.setCount(previousDayCount);
			headcount.setDate(java.sql.Date.valueOf(date.toString()));

			headcount = headCountDao.save(headcount);
		}

		return headcount;
	}

	@Override
	public void Reset(Date date) {
		initGetCount();

		HeadCount headCount = getHeadCount(date);

		headCount.setCount(0);

		headCountDao.save(headCount);

	}

	private void initGetCount() {
		// headCount = -1;
	}

}