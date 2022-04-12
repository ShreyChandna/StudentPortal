


	package com.student.config;

	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.student.dao.AdminRepository;
import com.student.entities.Admins;


	public class AdminDetailsServiceImpl implements UserDetailsService {
		
		@Autowired
		private AdminRepository adminRepository;
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			// fetching user from database
			Admins admin=adminRepository.getAdminsByuserName(username);
			if(admin==null) 
			{
				throw new UsernameNotFoundException("could not find user!!");
			}
			CustomAdminDetails customAdminDetails=new CustomAdminDetails(admin);  
			return customAdminDetails;
		}

	}

