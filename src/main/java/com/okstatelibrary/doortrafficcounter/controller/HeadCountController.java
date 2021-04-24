package com.okstatelibrary.doortrafficcounter.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/headcount")
public class HeadCountController {

	private String message = "";

	@Autowired
	private UserService userService;

	@Autowired
	private HeadCountStatService headCountStatService;

	@Autowired
	private HeadCountService headCountService;

	/**
	 * Staticics
	 * 
	 * @param dateString
	 * @param model
	 * @param principal
	 * @return
	 * @throws ParseException
	 */
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

	@RequestMapping(value = "/semreset", method = RequestMethod.GET)
	public String semreset(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		messageSetup(model);

		return "semreset";
	}

	@RequestMapping(value = "/semreset", method = RequestMethod.POST)
	public String semresetPost(@ModelAttribute("startDateString") String startDateString,
			@ModelAttribute("endDateString") String endDateString, Model model, Principal principal,
			HttpServletRequest request) throws Exception {

		User user = userService.findByUsername(principal.getName());
		model.addAttribute("user", user);

		Date startDate = java.sql.Date.valueOf(startDateString);
		Date endDate = DateUtil.getLongDate(endDateString);

		int count = headCountStatService.findAllCount(startDate, endDate);

		model.addAttribute("recordcount", count);

		HttpSession session = request.getSession();
		session.setAttribute("startDate", startDateString);
		session.setAttribute("endDate", endDateString);

		return "semreset";
	}

	@PostMapping("/downloadtraffic")
	public ResponseEntity<Object> downloadreports(HttpServletRequest request, @RequestParam String action)
			throws IOException {

		try {

			HttpSession session = request.getSession();

			String startDateString = (String) session.getAttribute("startDate");
			String endDateString = (String) session.getAttribute("endDate");

			Date startDate = java.sql.Date.valueOf(startDateString);
			Date endDate = DateUtil.getLongDate(endDateString);

			List<HeadCountStat> list = headCountStatService.findAll(startDate, endDate);

			ByteArrayInputStream in = ExcelGenerator.statDataToExcel(list);

			HttpHeaders headers = new HttpHeaders();

			headers.add("Content-Disposition",
					"attachment; filename=" + startDateString + "_" + endDateString + "_trafficcount.xlsx");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("Number of records cannot handle to the server. Shorten the period of time and try again.");
		}

	}

	@PostMapping("/deletetraffic")
	public String deletetraffic(HttpServletRequest request, @RequestParam String action) throws IOException {

		HttpSession session = request.getSession();

		String startDateString = (String) session.getAttribute("startDate");
		String endDateString = (String) session.getAttribute("endDate");

		Date startDate = java.sql.Date.valueOf(startDateString);
		Date endDate = DateUtil.getLongDate(endDateString);

		System.out.println("startDate" + startDate);
		System.out.println("endDate" + endDate);

		headCountStatService.deleteAll(startDate, endDate);

		message = "Records deleted from system successfully!";

		return "redirect:/headcount/semreset";

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
	public String enterconut(@RequestParam("count") Integer count, Principal principal) throws Exception {

		User user = userService.findByUsername(principal.getName());

		modifyCount(true, count, user.getUserId());

		return "redirect:/index";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public String exitcount(@RequestParam("count") Integer count, Principal principal) throws Exception {

		User user = userService.findByUsername(principal.getName());

		modifyCount(false, count, user.getUserId());

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

				@SuppressWarnings("deprecation")
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

	private void modifyCount(boolean isEntrance, Integer count, Long userId) {

		Integer liveCount = headCountService.getCount(DateUtil.getTodayDate());

		HeadCountStat headCountStat = new HeadCountStat(DateUtil.getTodayDate(), false, isEntrance, liveCount, count,
				userId);

		headCountStatService.createHeadCountStat(headCountStat);

		if (isEntrance) {
			headCountService.Increment(DateUtil.getTodayDate(), count);
		} else {
			headCountService.Decrement(DateUtil.getTodayDate(), count);
		}
	}

	private void messageSetup(Model model) {
		if (!message.equals(null) || !message.equals("")) {

			model.addAttribute("errorMessage", message);

			message = "";
		}
	}
}