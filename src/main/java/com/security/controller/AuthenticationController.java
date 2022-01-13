package com.security.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.UserInfo;
import com.security.jwt.JwtUtils;
import com.security.repository.UserRepositoty;
import com.security.request.LoginRequest;
import com.security.request.ResponseToken;
import com.security.service.AuthenticationService;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepositoty userRepositoty;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationService authenticationService;

	// http://localhost:8080/login
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws MessagingException {

		System.out.println("Login value " + loginRequest.getUsername() + "  " + loginRequest.getPassword());

//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwtToken = jwtUtils.generateToken(loginRequest.getUsername());

		ResponseToken responseToken = authenticationService.login(loginRequest);
		if (responseToken.getJwt().equals("0")) {
			return ResponseEntity.badRequest().body("Please active your account");
		} else if (responseToken.getJwt().equals("1")) {
			return ResponseEntity.badRequest().body("Account not found");
		}

		return ResponseEntity.ok(responseToken);
		// return ResponseEntity.ok("okkk"); authenticationService.login(loginRequest);

//	 	{
//	 		"username" : "ebrahimkhanobak@gmail.com",
//	 		"password" : "11"
//	 	}

	}

	// http://localhost:8080/signup
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserInfo user) throws MessagingException {

		int result = authenticationService.userSignUp(user);

		if (result == 0) {
			return ResponseEntity.badRequest().body("This email already exists");
		}

		// return ResponseEntity.ok("singup successfull");
		return ResponseEntity.ok().body("singup successfull");

//		{
//			"username" : "ibrahim",
//			"password" : "11",
//			"gmail" : "ebrahimkhanobak@gmail.com",
//			"phoneNumber" : "01929345678",
//			"district" : "Khulna"
//		}

//		{"username":"khan","gmail":"ebrahimkhanobak@gmail.com","phoneNumber":"1234567890","district":"Khnaluna"}

	}

	// http://localhost:8080/confirmaccount
	@GetMapping("/confirm-account")
	public ResponseEntity<?> confirmaccount(@RequestParam("token") String token) throws MessagingException {

		int res = authenticationService.confirmaccount(token);

		if (res == 1) {
			return ResponseEntity.ok("Account Confirmed");
		} else if (res == 2) {
			return ResponseEntity.ok("Time is expired.Please try again");
		}

		return ResponseEntity.badRequest().body("Gmail varyfication is failed");

	}

}
