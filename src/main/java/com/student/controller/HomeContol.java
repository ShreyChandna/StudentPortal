package com.student.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.student.dao.AdminRepository;
import com.student.entities.Admins;
import com.student.helper.Message;

@Controller
public class HomeContol {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@RequestMapping("/")
	public String login(Model model) {
		model.addAttribute("title", "Home - Student Portal");
		return "login";
	}
	@RequestMapping("/login2")
	public String login2(Model model) {
		model.addAttribute("title", "SIGNUP - Student Portal");
		model.addAttribute("Admins", new Admins());
		return "login2";
	}
	

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "SIGNUP - Student Portal");
		model.addAttribute("admin", new Admins());
		return "signup";
	}
	
	// handler for registering user
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("admin") Admins admin, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
			@RequestParam(value= "radio",defaultValue = "false")boolean radio,
			@RequestParam(value= "radiouser",defaultValue = "false")boolean radiouser,
			Model model,
			HttpSession session) {
		
		
		try {	
			if (!agreement) {
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			if (result1.hasErrors()) {
				System.out.println("ERROR " + result1.toString());
				model.addAttribute("admin", admin);
				return "signup";
			}
			if(radiouser)
			{
			admin.setRole("ROLE_USER");
			}
			if(radio)
			{admin.setRole("ROLE_ADMIN");
			}
			
			admin.setEnabled(true);
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			
			System.out.println("Agreement " + agreement);
			System.out.println("ADMIN" + admin);
		

			Admins result = this.adminRepository.save(admin);
			
			model.addAttribute("admin", new Admins());

			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("admin", admin);
			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
		}
		
		

	//handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		model.addAttribute("title","Login Page");
		return "login";
	}
}
