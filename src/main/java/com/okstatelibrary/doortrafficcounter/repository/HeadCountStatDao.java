package com.okstatelibrary.doortrafficcounter.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.okstatelibrary.doortrafficcounter.entity.HeadCountStat;

public interface HeadCountStatDao extends CrudRepository<HeadCountStat, Long> {

    List<HeadCountStat> findAll();
}