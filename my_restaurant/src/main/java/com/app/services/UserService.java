package com.app.services;

import com.app.dto.Signup;

import jakarta.validation.Valid;

public interface UserService {
	
	Signup userRegistration(Signup reqDTO);
}
