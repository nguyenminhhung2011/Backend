package com.fitlife.app.Security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//@Component
@AllArgsConstructor
public class CustomEntryPoint implements AuthenticationEntryPoint {

	private final String message;
	private final int status;
	public void commence(HttpServletResponse response)
			throws IOException {
		// TODO Auto-generated method stub
		 response.setContentType("application/json;charset=UTF-8");
	        response.setStatus(status);

	        Map<String, Object> error = new HashMap<>();
	        error.put("status", status);
	        error.put("message",message);

	        ObjectMapper mapper = new ObjectMapper();
	        mapper.writeValue(response.getOutputStream(), error);
	}
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	}


}
