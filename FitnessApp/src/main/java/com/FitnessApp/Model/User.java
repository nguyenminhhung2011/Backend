package com.FitnessApp.Model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
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
	private String username;
	private String password;

	@Column(columnDefinition = "text")
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

	@OneToOne(mappedBy = "user")
	private UserProfile userProfile;

}

