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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ATTENDANCE")
public class Attendance
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int subid;
	private String subject;
	private String subcode;
	private int lecturesheld;	
	@ManyToOne
	@JsonIgnore
	private Admins admin;
	
	@ManyToOne
	@JsonIgnore
	private StudentDetails studentdetails;
	

	@OneToMany(mappedBy = "attendance",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<AttendanceDetails> attendancedetails=new ArrayList<>();
	
	
	public List<AttendanceDetails> getAttendancedetails() {
		return attendancedetails;
	}
	public void setAttendancedetails(List<AttendanceDetails> attendancedetails) {
		this.attendancedetails = attendancedetails;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public int getLecturesheld() {
		return lecturesheld;
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
	public void setLecturesheld(int lecturesheld) {
		this.lecturesheld = lecturesheld;
	}
	public int getSubid() {
		return subid;
	}
	public void setSubid(int subid) {
		this.subid = subid;
	}
	public String getSubcode() {
		return subcode;
	}
	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.subid==((Attendance)obj).getSubid();
	}
	public Attendance() {
		super();
		// TODO Auto-generated constructor stub
	}
	


}
