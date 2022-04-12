package com.student.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DynamicUpdate
@Table(name="attendanceDetails")
public class AttendanceDetails {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private double totalAttendance;
private String date;
private String attends;
private float presents;
private float absents;
private double percentage;
@ManyToOne
@JsonIgnore
private Attendance attendance;

@ManyToOne
@JsonIgnore
private StudentDetails studentdetails;

public String getAttends() {
	return attends;
}
public void setAttends(String attends) {
	this.attends = attends;
}


public double getPercentage() {
	return percentage;
}
public void setPercentage(double percentage) {
	this.percentage = percentage;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public StudentDetails getStudentdetails() {
	return studentdetails;
}
public void setStudentdetails(StudentDetails studentdetails) {
	this.studentdetails = studentdetails;
}
public String getAttend() {
	return attends;
}
public void setAttend(String attend) {
	this.attends = attend;
}
public String getDate() {
	return date;
}
public double getTotalAttendance() {
	return totalAttendance;
}
public void setTotalAttendance(double c) {
	this.totalAttendance = c;
}

public Attendance getAttendance() {
	return attendance;
}
public void setAttendance(Attendance attendance) {
	this.attendance = attendance;
}
public void setDate(String date) {
	this.date = date;
}
public AttendanceDetails() {
	super();
	// TODO Auto-generated constructor stub
}
public float getPresents() {
	return presents;
}
public void setPresents(float presents) {
	this.presents = presents;
}
public float getAbsents() {
	return absents;
}
public void setAbsents(float absents) {
	this.absents = absents;
}
@Override
public boolean equals(Object obj) {
	// TODO Auto-generated method stub
	return super.equals(obj);
}



public void setAttendance(Integer subid) {
	// TODO Auto-generated method stub
	
}
@Override
public String toString() {
	return "AttendanceDetails [id=" + id + ", totalAttendance=" + totalAttendance + ", date=" + date + ", attends="
			+ attends + ", presents=" + presents + ", absents=" + absents + ", percentage=" + percentage
			+ ", attendance=" + attendance + ", studentdetails=" + studentdetails + "]";
}




}
