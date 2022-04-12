package com.student.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.student.entities.Admins;

public interface AdminRepository extends JpaRepository<Admins,Integer>{
		@Query("select u from Admins u where u.email= :email")
		public Admins  getAdminsByuserName(@Param("email") String email);
		

	}
