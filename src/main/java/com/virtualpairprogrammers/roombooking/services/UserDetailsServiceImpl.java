package com.virtualpairprogrammers.roombooking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virtualpairprogrammers.roombooking.RoombookingApplication;
import com.virtualpairprogrammers.roombooking.data.UserRepository;
import com.virtualpairprogrammers.roombooking.model.entities.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByName(username);
		
		RoombookingApplication.logger.info(user.toString());
		
		if(user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		RoombookingApplication.logger.info(user.getGrantedAuthorities().toString());

		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getGrantedAuthorities());
	}

}
