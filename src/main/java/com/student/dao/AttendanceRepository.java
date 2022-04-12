package com.student.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.entities.Attendance;


public interface AttendanceRepository extends JpaRepository<Attendance,Integer>{
	
	
	@Query("from Attendance as a where a.admin.email= :emailId ")
	public Page<Attendance> findAttendanceByAdmin(@Param("emailId")String emailId,Pageable pePageable);
	
	
	@Query("from Attendance as e where e.studentdetails.emailid= :emailId")
	public List<Attendance> findAttendanceByEmailId(@Param("emailId")String emailId);
	
	@Query("from Attendance as u where u.studentdetails.emailid= :emailId")
	public List<Attendance> findAttendanceByemailId(String emailId);
	
}
