package com.okstatelibrary.doortrafficcounter.service;

import java.util.List;

public interface ReportService {

	List<Object[]> getStatByDateRange(String startDate, String endDate);

	List<Object[]> getStatByDate(String date);
}