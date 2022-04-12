package com.student.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.student.dao.AdminRepository;
import com.student.dao.AttendanceDetailsRepository;
import com.student.dao.AttendanceRepository;
import com.student.dao.MarksRepository;
import com.student.dao.StudentRepository;
import com.student.entities.Admins;
import com.student.entities.Attendance;
import com.student.entities.AttendanceDetails;
import com.student.entities.Marks;
import com.student.entities.StudentDetails;
import com.student.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private MarksRepository marksRepository;
	
	@Autowired
	private AttendanceDetailsRepository attendancedetailsRepository;
	
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal){
	
	String userName=principal.getName();
	System.out.println("USERNAME"+userName);
	Admins admin=adminRepository.getAdminsByuserName(userName);
	System.out.println("ADMIN"+admin);
	model.addAttribute("admin",admin);
	return "user-home";
		}
	
@RequestMapping("/home")
public String home(Model model,Principal principal) {
	String userName=principal.getName();
	System.out.println("USERNAME"+userName);
	 Admins admin=adminRepository.getAdminsByuserName(userName);
	System.out.println("ADMIN"+admin);
	model.addAttribute("admin",admin);
	return "user-home";
		}


@RequestMapping("/services")
public String about(Model model) {
	model.addAttribute("title", "Services - Student Portal");
	return "user-services";
}
@RequestMapping("/show_userattendance")
public String showAttendance(Model m,Principal principal)
{	
	m.addAttribute("title","Show-Attendance");
	String userName=principal.getName();
	Admins admin=this.adminRepository.getAdminsByuserName(userName);
	List<Attendance> attendance=this.attendanceRepository.findAttendanceByemailId(admin.getEmail());
	m.addAttribute("attendance",attendance);
	System.out.println("ADMIN"+attendance);
	System.out.println("ADMIN"+admin.getEmail());
	m.addAttribute("emailid",admin.getEmail());
	return "user-attendance";	
}
@RequestMapping("/show_usermarks")
public String showMarks(Model m,Principal principal)
{	
	m.addAttribute("title","Show-Attendance");
	String userName=principal.getName();
	Admins admin=this.adminRepository.getAdminsByuserName(userName);
	List<Marks> marks=this.marksRepository.findMarksByemailId(admin.getEmail());
	m.addAttribute("marks",marks);
	return "user-marks";	
}
//open settings handler
	@GetMapping("/settings")
	public String openSettings() {
		return "user_settings";
	}
	// change password..handler
		@PostMapping("/change-password")
		public String changePassword(@RequestParam("oldPassword") String oldPassword,
				@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
			System.out.println("OLD PASSWORD " + oldPassword);
			System.out.println("NEW PASSWORD " + newPassword);

			String userName = principal.getName();
			Admins currentUser = this.adminRepository.getAdminsByuserName(userName);
			System.out.println(currentUser.getPassword());

			if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
				// change the password

				currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
				this.adminRepository.save(currentUser);
				session.setAttribute("message", new Message("Your password is successfully changed..", "success"));

			} else {
				// error...
				session.setAttribute("message", new Message("Please Enter correct old password !!", "danger"));
				return "redirect:/user/settings";
			}

			return "redirect:/user/index";
		}
		
		
		@RequestMapping("/studentinfocenter")
		public String studentinfo(Model model,Principal principal) {
			model.addAttribute("title", "Student info center - Student Portal");
			String userName=principal.getName();
			java.util.Optional<StudentDetails> studentOptional=this.studentRepository.findStudentsByEmailIdShow(userName);
			 StudentDetails studentinfo=studentOptional.get();
			 model.addAttribute("studentinfo", studentinfo);
			return "user-studentinfocenter";
		}
		 @RequestMapping("/{emailid}/attendance/{subid}")
		 public String showAttendanceDates(@PathVariable("emailid") String emailid,@PathVariable("subid") Integer subid,Model model,@RequestParam(value= "present",defaultValue = "false")boolean present,
					@RequestParam(value= "absent",defaultValue = "false")boolean absent)
		 { 
			 List<Attendance> attendanceOptional=this.attendanceRepository.findAttendanceByEmailId(emailid);
			 List<AttendanceDetails> attendancedetails=this.attendancedetailsRepository.findAttendanceBysubId(subid);
			 System.out.println("subid="+subid);
			 model.addAttribute("attendancedate",attendancedetails);
				
			 return "user-attendance_date";
		 }
		 
		 @RequestMapping("/{emailid}/attendance-report/{subid}")
		 public String showAttendance(@PathVariable("emailid") String emailid,@PathVariable("subid") Integer subid,Model model,@RequestParam(value= "present",defaultValue = "false")boolean present,
					@RequestParam(value= "absent",defaultValue = "false")boolean absent)
		 {
			 float a=0;
			 int p=0;
			 float c=0;
			 float t=0;
			 List<Attendance> attendanceOptional=this.attendanceRepository.findAttendanceByEmailId(emailid);
			 List<AttendanceDetails> attendancedetails=this.attendancedetailsRepository.findAttendanceBysubId(subid);
			 for (int x=0; x<attendancedetails.size(); x++)
			 { AttendanceDetails att=attendancedetails.get(x);
//				    System.out.println("hello=="+attendancedetails.get(x));
				    System.out.println("attend===="+att.getAttends());
				    if(att.getAttends().contentEquals("PRESENT"))
				    {
				    	++p;
				    }
				    else if(att.getAttends().contentEquals("ABSENT"))
				    {
				    	++a;
				    }
			 }
			 t=a+p;
			 c=Math.round((p/t)*100);
			 model.addAttribute("absent",a);
			 model.addAttribute("present",p);
			 model.addAttribute("TotalAttendance",a+p);
			 model.addAttribute("subid",attendancedetails);
			 model.addAttribute("Percentage",c);

			return"user-attendancereport"; 
		 }
	 
}
