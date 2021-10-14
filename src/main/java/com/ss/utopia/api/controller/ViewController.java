package com.ss.utopia.api.controller;

import java.net.URI;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ss.utopia.api.pojo.User;
import com.ss.utopia.api.pojo.UserRole;
import com.ss.utopia.api.service.UserService;

@Controller
public class ViewController {

	
	@Autowired
	UserService user_service;
	
	
//	@RequestMapping(path="/")
//	public String home(HttpServletRequest request, Model model) {
//		
//		List<Route> routes = airline_service.findAllRoutes();
//		model.addAttribute("routes", routes);
//		return "home";
//	}
//	
//	@PostMapping("/process_registration")
//	public String addUser(User user){
//
//		user_service.save(user);
//		return "register_success";
//	
//	}
//	
//	
//	@RequestMapping(path="/register")
//	public String register(HttpServletRequest request, Model model) {
//		User user = new User();
//		model.addAttribute("user", user);
//		return "register";
//	}
//	
	@RequestMapping(path="/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	@RequestMapping(path="/agent")
	public @ResponseBody String agent() {
		return "agent";
	}
	
	@RequestMapping(path="/traveler")
	public @ResponseBody String traveler() {
		return "traveler";
	}
	
}
