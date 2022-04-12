package com.student.controller;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/admin")
public class AdminController {
	Integer a=(int) 0f;
	Integer b= (int) 0;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AttendanceRepository attendanceRepository;
	
	@Autowired
	private AttendanceDetailsRepository attendancedetailsRepository;
	
	@Autowired
	private MarksRepository marksRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
@RequestMapping("/index")
	public String dashboard(Model model,Principal principal){
	
	String userName=principal.getName();
	System.out.println("USERNAME"+userName);
	Admins admin=adminRepository.getAdminsByuserName(userName);
	System.out.println("ADMIN"+admin);
	model.addAttribute("admin",admin);
	return "home";
		}
@RequestMapping("/home")
public String home(Model model,Principal principal) {
	String userName=principal.getName();
	System.out.println("USERNAME"+userName);
	 Admins admin=adminRepository.getAdminsByuserName(userName);
	System.out.println("ADMIN"+admin);
	model.addAttribute("admin",admin);
	return "home";
		}


@RequestMapping("/services")
public String about(Model model) {
	model.addAttribute("title", "Services - Student Portal");
	return "services";
}
@RequestMapping("/studentinfocenter")
public String studentinfo(Model model) {
	model.addAttribute("title", "Student info center - Student Portal");
	return "studentinfo";
}
@GetMapping("/add-student")
public String openAddStudentForm(Model model)
{
	model.addAttribute("title", "Add-Student");
	model.addAttribute("studentdetails", new StudentDetails());
	return "add-student";
}
@PostMapping("/process-student")
public String processStudent(@ModelAttribute StudentDetails studentdetails,Principal principal, HttpSession session)
{ try {
	String name=principal.getName();
	Admins admin=this.adminRepository.getAdminsByuserName(name);
	studentdetails.setAdmin(admin);
	admin.getStudentdetails().add(studentdetails);
	this.adminRepository.save(admin);
	System.out.println("DATA"+studentdetails);
	//message success.......
	session.setAttribute("message", new Message("Student Added", "success"));

} catch (Exception e) {
	System.out.println("ERROR " + e.getMessage());
	e.printStackTrace();
	// message error
	session.setAttribute("message", new Message("Some went wrong !! Try again..", "danger"));

}
	return "add-student";
}
@RequestMapping("/show_students/{page}")
public String showStudents(@PathVariable("page") Integer page,Model m,Principal principal)
{	
	m.addAttribute("title","Show-Students");
	String userName=principal.getName();
	Admins admin=this.adminRepository.getAdminsByuserName(userName);
	Pageable pageable=PageRequest.of(page, 5);
	Page<StudentDetails> studentdetails=this.studentRepository.findAll(pageable);
	m.addAttribute("studentdetails",studentdetails);
	System.out.println("studentdetails " +studentdetails);
	m.addAttribute("currentpage",page);
	m.addAttribute("totalPages",studentdetails.getTotalPages());
	return "show_students";	
}
 @GetMapping("/delete/{emailid}")
 public String deleteStudent(@PathVariable("emailid") String emailid,Model model,HttpSession session,Principal principal)
 {
	 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
	 this.studentRepository.delete(studentdetails);
	 session.setAttribute("message", new Message("Successfully Deleted !!", "alert-success"));
	 return "redirect:/admin/show_students/0";
 }
 
 @PostMapping("/update-student/{emailid}")
 public String updateForm(@PathVariable("emailid") String emailid,Model m)
 {
	 m.addAttribute("title","update contact");
	 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
	 m.addAttribute("studentdetails",studentdetails);
	 m.addAttribute("stud", studentdetails);
	 return "update_form";
 }
 
 @RequestMapping(value= "/process-update/{emailid}",method=RequestMethod.POST)
 public String updateHandler(@PathVariable("emailid") String emailid,@ModelAttribute StudentDetails studentdetails,Model m,HttpSession session,Principal principal)
 {
	 try {
		 StudentDetails oldstudentdetail=this.studentRepository.findStudentsByEmailId(emailid);
		 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
		 studentdetails.setAdmin(admin);
		 StudentDetails student=this.studentRepository.findStudentsByEmailId(emailid);
		 this.studentRepository.save(student);
		 this.studentRepository.save(studentdetails);
		 session.setAttribute("message", new Message("Details Updated","success"));
	 }catch (Exception e) {
		 e.printStackTrace();
	 }
		return "redirect:/admin/show_students/0";
 }
 
 @RequestMapping("/{emailid}/studentdetails")
 public String showStudentDetail(@PathVariable("emailid") String emailid,Model model)
 {
	 java.util.Optional<StudentDetails> studentOptional=this.studentRepository.findStudentsByEmailIdShow(emailid);
	 StudentDetails studentdetails=studentOptional.get();
	 model.addAttribute("studentdetails", studentdetails);
	 return "student_detail";
 }
 @GetMapping("/add-attendance/{emailid}")
 public String openAddSubjectDetailsForm(@PathVariable("emailid") String emailid,Model model)
 {
 	model.addAttribute("title", "Add-Subject");
 	model.addAttribute("attendance", new Attendance());
 	return "add-attendance";
 }
 
 @PostMapping("/process-attendance/{emailid}")
 public String processSubjectDetails(@PathVariable("emailid") String emailid,@ModelAttribute Attendance attendance,Principal principal,StudentDetails stu,HttpSession session,@RequestParam(value= "present",defaultValue = "false")boolean present,
			@RequestParam(value= "absent",defaultValue = "false")boolean absent)
 { try {
	 	
	 
	 	
	 	String name=principal.getName();
	 	Admins admin=this.adminRepository.getAdminsByuserName(name);
	 	attendance.setAdmin(admin);
	 	this.adminRepository.save(admin);
 	StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
 	attendance.setStudentdetails(studentdetails);
 	studentdetails.getAttendance().add(attendance);
 	this.studentRepository.save(studentdetails);
 	System.out.println("DATA"+attendance);
 	//message success.......
 		session.setAttribute("message", new Message("SUBJECT ADDED", "success"));

 	} catch (Exception e) {
 		System.out.println("ERROR " + e.getMessage());
 		e.printStackTrace();
 		// message error
 		session.setAttribute("message", new Message("SOMETHING WENT WRONG", "danger"));

 	}
 	return "redirect:/admin/{emailid}/attendance";
 }
 
 
 
 
 @GetMapping("/{subid}/add-attendancedetail/{emailid}")
 public String openAddAttendanceForm(@PathVariable("emailid") String emailid,Model model,@PathVariable("subid") Integer subid,Attendance attendance)
 {		System.out.println(subid);
 	model.addAttribute("title", "Add-Attendance");
 	model.addAttribute("att", attendance);
 	model.addAttribute("attendancedetail",new AttendanceDetails());
 	return "add-attendancedetail";
 }
 
 @PostMapping("/{subid}/process-attendancedetail/{emailid}")
 public String processAttendance(@PathVariable("emailid") String emailid,@ModelAttribute AttendanceDetails attendancedetails,Attendance attendance,Principal principal,StudentDetails stu,HttpSession session,@RequestParam(value= "present",defaultValue = "false")boolean present,
			@RequestParam(value= "absent",defaultValue = "false")boolean absent,@PathVariable("subid") Integer subid)
 {	
	 try {
	 	
	 	if(present)
	 	{
	 		attendancedetails.setAttend("PRESENT");	
	 		b++;
	 	}
	 	else if(absent)
	 	{
	 		attendancedetails.setAttend("ABSENT");
	 		a++;
	 	}
	 	else
	 	{
	 		throw new Exception("YOU HAVE NOT SELECTED THE SUBJECT TYPE");
	 	}
	 	String name=principal.getName();
	 	Admins admin=this.adminRepository.getAdminsByuserName(name);
	 	attendance.setAdmin(admin);
	 	this.adminRepository.save(admin);
 	StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
 	attendancedetails.setStudentdetails(studentdetails);
 	 Attendance att=this.attendanceRepository.findById(subid).get();
 	 
 	List<AttendanceDetails> attendancedett=this.attendancedetailsRepository.findAttendanceBysubId(subid);
 	
 	System.out.println("subid="+subid);
	 attendancedetails.setTotalAttendance(b+a);
	 attendancedetails.setAbsents(a.intValue());
	 	double c=Math.round((((b+a)-a.floatValue())/(b+a))*100.0);
	 	attendancedetails.setPresents((b+a)-a.floatValue());
	 	attendancedetails.setPercentage(c);
	 	 attendancedetails.setAttendance(att);
		att.getAttendancedetails().add(attendancedetails);
		 this.attendanceRepository.save(att);
		 
		 System.out.println("total="+b);
		 System.out.println("a value="+a.intValue());
		 
		 if(attendancedett.size()!=0)
		 System.out.println("list"+attendancedett.get(attendancedett.size()-1));

 	 
 	
 	
 
 	
 	
 	
 	System.out.println("DATA"+attendance);
 	//message success.......
 		session.setAttribute("message", new Message("Attendance Added", "success"));

 	} catch (Exception e) {
 		System.out.println("ERROR " + e.getMessage());
 		e.printStackTrace();
 		// message error
 		session.setAttribute("message", new Message("YOU HAVE NOT SELECTED THE ATTENDANCE TYPE", "danger"));

 	}
 	return "redirect:/admin/{emailid}/attendance-percentage/{subid}";
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @RequestMapping("/show_attendance/{page}")
 public String showAttendance(@PathVariable("page") Integer page,Model m,Principal principal)
 {	
 	m.addAttribute("title","Show-Attendance");
 	String userName=principal.getName();
 	Admins admin=this.adminRepository.getAdminsByuserName(userName);
 	Pageable pageable=PageRequest.of(page, 5);
 	Page<Attendance> attendance=this.attendanceRepository.findAttendanceByAdmin(admin.getEmail(),pageable);
 	m.addAttribute("attendance",attendance);
 	m.addAttribute("currentpage",page);
 	System.out.println("hello"+attendance.getSize());
 	m.addAttribute("totalPages",attendance.getTotalPages());
 	return "attendance-details";	
 }
 @GetMapping("/add-marks/{emailid}")
 public String openAddMarksForm(@PathVariable("emailid") String emailid,Model model)
 {
	 
 	model.addAttribute("title", "Add-Marks");
 	model.addAttribute("marks", new Marks());
 	return "add-marks";
 }
 
 @PostMapping("/process-marks/{emailid}")
 public String processMarks(@PathVariable("emailid") String emailid,@ModelAttribute Marks marks,Principal principal,HttpSession session)
 { try {
	 String name=principal.getName();
	 	Admins admin=this.adminRepository.getAdminsByuserName(name);
	 	marks.setAdmin(admin);
	 	this.adminRepository.save(admin);
	 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
	 	marks.setStudentdetails(studentdetails);
	 	studentdetails.getMarks().add(marks);
	 	this.studentRepository.save(studentdetails);
	 	System.out.println("DATA"+marks);
	 	//message success.......
 		session.setAttribute("message", new Message("Marks Added", "success"));

 	} catch (Exception e) {
 		System.out.println("ERROR " + e.getMessage());
 		e.printStackTrace();
 		// message error
 		session.setAttribute("message", new Message("Some went wrong !! Try again..", "danger"));

 	}
	 	return "add-marks";
 }
 @RequestMapping("/show_marks/{page}")
 public String showMarks(@PathVariable("page") Integer page,Model m,Principal principal)
 {	
 	m.addAttribute("title","Show-Marks");
 	String userName=principal.getName();
 	Admins admin=this.adminRepository.getAdminsByuserName(userName);
 	Pageable pageable=PageRequest.of(page, 5);
 	Page<Marks> marks=this.marksRepository.findMarksByAdmins(admin.getEmail(),pageable);
 	m.addAttribute("marks",marks);
 	m.addAttribute("currentpage",page);
 	m.addAttribute("totalPages",marks.getTotalPages());
 	return "marks-details";	
 }
 @GetMapping("/deleteattendance/{subid}")
 public String deleteAttendance(@PathVariable("subid") Integer subid,Model model,HttpSession session,Principal principal)
 {
	 Attendance attendance=this.attendanceRepository.findById(subid).get();
	 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
	 admin.getAttendance().remove(attendance);
	 this.adminRepository.save(admin);
	 session.setAttribute("message", new Message("Successfully Deleted !!", "alert-success"));
	 return "redirect:/admin/{emailid}/attendance";
 }
 @PostMapping("/{subid}/update-attendance/{emailid}")
 public String updateAForm(@PathVariable("emailid") String emailid,@PathVariable("subid") Integer subid,Model m)
 {
	 m.addAttribute("title","update attendance");
	 Attendance attendance=this.attendanceRepository.findById(subid).get();
	 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
	 m.addAttribute("attendance",attendance);
	 m.addAttribute("stud", studentdetails);
	 return "update_Aform";
 }
 
 @RequestMapping(value= "/process-Aupdate/{emailid}",method=RequestMethod.POST)
 public String updateAHandler(@PathVariable("emailid") String emailid,@ModelAttribute Attendance attendance,Model m,HttpSession session,Principal principal,@RequestParam(value= "present",defaultValue = "false")boolean present,
			@RequestParam(value= "absent",defaultValue = "false")boolean absent)
 { try {
	 try {
//		
		 Attendance oldattendance=this.attendanceRepository.findById(attendance.getSubid()).get();
		 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
		 attendance.setAdmin(admin);
		 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
		 attendance.setStudentdetails(studentdetails);
		 this.studentRepository.save(studentdetails);
		 this.attendanceRepository.save(attendance);
		 session.setAttribute("message", new Message("Details Updated","success"));
		 
	 }catch (Exception e) {
		 e.printStackTrace();
	 }
	//message success.......
		session.setAttribute("message", new Message("Attendance Updated", "success"));

	} catch (Exception e) {
		System.out.println("ERROR " + e.getMessage());
		e.printStackTrace();
		// message error
		session.setAttribute("message", new Message("YOU HAVE NOT SELECTED THE ATTENDANCE TYPE", "danger"));

	}
		return "attendance-deleted";
 }
 @GetMapping("/deletemarks/{mid}")
 public String deleteMarks(@PathVariable("mid") Integer mid,Model model,HttpSession session,Principal principal)
 {
	 Marks marks=this.marksRepository.findById(mid).get();
	 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
	 admin.getMarks().remove(marks);
	 this.adminRepository.save(admin);
	 session.setAttribute("message", new Message("Successfully Deleted !!", "alert-success"));
	 return "marks-deleted";
 }
 @PostMapping("/{mid}/update-marks/{emailid}")
 public String updateMForm(@PathVariable("emailid") String emailid,@PathVariable("mid") Integer mid,Model m)
 {
	 m.addAttribute("title","update marks");
	 Marks marks=this.marksRepository.findById(mid).get();
	 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
	 m.addAttribute("stud", studentdetails);
	 m.addAttribute("marks",marks);
	 return "update_Mform";
 }
 
 @RequestMapping(value= "/process-Mupdate/{emailid}",method=RequestMethod.POST)
 public String updateMHandler(@PathVariable("emailid") String emailid,@ModelAttribute Marks marks,Model m,HttpSession session,Principal principal)
 { try {
	 try {
		 Marks oldmarks=this.marksRepository.findById(marks.getMid()).get();
		 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
		 marks.setAdmin(admin);
		 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
		 marks.setStudentdetails(studentdetails);
		 this.studentRepository.save(studentdetails);
		 this.marksRepository.save(marks);
		 session.setAttribute("message", new Message("Details Updated","success"));
		 
	 }catch (Exception e) {
		 e.printStackTrace();
	 }
	//message success.......
		session.setAttribute("message", new Message("Marks Updated", "success"));

	} catch (Exception e) {
		System.out.println("ERROR " + e.getMessage());
		e.printStackTrace();
		// message error
		session.setAttribute("message", new Message("Some went wrong !! Try again..", "danger"));

	}
		return "redirect:/admin/{emailid}/marks";
 }
 
 @RequestMapping("/{emailid}/attendance")
 public String showAttendanceDetail(@PathVariable("emailid") String emailid,Model model)
 {
	 List<Attendance> attendanceOptional=this.attendanceRepository.findAttendanceByEmailId(emailid);
	 System.out.println(attendanceOptional.size());
	 
	 System.out.println("emailid"+emailid);
	 model.addAttribute("totalAttendance", attendanceOptional.size());
	 model.addAttribute("attendancedetails",attendanceOptional);
	 return "stud_attendance";
 }
 @RequestMapping("/{emailid}/marks")
 public String showMarks(@PathVariable("emailid") String emailid,Model model)
 {
	 List<Marks> marks=this.marksRepository.findMarksByEmailId(emailid);
	 System.out.println("stud"+emailid);
	 System.out.println("hello"+ marks.size());
	 model.addAttribute("marksdetails",marks);
	 return "stud_marks";
 }
	// open settings handler
	@GetMapping("/settings")
	public String openSettings() {
		return "admin_settings";
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
				return "redirect:/admin/settings";
			}

			return "redirect:/admin/index";
		}
		 @RequestMapping("/{emailid}/attendance/{subid}")
		 public String showAttendanceDates(@PathVariable("emailid") String emailid,@PathVariable("subid") Integer subid,Model model)
		 {
			 List<Attendance> attendanceOptional=this.attendanceRepository.findAttendanceByEmailId(emailid);
			 List<AttendanceDetails> attendancedetails=this.attendancedetailsRepository.findAttendanceBysubId(subid);
			 System.out.println("subid="+subid);
			 model.addAttribute("attendancedate",attendancedetails);
			 return "attendance_date";
		 }
		 
		 @RequestMapping("/{emailid}/attendance-percentage/{subid}")
		 public String showAttendancePercentage(@PathVariable("emailid") String emailid,@PathVariable("subid") Integer subid,Model model)
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
			 model.addAttribute("Percentage",c);
			
			 return "attendance_percentage";
		 }
		 
		 @PostMapping("{id}/update-attendance/{subid}/date/{emailid}")
		 public String updateADateForm(@PathVariable("emailid") String emailid,@PathVariable("subid") Integer subid,@PathVariable("id") Integer id,Model m)
		 {
			 m.addAttribute("title","update attendance");
			 Attendance attendance=this.attendanceRepository.findById(subid).get();
			 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
			 AttendanceDetails att=this.attendancedetailsRepository.findById(id).get();
			 System.out.println("att="+att);
			 m.addAttribute("att",att);
			 return "update-Adateform";
		 }
		 @RequestMapping(value= "/{id}/process-ADate/{subid}/update/{emailid}",method=RequestMethod.POST)
		 public String updateADateHandler(@PathVariable("emailid") String emailid,@ModelAttribute Attendance attendance,Model m,HttpSession session,Principal principal,@RequestParam(value= "present",defaultValue = "false")boolean present,
					@RequestParam(value= "absent",defaultValue = "false")boolean absent,@PathVariable("id") Integer id,@PathVariable("subid") Integer subid,AttendanceDetails att)
		 { try {
			 try {
				 
				 
				 if(present)
						{
						att.setAttend("PRESENT");
						b++;
						}
					 else if(absent)
						{
							att.setAttend("ABSENT");
							--b;
						}
					 else
					 {
							throw new Exception("You have not selected the attendance type");
					 	}
				 		
				 Attendance oldattendance=this.attendanceRepository.findById(attendance.getSubid()).get();
				 AttendanceDetails attendancedate=this.attendancedetailsRepository.findById(att.getId()).get();
				 System.out.println("attends"+att.getAttends());
				 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
				 StudentDetails studentdetails=this.studentRepository.findStudentsByEmailId(emailid);
				 att.setStudentdetails(studentdetails);
				 att.setAttendance(oldattendance);
				 this.attendancedetailsRepository.save(att);
				 session.setAttribute("message", new Message("Details Updated","success"));
				 
			 }catch (Exception e) {
				 e.printStackTrace();
			 }
			//message success.......
				session.setAttribute("message", new Message("Attendance Updated", "success"));

			} catch (Exception e) {
				System.out.println("ERROR " + e.getMessage());
				e.printStackTrace();
				// message error
				session.setAttribute("message", new Message("YOU HAVE NOT SELECTED THE ATTENDANCE TYPE", "danger"));

			}
				return "redirect:/admin/{emailid}/attendance-percentage/{subid}";
		 }
		 
		 @GetMapping("{id}/deleteattendancedate/{subid}")
		 public String deleteAttendancedate(@PathVariable("subid") Integer subid,@PathVariable("id") Integer id,AttendanceDetails attd,Model model,HttpSession session,Principal principal)
		 {
			 Attendance attendance=this.attendanceRepository.findById(subid).get();
			 AttendanceDetails att=this.attendancedetailsRepository.findById(id).get();
			 if(att.getAttends()=="ABSENT")
			 {
				 a--;
			 }
			 else if(att.getAttends()=="PRESENT")
			 {
				b--; 
			 }
			 System.out.println("attends=" +att.getAttends());
			 System.out.println("a=" +a);
			 System.out.println("b=" +b);
			attendance.getAttendancedetails().remove(att);
			 this.attendanceRepository.save(attendance);
			 System.out.println("a=" +a);
			 System.out.println("b=" +b);
			 Admins admin=this.adminRepository.getAdminsByuserName(principal.getName());
			 model.addAttribute("admin",admin.getEmail());
			 session.setAttribute("message", new Message("Successfully Deleted !!", "alert-success"));
			 return "attendance-deleted";
 }
 
}

 
 