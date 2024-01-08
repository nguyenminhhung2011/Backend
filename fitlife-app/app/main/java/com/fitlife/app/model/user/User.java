package com.fitlife.app.model.user;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fitlife.app.dataClass.views.UserViews;

import com.fasterxml.jackson.annotation.JsonView;
import com.fitlife.app.model.trainer.ChatThread;
import com.fitlife.app.model.trainer.Trainer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "userInfo")
public class User implements Serializable {
	@JsonView(value = {UserViews.Summary.class})
	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_User")
	@SequenceGenerator(name = "sequence_User", sequenceName = "sequence2", allocationSize = 1)
	private Long id;

	@JsonView(value = {UserViews.Summary.class})
	@Column(nullable = false)
	@NotBlank(message = "Username is mandatory")
	private String username;

	@Column(nullable = false)
	@NotBlank(message = "Password is mandatory")
	private String password;

	@JsonView(value = {UserViews.Detail.class})
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

	@JsonView(value = {UserViews.Summary.class})
	@OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private UserProfile userProfile;


	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Trainer> trainers;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ChatThread> chatThreads;
}

