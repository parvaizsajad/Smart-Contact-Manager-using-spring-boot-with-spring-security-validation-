package com.smart.MyConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.entities.User;
import com.smart.userRepository.UserRepository;

public class UserDetailServiceImpul implements UserDetailsService{
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User userByUserName = repository.getUserByUserName(username);
		
		if(userByUserName==null) {
			throw new UsernameNotFoundException("could not find the user name");
		}
		CustomUserDetail cud=new CustomUserDetail(userByUserName);
		return cud;
	}

}
