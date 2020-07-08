package com.okstatelibrary.doortrafficcounter.controller;

import java.security.Principal;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.okstatelibrary.doortrafficcounter.entity.User;
import com.okstatelibrary.doortrafficcounter.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index() {
		return "redirect:/index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/index")
	public String index(Principal principal, Model model) throws ParseException {

		User user = null;
		
		if	(principal != null )
		{
			user = userService.findByUsername(principal.getName());
		}

		model.addAttribute("user", user);

		return "index";
	}

}