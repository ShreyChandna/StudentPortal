package com.student.entities;

	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.ManyToOne;
	import javax.persistence.Table;

	import com.fasterxml.jackson.annotation.JsonIgnore;

	@Entity
	@Table(name="MARKS")
	public class Marks 
	{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int mid;
		
		private String subjectname;
		private String subcode;
		private int midterm;
		private int endterm;
		private int classtest;
		@ManyToOne
		@JsonIgnore
		private Admins admin;
		
		@ManyToOne
		@JsonIgnore
		private StudentDetails studentdetails;

		public int getMid() {
			return mid;
		}

		public void setMid(int mid) {
			this.mid = mid;
		}

		public String getSubjectname() {
			return subjectname;
		}

		public void setSubjectname(String subjectname) {
			this.subjectname = subjectname;
		}

		public String getSubcode() {
			return subcode;
		}
		
		public void setSubcode(String subcode) {
			this.subcode = subcode;
		}

		public int getMidterm() {
			return midterm;
		}

		public void setMidterm(int midterm) {
			this.midterm = midterm;
		}

		public int getEndterm() {
			return endterm;
		}

		public void setEndterm(int endterm) {
			this.endterm = endterm;
		}

		public int getClasstest() {
			return classtest;
		}

		public void setClasstest(int classtest) {
			this.classtest = classtest;
		}
		
		public Admins getAdmin() {
			return admin;
		}

		public void setAdmin(Admins admin) {
			this.admin = admin;
		}

		public StudentDetails getStudentdetails() {
			return studentdetails;
		}

		public void setStudentdetails(StudentDetails studentdetails) {
			this.studentdetails = studentdetails;
		}

		public Marks() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
	
	
	}
	
		
		
