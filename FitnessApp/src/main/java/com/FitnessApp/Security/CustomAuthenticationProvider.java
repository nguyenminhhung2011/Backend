package com.FitnessApp.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.FitnessApp.Model.Role;
import com.FitnessApp.Model.User;
import com.FitnessApp.Service.User.UserService;

import ch.qos.logback.classic.Logger;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	Logger logger = (Logger) org.slf4j.LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		logger.info(username);
		List<User> user = userService.findByUsername(username);
		if (user.isEmpty()) {

			throw new BadCredentialsException("User not found");
		}
		User userDB = user.get(0);
		String passDB = userDB.getPassword();
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
