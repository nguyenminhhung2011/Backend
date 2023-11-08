package com.FitnessApp.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "userInfo")
public class User implements Serializable {
	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_User")
	@SequenceGenerator(name = "sequence_User", sequenceName = "sequence2", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	@NotBlank(message = "Username is mandatory")
	private String username;

	@Column(nullable = false)
	@NotBlank(message = "Password is mandatory")
	private String password;

	@Column(columnDefinition = "text")
	@NotBlank
	private String refreshToken;

//	@JsonManagedReference
//	@ManyToMany(cascade = CascadeType.ALL, FETCH = FetchType.EAGER)
//	@JoinTable(name = "User_Role",
//			joinColumns = {@JoinColumn(name = "user_Id")},
//			inverseJoinColumns = {
//				@JoinColumn(name = "role_Id")
//	})
////	@JsonManagedReference(value="user-roles")
//	@JsonIgnore
//	private Set<Role> roles = new HashSet<>();

	@Serial
	private static final long serialVersionUID = -297553281792804226L;

	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private UserProfile userProfile;

}

