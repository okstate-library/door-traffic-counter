package com.okstatelibrary.doortrafficcounter.entity;

import java.io.Serializable;

public class HeadCountReport implements Serializable {

	public HeadCountReport() {

	}

	public HeadCountReport(Object[] object) {
		this.setProcess_date(object[0].toString());
		this.setInoutcount(object);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String process_date;

	/**
	 * @return the process_date
	 */
	public String getProcess_date() {
		return process_date;
	}

	/**
	 * @param process_date the process_date to set
	 */
	public void setProcess_date(String process_date) {
		this.process_date = process_date;
	}

	/**
	 * @return the inoutcount
	 */
	public String[] getInoutcount() {
		return inoutcount;
	}

	/**
	 * @param inoutcount the inoutcount to set
	 */
	public void setInoutcount(Object[] object) {

		this.inoutcount = new String[24];

		for (int i = 1; i < object.length; i++) {

			if (object[i] != null) {
				// System.out.println(i + " -- " + object[i].toString());
				this.inoutcount[i - 1] = object[i].toString();
			}
		}

	}

	private String[] inoutcount;
}
