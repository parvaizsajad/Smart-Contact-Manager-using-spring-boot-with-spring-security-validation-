package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int id;
	@NotBlank(message = "name field is requied")
	@Size(min = 2,max = 20,message = "Min 2 or Max 20 are allowed")
	private String name;
@Column(unique = true)
@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
@NotBlank(message = "Password field is requied")

	private String password;
	private String image;
	@Column(length = 500)
	private String about;
	private String role;
	private boolean active;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "user",orphanRemoval =true)
	private List<Contact> list=new ArrayList<>();
	
	public List<Contact> getList() {
		return list;
	}
	public void setList(List<Contact> list) {
		this.list = list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public User(int id, String name, String email, String password, String image, String about, String role,
			boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.image = image;
		this.about = about;
		this.role = role;
		this.active = active;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", image=" + image
				+ ", about=" + about + ", role=" + role + ", active=" + active + ", list=" + list + "]";
	}


}
