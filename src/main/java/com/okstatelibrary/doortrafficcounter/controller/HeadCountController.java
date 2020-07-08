package com.okstatelibrary.doortrafficcounter.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.okstatelibrary.doortrafficcounter.entity.HeadCountStat;
import com.okstatelibrary.doortrafficcounter.entity.User;
import com.okstatelibrary.doortrafficcounter.service.HeadCountService;
import com.okstatelibrary.doortrafficcounter.service.HeadCountStatService;
import com.okstatelibrary.doortrafficcounter.service.UserService;
import com.okstatelibrary.doortrafficcounter.util.DateUtil;
import com.okstatelibrary.doortrafficcounter.util.ExcelGenerator;
import com.okstatelibrary.doortrafficcounter.util.GraphDataModel;
import com.okstatelibrary.doortrafficcounter.util.StatDataModel;

import java.io.ByteArrayInputStream;
import org.springframework.http.HttpHeaders;

@Controller
@RequestMapping("/headcount")
public class HeadCountController {

	@Autowired
	private UserService userService;

	@Autowired
	private HeadCountStatService headCountStatService;

	@Autowired
	private HeadCountService headCountService;

	@RequestMapping("/stat")
	public String stat(@ModelAttribute("dateString") String dateString, Model model, Principal principal)
			throws ParseException {

		StatDataModel statListParams = getStatDataModel(dateString);

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		model.addAttribute("statList", statListParams.StatList);
		model.addAttribute("dateString", statListParams.DateString);

		setupTotalCount(statListParams.StatList, model);

		return "stat";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String reset(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		return "reset";
	}

	@RequestMapping(value = "/resetcount", method = RequestMethod.POST)
	public String resetcount(Principal principal) throws Exception {

		User user = userService.findByUsername(principal.getName());
		HeadCountStat headCountStat = new HeadCountStat(DateUtil.getTodayDate(), true, false, 0, 0, user.getUserId());

		headCountStatService.createHeadCountStat(headCountStat);

		headCountService.Reset(DateUtil.getTodayDate());

		return "redirect:/index";
	}

	@RequestMapping(value = "/enter", method = RequestMethod.POST)
	public String enterconut(Principal principal) throws Exception {

		User user = userService.findByUsername(principal.getName());

		modifyCount(true, user.getUserId());

		return "redirect:/index";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public String exitcount(Principal principal) throws Exception {

		User user = userService.findByUsername(principal.getName());

		modifyCount(false, user.getUserId());

		return "redirect:/index";
	}

	@RequestMapping(value = "/bargraph")
	public String barGraph(@ModelAttribute("dateString") String dateString, Model model, Principal principal)
			throws ParseException {

		GraphDataModel getGraphDataModel = getGraphDataModel(dateString);

		User user = userService.findByUsername(principal.getName());

		model.addAttribute("user", user);
		model.addAttribute("dateString", getGraphDataModel.StatDataModel.DateString);

		model.addAttribute("categories", getGraphDataModel.Categories);
		model.addAttribute("liveCountMap", getGraphDataModel.LiveCountMap);
		model.addAttribute("resetCountMap", getGraphDataModel.ResetMap);
		model.addAttribute("enterMap", getGraphDataModel.EntryMap);
		model.addAttribute("exitMap", getGraphDataModel.ExitMap);

		setupTotalCount(getGraphDataModel.StatDataModel.StatList, model);

		return "bargraph";
	}

	@GetMapping(value = "/stat_excel")
	public ResponseEntity<InputStreamResource> excelStatReport(@ModelAttribute("dateString") String dateString,
			Model model) throws IOException {

		StatDataModel statListParams = getStatDataModel(dateString);

		ByteArrayInputStream in = ExcelGenerator.statDataToExcel(statListParams.StatList);

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Disposition", "attachment; filename=" + dateString + "_movements.xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	@GetMapping(value = "/graph_excel")
	public ResponseEntity<InputStreamResource> excelGraphReport(@ModelAttribute("dateString") String dateString,
			Model model) throws IOException {

		GraphDataModel getGraphDataModel = getGraphDataModel(dateString);

		ByteArrayInputStream in = ExcelGenerator.GraphDataToExcel(getGraphDataModel);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + dateString + "_time_movements.xlsx");

		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}

	private GraphDataModel getGraphDataModel(String dateString) {

		GraphDataModel graphDataModel = new GraphDataModel();

		StatDataModel statDataModel = getStatDataModel(dateString);

		Map<Integer, Integer> liveCountMap = new LinkedHashMap<>();
		Map<Integer, Integer> entryMap = new LinkedHashMap<>();
		Map<Integer, Integer> exitMap = new LinkedHashMap<>();
		Map<Integer, Integer> resetMap = new LinkedHashMap<>();

		for (int i = 0; i < 24; i++) {

			if (statDataModel.IsToday && DateUtil.getCurrentHour() < i) {
				break;
			}

			liveCountMap.put(i, 0);
			entryMap.put(i, 0);
			exitMap.put(i, 0);
			resetMap.put(i, 0);
		}

		boolean initialLiveCountGot = false;
		Integer initialLiveCount = 0;

		if (statDataModel.StatList != null && statDataModel.StatList.size() > 0) {

			for (HeadCountStat headCountStat : statDataModel.StatList) {

				if (!initialLiveCountGot) {
					initialLiveCount = headCountStat.getLiveCount();
					initialLiveCountGot = true;
				}

				Integer key = headCountStat.getDate().getHours();
				Integer count = headCountStat.getCount();

				if (headCountStat.isReset()) {
					resetMap.put(key, resetMap.get(key) + 1);
				} else {
					if (headCountStat.isEntry()) {
						entryMap.put(key, entryMap.get(key) + count);
					} else {
						exitMap.put(key, exitMap.get(key) + count);
					}
				}
			}
		}

		for (int i = 0; i < 24; i++) {

			if (statDataModel.IsToday && DateUtil.getCurrentHour() < i) {
				break;
			}

			if (entryMap.get(i) > 0 || exitMap.get(i) > 0) {
				liveCountMap.put(i, initialLiveCount);
				initialLiveCount = initialLiveCount + (entryMap.get(i) - exitMap.get(i));
			} else {
				liveCountMap.put(i, initialLiveCount);
			}

			if (resetMap.get(i) > 0) {
				initialLiveCount = 0;
			}
		}

		graphDataModel.StatDataModel = statDataModel;
		graphDataModel.LiveCountMap = liveCountMap;
		graphDataModel.ResetMap = resetMap;
		graphDataModel.EntryMap = entryMap;
		graphDataModel.ExitMap = exitMap;

		return graphDataModel;
	}

	private StatDataModel getStatDataModel(String dateString) {
		StatDataModel statDataModel = new StatDataModel();

		Date date = DateUtil.getTodayDate();

		if (dateString.isEmpty()) {
			dateString = DateUtil.getTodayDate().toString();

		} else {
			date = java.sql.Date.valueOf(dateString);
		}

		if (DateUtil.compareDates(date) == 0) {
			statDataModel.IsToday = true;
		}

		statDataModel.DateString = dateString;

		statDataModel.StatList = headCountStatService.findByDate(date);

		return statDataModel;
	}

	private void setupTotalCount(List<HeadCountStat> statList, Model model) {
		
		Long entryCount = statList.stream().filter(s -> s.isEntry()).count();
		Long exitCount = statList.stream().filter(s -> !s.isReset() && !s.isEntry()).count();

		model.addAttribute("entryCount", entryCount);
		model.addAttribute("exitCount", exitCount);
	}

	private void modifyCount(boolean isEntrance, Long userId) {

		Integer liveCount = headCountService.getCount(DateUtil.getTodayDate());

		HeadCountStat headCountStat = new HeadCountStat(DateUtil.getTodayDate(), false, isEntrance, liveCount, 1,
				userId);

		headCountStatService.createHeadCountStat(headCountStat);

		if (isEntrance) {
			headCountService.Increment(DateUtil.getTodayDate(), 1);
		} else {
			headCountService.Decrement(DateUtil.getTodayDate(), 1);
		}
	}
}