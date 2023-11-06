package com.FitnessApp.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "userInfo")
public class User  implements Serializable {

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_User")
	@SequenceGenerator(name = "sequence_User", sequenceName = "sequence2", allocationSize = 1)
	private Long id;
	private String username;
	private String password;

	private Boolean isEnable;

	@Column(columnDefinition = "text")
	private String refreshToken;

	//	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "User_Role",
			joinColumns = {@JoinColumn(name = "user_Id")},
			inverseJoinColumns = {
				@JoinColumn(name = "role_Id")
			})
//	@JsonManagedReference(value="user-roles")
	@JsonIgnore
	private Set<Role> roles = new HashSet<>();


	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Serial
	private static final long serialVersionUID = -297553281792804226L;

	@OneToOne(mappedBy = "user")
	@JsonManagedReference(value = "user_gymer")
	private Gymer gymer;


	public User(String username, String password, Boolean isEnable, Role roles) {
		this.username = username;
		this.password = password;
		this.isEnable = isEnable;
		this.roles.add(roles);
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

