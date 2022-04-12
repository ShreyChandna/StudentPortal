package com.student.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.student.dao.AdminRepository;
import com.student.entities.Admins;
import com.student.service.EmailService;
@Controller
public class ForgotController {
Random random = new Random(1000);
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	
	//email id form open  handler
		@RequestMapping("/forgot")
		public String openEmailForm()
		{
			return "forgot_email_form";
		}
		@PostMapping("/send-otp")
		public String sendOTP(@RequestParam("email") String email,HttpSession session)
		{
			System.out.println("EMAIL "+email);
			
			//generating otp of 4 digit
			
			
			int otp = random.nextInt(999999);
			
			System.out.println("OTP "+otp);
			
			
			//write code for send otp to email...
			
			
			String subject="OTP From STUDENT PORTAL";
			String message=""
					+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
					+ "<h1>"
					+ "OTP is "
					+ "<b>"+otp
					+ "</n>"
					+ "</h1> "
					+ "</div>";
			String to=email;
			
			boolean flag = this.emailService.sendEmail(subject, message, to);
			
			if(flag)
			{
				
				session.setAttribute("myotp", otp);
				session.setAttribute("email", email);
				return "verify_otp";
				
			}else
			{	
				
				session.setAttribute("message", "Check your email id !!");
				
				return "forgot_email_form";
			}
			
			
		}
		
		//verify otp
		@PostMapping("/verify-otp")
		public String verifyOtp(@RequestParam("otp") int otp,HttpSession session)
		{
			int myOtp=(int)session.getAttribute("myotp");
			
			System.out.println("User OTP "+otp);
			System.out.println("Our OTP "+myOtp);
			
			String email=(String)session.getAttribute("email");
			if(myOtp==otp)
			{
				//password change form 
				Admins admin = this.adminRepository.getAdminsByuserName(email);
				
				if(admin==null)
				{
					//send error message
					session.setAttribute("message", "User does not exits with this email !!");
					
					return "forgot_email_form";
				}
				else
				{
					//
					//send change password form
					
				}
				
				
				
				return "password_change_form";
			}else
			{
				session.setAttribute("message", "You have entered wrong otp !!");
				return "verify_otp";
			}
		}
		
		//change password
		@PostMapping("/change-password")
		public String changePassword(@RequestParam("newpassword") String newpassword,HttpSession session)
		{
			String email=(String)session.getAttribute("email");
			Admins admin = this.adminRepository.getAdminsByuserName(email);
			admin.setPassword(this.bcrypt.encode(newpassword));
			this.adminRepository.save(admin);
			return "redirect:/signin?change=password changed successfully..";
			
		}
		
		
	}

