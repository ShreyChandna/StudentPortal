//package com.student.entities;
//	import java.util.ArrayList;
//	import java.util.List;
//
//	import javax.persistence.CascadeType;
//	import javax.persistence.Column;
//	import javax.persistence.Entity;
//	import javax.persistence.GeneratedValue;
//	import javax.persistence.GenerationType;
//	import javax.persistence.Id;
//	import javax.persistence.OneToMany;
//	import javax.persistence.Table;
//	import javax.validation.constraints.NotBlank;
//	@Entity
//	@Table(name="user")
//	public class User {
//		@Id
//		@GeneratedValue(strategy = GenerationType.AUTO)
//		private int id;
//		@NotBlank
//		@Column(unique = true)
//		private String username;
//		private String password;
//		@Column(unique = true)
//		private String email;
//		private int student_id;
//		private String role;
//		
//		@OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,orphanRemoval = true)
//		private List<StudentDetails> studentdetails=new ArrayList<>();
//		@OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,orphanRemoval = true)
//		private List<Attendance> attendance=new ArrayList<>();
//		@OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,orphanRemoval = true)
//		private List<Marks> marks=new ArrayList<>();
//		
//		public void setEnabled(boolean b) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public int getId() {
//			return id;
//		}
//
//		public void setId(int id) {
//			this.id = id;
//		}
//
//		public String getUsername() {
//			return username;
//		}
//
//		public void setUsername(String username) {
//			this.username = username;
//		}
//
//		public String getPassword() {
//			return password;
//		}
//
//		public void setPassword(String password) {
//			this.password = password;
//		}
//
//		public String getEmail() {
//			return email;
//		}
//
//		public void setEmail(String email) {
//			this.email = email;
//		}
//
//		public int getStudent_id() {
//			return student_id;
//		}
//
//		public void setStudent_id(int student_id) {
//			this.student_id = student_id;
//		}
//
//		public String getRole() {
//			return role;
//		}
//
//		public void setRole(String role) {
//			this.role = role;
//		}
//
//		public List<StudentDetails> getStudentdetails() {
//			return studentdetails;
//		}
//
//		public void setStudentdetails(List<StudentDetails> studentdetails) {
//			this.studentdetails = studentdetails;
//		}
//
//		public List<Attendance> getAttendance() {
//			return attendance;
//		}
//
//		public void setAttendance(List<Attendance> attendance) {
//			this.attendance = attendance;
//		}
//
//		public List<Marks> getMarks() {
//			return marks;
//		}
//
//		public void setMarks(List<Marks> marks) {
//			this.marks = marks;
//		}
//
//		public User() {
//			super();
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		public String toString() {
//			return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
//					+ ", student_id=" + student_id + ", role=" + role + ", studentdetails=" + studentdetails
//					+ ", attendance=" + attendance + ", marks=" + marks + "]";
//		}
//	
//		
//	}
//		
