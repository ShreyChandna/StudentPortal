package com.student.entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

	@Entity
	@Table(name="STUDEN")
	public class StudentDetails {
		@Column(unique = true)
		private int student_id;
		@NotBlank(message="NAME FIELD IS REQUIRED!!")
		private String name;
		@Column(unique = true)
		private int sapid;
		private String acyear;
		private String phone;
		private String program;
		private String semester;
		private String rollno;
		@Id
		@Column(unique = true)
		private String emailid;
		
		
		@ManyToOne
		@JsonIgnore
		private Admins admin;
		
		@OneToMany(mappedBy = "studentdetails",cascade = CascadeType.ALL,orphanRemoval = true)
		private List<Attendance> attendance=new ArrayList<>();
		
		@OneToMany(mappedBy = "studentdetails",cascade = CascadeType.ALL,orphanRemoval = true)
		private List<Marks> marks=new ArrayList<>();
		
		@OneToMany(mappedBy = "studentdetails",cascade = CascadeType.ALL,orphanRemoval = true)
		private List<AttendanceDetails> attendancedetail=new ArrayList<>();
		
		
		
		
		public List<Attendance> getAttendance() {
			return attendance;
		}


		public void setAttendance(List<Attendance> attendance) {
			this.attendance = attendance;
		}


		public List<Marks> getMarks() {
			return marks;
		}


		public void setMarks(List<Marks> marks) {
			this.marks = marks;
		}


		public Admins getAdmin() {
			return admin;
		}


		public void setAdmin(Admins admin) {
			this.admin = admin;
		}


		public StudentDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		public String getRollno() {
			return rollno;
		}
		public void setRollno(String rollno) {
			this.rollno = rollno;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getProgram() {
			return program;
		}
		public void setProgram(String program) {
			this.program = program;
		}
		
		public String getAcyear() {
			return acyear;
		}
		public void setAcyear(String acyear) {
			this.acyear = acyear;
		}
		public String getSemester() {
			return semester;
		}
		public void setSemester(String semester) {
			this.semester = semester;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		
		public int getStudent_id() {
			return student_id;
		}
		public void setStudent_id(int student_id) {
			this.student_id = student_id;
		}
	
			public int getSapid() {
			return sapid;
		}
		public void setSapid(int sapid) {
			this.sapid = sapid;
		}
		
		
		public String getEmailid() {
			return emailid;
		}


		public void setEmailid(String emailid) {
			this.emailid = emailid;
		}
		
		public List<AttendanceDetails> getAttendancedetail() {
			return attendancedetail;
		}


		public void setAttendancedetail(List<AttendanceDetails> attendancedetail) {
			this.attendancedetail = attendancedetail;
		}
		
		
		
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub
			return this.student_id==((StudentDetails)obj).getStudent_id();
		}


	
		
		
		
		

	}



