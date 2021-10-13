package com.smart.Controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.entities.User;
import com.smart.helper.Message;
import com.smart.otp.EmailService;
import com.smart.otp.RandomNum;
import com.smart.userRepository.UserRepository;

@Controller
public class CommonController {
	String otpRandom;
	@Autowired
	private EmailService emailService;
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
	@RequestMapping("/forgot")
	public String ForgotController(Model m) {
		m.addAttribute("title","Forgot Password :Smart-Contact-manager");
		
		
	 
	    
		
	    return "forgotPass";
	}
	
	@PostMapping("/passwordHandler")
	public String passwordHandler(@Param("email") String email ,Model m) {
		m.addAttribute("title","Forgot Password :Smart-Contact-manager");
		System.out.println(email);
		String random = RandomNum.getRandomNumberString();
		otpRandom=random;
		emailService.sendmail("mirpaten@gmail.com", "Forget-PassWord", "Hi This is your OTP "+random, email);
		m.addAttribute("email", email);
		
		
	 
	    
		
	    return "otphandler";
	}
	

	@PostMapping("/otpmsg")
	public String otpHandler(@Param("otp") String otp ,@Param("email") String email,Model m) {
		m.addAttribute("title","OTP  :Smart-Contact-manager");
		System.out.println(otp);
		System.out.println("this is working");
		if(otp.equals(otpRandom)) {
			m.addAttribute("email", email);
			
			return "changePassword";	
		}

		
		
	 
	    
		
	    return "otphandler";
		
	}
	
	@PostMapping("/passwordChanged")
	public String passChangedHandler(@Param("pass1") String pass1 , @Param("pass2") String pass2 ,@Param("email") String email, Model m) {
		m.addAttribute("title","OTP  :Smart-Contact-manager");
		
		
	    System.out.println(pass1+pass2);
	    if(pass1.equals(pass2)) {
	    	User userByUserName = this.userRepository.getUserByUserName(email);
	    	String encode = this.bCryptPasswordEncoder.encode(pass2);
	    	userByUserName.setPassword(encode);
	    	this.userRepository.save(userByUserName);
	    	
	    	
	    }
		
	    return "signin";
		
	}
	
	
}
