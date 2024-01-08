package com.fitlife.app.Security;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails user = userService.loadUserByUsername(username);

		String passDB = user.getPassword();
		if (passwordEncoder.matches(password, passDB)) {
			List<GrantedAuthority> grand = new ArrayList<>();
//			Collection<Role> listRole = userDB.getRoles();
//			for (Role r : listRole) {
//				grand.add(new SimpleGrantedAuthority(r.getNameRole()));
//			}
			return new UsernamePasswordAuthenticationToken(username, passDB, grand);
		} else
			throw new BadCredentialsException("Password incorrect");

	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
