package com.FitnessApp.Security.Model;

import java.util.ArrayList;
import java.util.Collection;

import com.FitnessApp.Model.Role;
import com.FitnessApp.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails {

	private final User user;

	public CustomUserDetail(User user) {
		this.user = user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<Role> userRole= user.getRoles();
		ArrayList<SimpleGrantedAuthority> listRole= new ArrayList<SimpleGrantedAuthority>();
		for(Role i : userRole) {
			listRole.add(new SimpleGrantedAuthority("ROLE_"+i.getNameRole()));
		}
		return listRole;

	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.getIsEnable();
	}

}
