package com.fitlife.app.Model;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	private Long id;
	private String nameRole;

	@Serial
	private static final long serialVersionUID = -297333281792804226L;

//	@ManyToMany(mappedBy = "roles")
////	@JsonBackReference(value="user-roles")
//	@JsonIgnore
////	private Collection<User> users;
//	private java.util.Set<User> roles = new HashSet<>();

	public Role(Long id, String name) {
		this.nameRole = name;
		this.id = id;
	}

	public Role() {
		this.nameRole = "";
	}

}
