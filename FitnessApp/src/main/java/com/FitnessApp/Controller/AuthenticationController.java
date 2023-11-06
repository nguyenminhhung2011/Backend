package com.FitnessApp.Controller;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FitnessApp.DTO.AuthResponse;
import com.FitnessApp.DTO.AuthenRequest;
import com.FitnessApp.DTO.GymerRegistrationRequest;
import com.FitnessApp.DTO.ResponseObject;
import com.FitnessApp.Model.Gymer;
import com.FitnessApp.Utils.JwtTokenUtils;
import com.FitnessApp.Security.Model.Role;
import com.FitnessApp.Security.Model.RoleRepository;
import com.FitnessApp.Security.Model.User;
import com.FitnessApp.Service.UserService;
import com.FitnessApp.Service.GymerService.GymerService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class AuthenticationController {

	private  RoleRepository roleRepo;
	private  UserService uService;
	private  PasswordEncoder passEncoder;
	private  AuthenticationManager authenticationManager;
	private  GymerService gymerService;
	private  JwtTokenUtils jWTTokenUtils;

	@PostMapping("/loginGymer")
	public ResponseEntity<?> loginClient(@RequestBody AuthenRequest authRequest) {

		try {
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							authRequest.username(),
							authRequest.password()
					));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jWTTokenUtils.generateToken(authRequest.username());
			String freshToken = jWTTokenUtils.generateTokenRefresh(authRequest.username());

			User user = uService.findOneByUsername(authRequest.username());
			Set<Role> roles;
			roles = user.getRoles();
			boolean check = false;

			for (Role role : roles) {
                if (Objects.equals(role.getNameRole(), "CLIENT")) {
                    check = true;
                    break;
                }
            }

			if (!check) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseObject("failed", "Email hoặc mật khẩu chưa chính xác !", ""));
			}
			user.setRefreshToken(freshToken);
			uService.save(user);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("ok", "thành côngg", new AuthResponse(jwt, freshToken)));

		} catch (BadCredentialsException ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseObject("failed", "Đăng nhập không thành công", ""));
		}

	}

	@PostMapping("/registerGymer")
	public ResponseEntity<?> registerGymer(@RequestBody GymerRegistrationRequest registrationRequest) {

		try {
			// Kiểm tra xem Gymer đã tồn tại trong hệ thống chưa. Nếu có, trả về thông báo
			// lỗi.

			List<User> userExits = uService.findByUsername(registrationRequest.getUsername());
			if (!userExits.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseObject("failed", "Gymer đã tồn tại trong hệ thống.", ""));
			}

			// Tạo một Gymer mới từ dữ liệu đăng ký và lưu vào cơ sở dữ liệu.
			Role initRole = roleRepo.findRoleByName("CLIENT");

			User newUser = new User(registrationRequest.getUsername(),
					registrationRequest.getPassword(),true, initRole);
			Gymer newGymer = new Gymer();
			newGymer.setAge(registrationRequest.getAge());
			newGymer.setGender(registrationRequest.getGender());
			newGymer.setFullname(registrationRequest.getFullName());

			newGymer.setUser(newUser);
			uService.create(newUser);
			gymerService.save(newGymer);

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Đăng ký Gymer thành công.", ""));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ResponseObject("failed", "Đăng ký Gymer thất bại.", ""));
		}
	}

}