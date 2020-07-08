package com.okstatelibrary.doortrafficcounter.controller;

import com.okstatelibrary.doortrafficcounter.entity.User;
import com.okstatelibrary.doortrafficcounter.repository.RoleDao;
import com.okstatelibrary.doortrafficcounter.security.Role;
import com.okstatelibrary.doortrafficcounter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleDao roleDao;

	private String message = "";

	@GetMapping("/profile")
	public String profile(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());

		model.addAttribute("user", user);

		messageSetup(model);
		
		return "profile";
	}

	@PostMapping("/profile")
	public String profilePost(@ModelAttribute("user") User newUser, Model model) {

		User user = userService.findByUsername(newUser.getUsername());
		user.setUsername(newUser.getUsername());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setEmail(newUser.getEmail());

		model.addAttribute("user", user);

		userService.saveUser(user);

		message = "User details changed successfully!";
		
		
		return "redirect:/user/profile";
	}

	@GetMapping("/adduser")
	public String adduser(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());

		model.addAttribute("user", user);

		return "adduser";
	}

	@PostMapping("/adduser")
	public String adduser(@ModelAttribute("user") User user, Model model) {

		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {

			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}

			if (userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}

			return "adduser";
		} else {

			Role role = roleDao.findByName("ROLE_USER");

			user.setRoleId(role.getRoleId());

			userService.createUser(user);

			message = "User details saved successfully!";
			
			return "redirect:/user/users";
		}
	}

	@PostMapping("/updateuser")
	public String update(@ModelAttribute("user") User newUser, Model model) {

		User user = userService.findByUsername(newUser.getUsername());
		
		user.setUsername(newUser.getUsername());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setEmail(newUser.getEmail());

		model.addAttribute("user", user);

		userService.saveUser(user);

		message = "User details changed successfully!";
		
		return "redirect:/user/details/" + user.getUsername();
	}
	
	@GetMapping("/users")
	public String users(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());

		List<User> users = userService.findUserList();

		model.addAttribute("user", user);
		model.addAttribute("userList", users);

		messageSetup(model);
		
		return "users";
	}

	@RequestMapping("details/{username}")
	public String details(@PathVariable String username, Principal principal, Model model) {

		User user = userService.findByUsername(principal.getName());
		User detailUser = userService.findByUsername(username);

		model.addAttribute("user", user);
		model.addAttribute("userdetails", detailUser);

		messageSetup(model);
		
		return "userdetails";
	}

	@PostMapping("/updatestatus")
	public String updatestatus(@ModelAttribute("user") User newUser, Model model) {

		User user = userService.findByUsername(newUser.getUsername());

		user.setEnabled(!user.isEnabled());

		userService.saveUser(user);

		message = "Update user status successfully!";

		return "redirect:/user/details/" + user.getUsername();
	}

	@PostMapping("/resetpasswordbyuser")
	public String resetpasswordbyuser(@ModelAttribute("user") User newUser, Model model) {

		String username = newUser.getUsername();

		updatePassword(username, newUser.getPassword());

		message = "Password changed successfully!";
		
		return "redirect:/user/profile";
	}

	@PostMapping("/resetpassword")
	public String resetpassword(@ModelAttribute("user") User newUser, Model model) {

		String username = newUser.getUsername();

		updatePassword(username, "abc123");

		message = "Reset password successfully!";

		return "redirect:/user/details/" + username;

	}

	@PostMapping("/deleteuser")
	public String deleteuser(@ModelAttribute("user") User newUser, Model model) {

		User user = userService.findByUsername(newUser.getUsername());

		user.setDeleted(true);

		userService.saveUser(user);

		message = "Delete user successfully!";

		return "redirect:/user/users";
	}

	private void messageSetup(Model model) {
		if (!message.equals(null) || !message.equals("")) {

			model.addAttribute("errorMessage", message);

			message = "";
		}
	}

	private void updatePassword(String username, String newPassword) {
		User user = userService.findByUsername(username);

		user.setPassword(newPassword);

		userService.updatePassword(user);
	}

}