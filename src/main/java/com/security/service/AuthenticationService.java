package com.security.service;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entity.ConfirmationToken;
import com.security.entity.UserInfo;
import com.security.jwt.JwtUtils;
import com.security.repository.ConfirmationTokenRepository;
import com.security.repository.UserRepositoty;
import com.security.request.LoginRequest;
import com.security.request.ResponseToken;

@Service
public class AuthenticationService {

	private JavaMailSender javaMailSender;

	@Autowired
	private UserRepositoty userRepositoty;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	public void MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public ResponseToken login(LoginRequest loginRequest) throws MessagingException {

		UserInfo userInfo = userRepositoty.findByGmail(loginRequest.getUsername());
		ResponseToken responseToken = new ResponseToken();

		if (userInfo != null) {
			if (userInfo.isEnable()) {
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
								loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwtToken = jwtUtils.generateToken(loginRequest.getUsername());
				responseToken.setJwt(jwtToken);
				responseToken.setUserId(userInfo.getUserId());
				return responseToken;

			}
			responseToken.setJwt("0");
			return responseToken;
		}
		responseToken.setJwt("1");
		return responseToken;
	}

	public int userSignUp(UserInfo user) throws MessagingException {

		if (userRepositoty.existsBygmail(user.getGmail())) {
			return 0;
		}

		System.out.println(user.toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnable(false);
		userRepositoty.save(user);

		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationToken.setExpireDate(calExpiryDate(3));
		confirmationTokenRepository.save(confirmationToken);
		System.out.println(confirmationToken.toString());

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setFrom("ibrahimkhanextra83@gmail.com");
		helper.setTo(user.getGmail());
		helper.setSubject("Complete Registration!");
		helper.setText("To confirm your account, please click here : " + "http://localhost:8080/confirm-account?token="
				+ confirmationToken.getConfirmationToken());
		javaMailSender.send(mimeMessage);

		return 1;
	}

	public int confirmaccount(String token) {

		ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);

		if (confirmationToken != null) {
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			UserInfo userInfo = confirmationToken.getUser();

//			System.out.println(userInfo.toString());

			if (!confirmationToken.getExpireDate().before(currentTime)) {

				userInfo.setEnable(true);
				userRepositoty.save(userInfo);

				confirmationToken.setConfirmationToken(null);
				confirmationTokenRepository.save(confirmationToken);
				return 1;

			} else {
				return 2;
			}

		} else {
			System.out.println("else ");
		}
		return 0;
	}

	public Timestamp calExpiryDate(int expiryTimeMinute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, expiryTimeMinute);
		return new Timestamp(cal.getTime().getTime());
	}
}
