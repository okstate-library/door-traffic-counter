package com.okstatelibrary.doortrafficcounter.util;

import java.util.Map;

public class GraphDataModel {

	public String[] Categories = { "12am - 1am", "1am - 2am", "2am - 3am", "3am - 4am", "4am - 5am", "5am - 6am",
			"6am - 7am", "7am - 8am", "8am - 9am", "9am - 10am", "10am - 11am", "11am - 12pm", "12pm - 1pm",
			"1pm - 2pm", "2pm - 3pm", "3pm - 4pm", "4pm - 5pm", "5pm - 6pm", "6pm - 7pm", "7pm - 8pm", "8pm - 9pm",
			"9pm - 10pm", "10pm - 11pm", "11pm - 12am" };

	public Map<Integer, Integer> LiveCountMap;
	
	public Map<Integer, Integer> ResetMap;
	
	public Map<Integer, Integer> EntryMap;

	public Map<Integer, Integer> ExitMap;

	public StatDataModel StatDataModel;
}
