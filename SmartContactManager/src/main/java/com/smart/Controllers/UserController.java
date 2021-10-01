package com.smart.Controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.userRepository.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/")
public String HomeController(Model m) {
		m.addAttribute("title","Home :Smart-Contact-manager");

	
	return "home";
}
	
	
	@GetMapping("/about")
public String aboutController(Model m) {
		m.addAttribute("title"," About :Smart-Contact-manager");

	
	return "about";
}
	
	
	@GetMapping("/signup")
public String SignupController(Model m) {
		m.addAttribute("title","Signup :Smart-Contact-manager");
		m.addAttribute("user", new User());

	
	return "signup";
}
	// handler for the regster user
	@PostMapping("/register")
public String RegisterController(@Valid @ModelAttribute("user") User user,BindingResult result,Model m,@RequestParam(value = "aggrement",defaultValue ="false" ) boolean aggrement,HttpSession session) {
		try {
			
			if(result.hasErrors()) {
				System.out.println(result.toString());
				//m.addAttribute("user", result);
				throw new Exception();
			}
			if(!aggrement) {
				System.out.println("please accept the terms and conditions");
				throw new Exception("please accept the terms and conditions");
			
			}
			
			m.addAttribute("title","Register :Smart-Contact-manager");
		
		
			user.setRole("ROLE_USER");
			user.setActive(true);
			user.setImage("default.png");
			user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
			User save = this.userRepository.save(user);
			m.addAttribute("user", save);
			System.out.println(aggrement);
			System.out.println(save);
			m.addAttribute("user", new User());
			session.setAttribute("message", new Message("Registered successfully", "alert-success"));
			return "signup";
		
		
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something Went wrong"+e.getMessage(), "alert-danger"));
			return "signup";
		}

}
	
	@RequestMapping("/login")
public String Signin(Model m) {
	m.addAttribute("title","LOGIN :Smart-Contact-manager");
	return "signin";
}
}
