package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Users")

public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(length=128, nullable=false,unique=true)
	private String email;
	@Column(length=64, nullable=false)
	private String password;
	@Column(name="firstname",length=45, nullable=false)
	private String firstname;
	@Column(name="lastname",length=45, nullable=false)
	private String lastname;
	@Column(length=45)
	private String photos;
	private boolean enabled;
	
	@ManyToMany
	@JoinTable(
			name="Users_roles",
	            joinColumns=@JoinColumn(name="user_id"),
	       inverseJoinColumns=@JoinColumn(name="role_id")
	     )
	
		private Set<Role> roles= new HashSet<>();
	public User() {
		
	}
	public User(String email, String password, String firstname, String lastname) {
		
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<Role> getRole() {
		return roles;
	}
	public void setRole(Set<Role> role) {
		this.roles = role;
	}
	public void addRole(Role role) {
		this.roles.add(role);
		
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", roles=" + roles + "]";
	}
	

}
