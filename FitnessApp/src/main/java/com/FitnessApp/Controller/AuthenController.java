package com.FitnessApp.Controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.FitnessApp.Security.JwtTokenHelper;
import com.FitnessApp.Security.Model.Role;
import com.FitnessApp.Security.Model.RoleRepository;
import com.FitnessApp.Security.Model.User;
import com.FitnessApp.Service.UserService;
import com.FitnessApp.Service.GymerService.GymerService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = { "Content-Type", "Authorization" })
public class AuthenController {

	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private UserService uService;

	@Autowired
	private PasswordEncoder passEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private GymerService gymerService;

	@Autowired
	JwtTokenHelper jWTTokenHelper;

	@PostMapping("/loginGymer")
	public ResponseEntity<?> loginClient(@RequestBody AuthenRequest authentRequest)
			throws InvalidKeySpecException, NoSuchAlgorithmException {

		try {
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authentRequest.getUsername(),
							authentRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jWTTokenHelper.generateToken(authentRequest.getUsername());
			String freshToken = jWTTokenHelper.generateTokenRefresh(authentRequest.getUsername());

			User user = (User) uService.findOneByUsername(authentRequest.getUsername());
			Set<Role> roles = new HashSet<>();
			roles = (Set<Role>) user.getRoles();
			boolean check = false;
			Iterator<Role> iterator = roles.iterator();
			while (iterator.hasNext()) {
				Role role = iterator.next();
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

//			if (gymerService.existsGymerByProfileId(registrationRequest.get())) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//						.body(new ResponseObject("failed", "Gymer đã tồn tại trong hệ thống.", ""));
//			}

			// Tạo một Gymer mới từ dữ liệu đăng ký và lưu vào cơ sở dữ liệu.
			Role initRole = roleRepo.findRoleByName("CLIENT");
			User newUser = new User(registrationRequest.getUsername(), registrationRequest.getPassword(), true,
					initRole);
			Gymer newGymer = new Gymer();
			newGymer.setAge(registrationRequest.getAge());
			newGymer.setGender(registrationRequest.getGender());
			newGymer.setFullname(registrationRequest.getUsername());

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

	private Gymer Gymer() {
		// TODO Auto-generated method stub
		return null;
	}

}