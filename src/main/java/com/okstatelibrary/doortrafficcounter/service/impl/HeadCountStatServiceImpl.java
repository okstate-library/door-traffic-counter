package com.okstatelibrary.doortrafficcounter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okstatelibrary.doortrafficcounter.entity.HeadCountStat;
import com.okstatelibrary.doortrafficcounter.repository.HeadCountStatDao;
import com.okstatelibrary.doortrafficcounter.service.HeadCountStatService;
import com.okstatelibrary.doortrafficcounter.util.DateUtil;

@Service
public class HeadCountStatServiceImpl implements HeadCountStatService {

	@Autowired
	private HeadCountStatDao headCountStatDao;

	@Override
	public List<HeadCountStat> findByDate(Date date) {

		return (List<HeadCountStat>) headCountStatDao.findAll().stream()
				.filter(u -> DateUtil.getDateFormat().format(u.getDate()).equals(DateUtil.getDateFormat().format(date)))
				.collect(Collectors.toList());
	}

	@Override
	public HeadCountStat createHeadCountStat(HeadCountStat headCountStat) {

		return headCountStatDao.save(headCountStat);
	}

	@Override
	public Integer findAllCount(Date startDate, Date endDate) {
		return headCountStatDao.findCountByDates(startDate, endDate);
	}

	@Override
	public List<HeadCountStat> findAll(Date startDate, Date endDate) {
		return headCountStatDao.findByDates(startDate, endDate);
	}

	@Transactional
	@Override
	public void deleteAll(Date startDate, Date endDate) {
		headCountStatDao.deleteAll(startDate, endDate);
	}

	@Override
	public CompletableFuture<List<HeadCountStat>> findAllAsync(Date startDate, Date endDate) {

		List<HeadCountStat> list = headCountStatDao.findByDates(startDate, endDate);

		return CompletableFuture.completedFuture(list);
	}

}