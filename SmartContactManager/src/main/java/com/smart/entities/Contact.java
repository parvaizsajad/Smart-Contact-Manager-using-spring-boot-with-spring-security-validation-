package com.smart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Contact {
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.id==((Contact)obj).getId();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotBlank(message = "name field is requied")
	@Size(min = 2,max = 20,message = "Min 2 or Max 20 are allowed")
	private String name;
	
	private String nick_name;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	private String work;
	private String image;
	@Column(length = 1000)
	private String description;
	@NotBlank(message = "phone field is requied")
	private String phone;
@ManyToOne
@JsonIgnore
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Contact(int id, String name, String nick_name, String email, String work, String image, String description,
			String phone) {
		super();
		this.id = id;
		this.name = name;
		this.nick_name = nick_name;
		this.email = email;
		this.work = work;
		this.image = image;
		this.description = description;
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", nick_name=" + nick_name + ", email=" + email + ", work="
				+ work + ", image=" + image + ", description=" + description + ", phone=" + phone + "]";
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
