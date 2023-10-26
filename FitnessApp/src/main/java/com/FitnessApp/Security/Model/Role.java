package com.FitnessApp.Security.Model;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	private Long id;
	private String nameRole;
	private static final long serialVersionUID = -297333281792804226L;
	@ManyToMany(mappedBy = "roles")
//	@JsonBackReference(value="user-roles")
	@JsonIgnore
//	private Collection<User> users;
	private java.util.Set<User> roles = new HashSet<>();

	public Role(String name) {
		this.nameRole = name;
	}

	public Role(Long id, String name) {
		this.nameRole = name;
		this.id = id;
	}

	public Role() {
		this.nameRole = "";
	}

	public String getNameRole() {
		return nameRole;
	}

	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}

	public java.util.Set<User> getRoles() {
		return roles;
	}

	public void setRoles(java.util.Set<User> roles) {
		this.roles = roles;
	}

}
