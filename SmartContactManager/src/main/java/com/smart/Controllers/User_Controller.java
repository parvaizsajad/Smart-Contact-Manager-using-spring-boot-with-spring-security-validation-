package com.smart.Controllers;

import java.io.File;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import com.razorpay.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smart.entities.Contact;
import com.smart.entities.MyOrders;
import com.smart.entities.User;
import com.smart.helper.Helper;
import com.smart.helper.Message;
import com.smart.userRepository.ContactRepository;
import com.smart.userRepository.MyOrderRepository;
import com.smart.userRepository.UserRepository;

@Controller
@RequestMapping("/user")
public class User_Controller {
	
	public static String getRandomNumberString() {
	    // It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(9999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%4d", number);
	}
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private MyOrderRepository myOrderRepository;
	@Autowired
 private Helper helper;
	
	// method for adding common data to response
	@ModelAttribute
	public void CommonFunction(Model m, Principal pin) {
		
		User userByUserName = userRepository.getUserByUserName(pin.getName());
		System.out.println(userByUserName);
		m.addAttribute("user", userByUserName);
	}
	@RequestMapping("/dashboard")
	public String User_dashboard(Model m, Principal pin) {
		m.addAttribute("title","Home :Smart-Contact-manager");
		
		return "normal/user_dashboard";
	}
	
	//add contact handler 
	@RequestMapping("/add-contact")
	public String AddContact(Model m, Principal pin) {
		m.addAttribute("title","Add Contact :Smart-Contact-manager");
		m.addAttribute("contact",new Contact());
		
		return "normal/Add-contact-form";
	}

	
// contact add form handler
@PostMapping("/process-contact")
	public String processcontat(@Valid @ModelAttribute Contact contact,BindingResult result, Model m,Principal principal,@RequestParam("imagee") MultipartFile file,HttpSession session) {
		m.addAttribute("title","Add Contact :Smart-Contact-manager");
try {
	
	if(result.hasErrors())
	{
		
		//m.addAttribute("user", result);
		throw new Exception();
	}
	System.out.println(contact);
	String name = principal.getName();
	User userByUserName = this.userRepository.getUserByUserName(name);
	contact.setImage(file.getOriginalFilename());
	userByUserName.getList().add(contact);
	contact.setUser(userByUserName);
	if(file.isEmpty()) {
		System.out.println("something went wrong");
		contact.setImage("contact.png");
	}
	
	
	
	this.userRepository.save(userByUserName);
	
		// file upload code.
	helper.fileupload(file);
	
		session.setAttribute("message", new Message("Saved successfully", "alert-success"));
	} catch (Exception e) 
{
	e.printStackTrace();
	session.setAttribute("message", new Message("Something Went wrong"+e.getMessage(), "alert-danger"));
}
return "redirect:/user/show-contact/0";
}




@RequestMapping("/show-contact/{page}")
public String ShowContact(@PathVariable("page") Integer page,Model m, Principal pin) {
	m.addAttribute("title","Show Contact :Smart-Contact-manager");
	String name = pin.getName();
	User userByUserName = userRepository.getUserByUserName(name);
Pageable pagable=PageRequest.of(page, 4);
	Page<Contact> findContactByUser = this.contactRepository.findContactByUser(userByUserName.getId(),pagable);
	m.addAttribute("contact", findContactByUser);
	m.addAttribute("currentPage", page);
	m.addAttribute("totalPages", findContactByUser.getTotalPages());

	
	return "normal/show_contact_page";
}

@RequestMapping("/{id}/show-contact")
public String Show_Contact(@PathVariable("id") Integer id, Model m,Principal principal) {
	System.out.println(id);
	m.addAttribute("title","Show Contact :Smart-Contact-manager");
	Optional<Contact> findById = this.contactRepository.findById(id);
	Contact contact = findById.get();
	String name = principal.getName();
	User userName = this.userRepository.getUserByUserName(name);
	
	if(userName.getId()==contact.getUser().getId())
	m.addAttribute("contact", contact);

	
	return "normal/show_contact";
}


@RequestMapping("/delete/{id}")
public String DeleteContact(@PathVariable("id") Integer id,Model m, Principal pin) {
	m.addAttribute("title","Add Contact :Smart-Contact-manager");
Contact contact = this.contactRepository.findById(id).get();
	
	/*
	 * contact.setUser(null); this.contactRepository.delete(contact);
	 */
	User usr = this.userRepository.getUserByUserName(pin.getName());
	usr.getList().remove(contact);
	this.userRepository.save(usr);
	return "redirect:/user/show-contact/0";
}


@PostMapping("/update-contact/{cid}")
public String UpdateContact(@PathVariable("cid") Integer id,Model m, Principal pin) {
	m.addAttribute("title","Update contact :Smart-Contact-manager");
	Contact findById = this.contactRepository.findById(id).get();
    System.out.println("this is update");
    m.addAttribute("contact", findById);
    
	
    return "normal/update_form";
}

@RequestMapping(value = "/process-update",method = RequestMethod.POST)
public String UpdateForm(@ModelAttribute Contact contact, Model m,Principal principal,@RequestParam("imagee") MultipartFile file,HttpSession session) {
	m.addAttribute("title","Update Process :Smart-Contact-manager");
	Contact contact2 = this.contactRepository.findById(contact.getId()).get();
	String oldImage = contact2.getImage();
	try {
		System.out.println(contact);
		String name = principal.getName();
		User userByUserName = this.userRepository.getUserByUserName(name);
		contact.setUser(userByUserName);
	
	contact.setImage(file.getOriginalFilename());
	if(file.isEmpty()) {
		
		contact.setImage(oldImage);
	}
	File deleteFile = new ClassPathResource("static"+File.separator+"img").getFile();
	new File(deleteFile,oldImage);
	this.contactRepository.save(contact);

		// file upload code.
	helper.fileupload(file);
		
		session.setAttribute("message", new Message("Updated successfully", "alert-success"));
	} catch (Exception e) 
{
	e.printStackTrace();
	session.setAttribute("message", new Message("Something Went wrong"+e.getMessage(), "alert-danger"));
}
	//return "redirect:/user/show-contact/0";
   // return "redirect:/user/"+contact.getId()+"/show-contact";
	return "normal/show_contact";
}

// handler for the profile 
@GetMapping("/profile")
public String profileHandler(Model m) {
	m.addAttribute("title","Update Process :Smart-Contact-manager");
	
	return "normal/profile-page";
}
//handler for the Settings 
@GetMapping("/settings")
public String SettingsHandler(Model m) {
	m.addAttribute("title","Settings :Smart-Contact-manager");
	
	return "normal/setting";
}
//handler for the change password 
@PostMapping("/change-password")
public String changePassHandler(@RequestParam("old-password") String old,@RequestParam("new-password") String newp,@RequestParam("rnew-password") String rnewp,Model m,Principal pin,HttpSession session) {
	m.addAttribute("title","change Password :Smart-Contact-manager");
	if(old.equals(newp)) {
		session.setAttribute("message", new Message("old and new password can not be same", "alert-danger"));
		return "normal/setting";
	}
	if(newp.equals(rnewp)) {
		System.out.println(" equal"+old+newp+rnewp);

		User userByUserName = this.userRepository.getUserByUserName(pin.getName());
		System.out.println(userByUserName);
		if(bCryptPasswordEncoder.matches(old,userByUserName.getPassword())) {
			System.out.println("pass matched");
			String encode = this.bCryptPasswordEncoder.encode(newp);
			userByUserName.setPassword(encode);
			this.userRepository.save(userByUserName);
			session.setAttribute("message", new Message("Update successfully ", "alert-success"));
			return "redirect:/user/show-contact/0";
			
		}
		session.setAttribute("message", new Message("please enter the correct details ", "alert-danger"));
		
		
		return "normal/setting";

		
	}
	System.out.println(old+newp+rnewp);
	session.setAttribute("message", new Message("Sorry password didn't match ", "alert-danger"));
	
	
	return "normal/setting";
}

@PostMapping("/createPayment")
@ResponseBody
public String PaymentHandler(@RequestBody Map<String, Object> data,Principal pin) throws Exception {
	System.out.println("this is a payment field");
	System.out.println(data);
	int amount = Integer.parseInt(data.get("amount").toString());
	var razorpayClient=new RazorpayClient(" your id","secret key");

	JSONObject options = new JSONObject();
	options.put("amount", amount*100);
	options.put("currency", "INR");
	options.put("receipt", "txn_123456");
	Order order = razorpayClient.Orders.create(options);
	System.out.println(order);
	MyOrders myOrders=new MyOrders();
	myOrders.setAmount(order.get("amount").toString());
	myOrders.setOrderId(order.get("id"));
	myOrders.setPaymentId(null);
	myOrders.setStatus("created");
	myOrders.setUser(this.userRepository.getUserByUserName(pin.getName()));
	myOrders.setReciept(order.get("receipt"));
	this.myOrderRepository.save(myOrders);
	
	return order.toString();

}
@PostMapping("/update_order")
public ResponseEntity<?> updateOrder(@RequestBody Map<String, Object> data){
	MyOrders myOrders = this.myOrderRepository.findByOrderId(data.get("order_id").toString());
	
	myOrders.setPaymentId(data.get("payment_id").toString());
	myOrders.setStatus(data.get("status").toString());
	System.out.println(data);
	this.myOrderRepository.save(myOrders);
	
	return ResponseEntity.ok(Map.of("msg","updated"));
}
}
