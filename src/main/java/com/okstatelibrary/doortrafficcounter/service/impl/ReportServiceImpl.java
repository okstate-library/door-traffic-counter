package com.okstatelibrary.doortrafficcounter.service.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.okstatelibrary.doortrafficcounter.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStatByDateRange(String startDate, String endDate) {

		StoredProcedureQuery query = em.createStoredProcedureQuery("getStatByDateRange");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

		// Pass the parameter values
		query.setParameter(1, startDate);
		query.setParameter(2, endDate);

		// Execute query
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getStatByDate(String date) {
		StoredProcedureQuery query = em.createStoredProcedureQuery("getStatByDate");

		// Declare the parameters in the same order
		query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);;

		// Pass the parameter values
		query.setParameter(1, date);

		// Execute query
		return query.getResultList();
	}

}