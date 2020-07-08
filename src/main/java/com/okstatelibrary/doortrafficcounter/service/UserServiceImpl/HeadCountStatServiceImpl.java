package com.okstatelibrary.doortrafficcounter.service.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
				.filter(u -> DateUtil.getDateFormat().format( u.getDate())
						.equals(DateUtil.getDateFormat().format(date))).collect(Collectors.toList());
	}

	@Override
	public HeadCountStat createHeadCountStat(HeadCountStat headCountStat) {

		return headCountStatDao.save(headCountStat);
	}
	
}