package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.ConfirmationToken;


public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
	
	ConfirmationToken findByConfirmationToken(String confirmationToken);

}
