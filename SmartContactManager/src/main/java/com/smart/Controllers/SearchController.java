package com.smart.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.userRepository.ContactRepository;
import com.smart.userRepository.UserRepository;

@RestController
public class SearchController {
	
	//AutoWired
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;

	//this is the search handler
	@RequestMapping("/search/{querry}")
	public ResponseEntity<?> searchName(@PathVariable("querry") String name,Principal principal){
		System.out.println(name);
		User user = this.userRepository.getUserByUserName(principal.getName());
		List<Contact> contacts = this.contactRepository.findByNameContainingAndUser(name, user);
		
		return ResponseEntity.ok(contacts);
	}
}
