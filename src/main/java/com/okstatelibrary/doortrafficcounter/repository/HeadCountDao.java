package com.okstatelibrary.doortrafficcounter.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.okstatelibrary.doortrafficcounter.entity.HeadCount;

public interface HeadCountDao extends CrudRepository<HeadCount, Long> {
    
	HeadCount findByDate(Date date);
}