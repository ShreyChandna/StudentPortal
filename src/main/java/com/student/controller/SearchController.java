package com.student.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.student.dao.AdminRepository;
import com.student.dao.StudentRepository;
import com.student.entities.Admins;
import com.student.entities.StudentDetails;


@RestController
public class SearchController {
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal){
	
		System.out.println(query);
		List<StudentDetails> studentdetails=this.studentRepository.findByName(query);
		return ResponseEntity.ok(studentdetails);
	
	}
	

	
}
