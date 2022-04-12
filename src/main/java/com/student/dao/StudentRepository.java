package com.student.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.entities.Admins;
import com.student.entities.StudentDetails;

public interface StudentRepository extends JpaRepository<StudentDetails,Integer>{

	@Query("from StudentDetails as c where c.admin.email= :emailId ")
public Page<StudentDetails> findStudentsByAdmins(@Param("emailId")String emailId,Pageable pePageable);  
//search	
public List<StudentDetails> findByName(String name);

@Query("from StudentDetails as c where c.emailid= :emailId ")
StudentDetails findStudentsByEmailId(@Param("emailId")String emailId);  


@Query("from StudentDetails as c where c.emailid= :emailId ")
Optional<StudentDetails> findStudentsByEmailIdShow(@Param("emailId")String emailId); 
}
