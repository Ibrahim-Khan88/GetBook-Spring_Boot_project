package com.security.request;

public class ResponseToken {

	private String jwt;

	private int userId;

	public ResponseToken() {
	}

	public ResponseToken(String jwt, int userId) {
		this.jwt = jwt;
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
