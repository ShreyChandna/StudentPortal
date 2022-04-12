package com.student.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.entities.Attendance;
import com.student.entities.Marks;

public interface MarksRepository extends JpaRepository<Marks,Integer>{
	
	@Query("from Marks as m where m.admin.email= :emailId")
	public Page<Marks> findMarksByAdmins(@Param("emailId")String emailId,Pageable pePageable);
	
	@Query("from Marks as d where d.studentdetails.emailid= :emailId")
	public List<Marks> findMarksByEmailId(String emailId);
	
	@Query("from Marks as m where m.studentdetails.emailid= :emailId")
	public List<Marks> findMarksByemailId(String emailId);
	
}
