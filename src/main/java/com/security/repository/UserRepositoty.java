package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.entity.UserInfo;

public interface UserRepositoty extends JpaRepository<UserInfo, Integer> {

	@Query("SELECT u FROM UserInfo u WHERE username=:username")
	UserInfo getRequestedUser(@Param("username") String username);

	Boolean existsBygmail(String gmail);

	Boolean existsById(int id);

	UserInfo findByGmail(String gmail);

	UserInfo findByUsername(String username);

}
