package com.okstatelibrary.doortrafficcounter.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.okstatelibrary.doortrafficcounter.service.HeadCountService;
import com.okstatelibrary.doortrafficcounter.util.DateUtil;


@RestController
@RequestMapping("/api")
public class ApiResource {

	@Autowired
	private HeadCountService headCountService;

	@RequestMapping(value = "/currenttraffic", method = RequestMethod.GET)
	public Index headcount() {
		
		return new Index(headCountService.getCount(DateUtil.getTodayDate()));
		
	}

	public class Index {

		public Index(Integer count) {
			this.count = count;
		}

		private Integer count;

		public String getDate() {

			long millis = System.currentTimeMillis();

			java.sql.Date date = new java.sql.Date(millis);

			return date.toString();
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

	}
}
