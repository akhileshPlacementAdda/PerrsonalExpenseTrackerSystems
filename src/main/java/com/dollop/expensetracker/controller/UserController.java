package com.dollop.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.payload.UserDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("/adminLogin")
	public ResponseEntity<ApiResponse>adminLogin(@RequestBody UserDTO userDTO){
		return new ResponseEntity<ApiResponse>(userService.adminLogin(userDTO),HttpStatus.OK);
	}
	@PostMapping("/adminLogout")
	public ResponseEntity<ApiResponse> adminLogout(
	        @RequestHeader("X-Admin-Email") String email,
	        @RequestHeader("X-Admin-Password") String password) {
	    ApiResponse response = userService.adminLogout(email, password);
	    return ResponseEntity.ok(response);
	}

//	@PostMapping("/register")
//	public ResponseEntity<ApiResponse>userRegister(@RequestBody UserDTO userDTO,@RequestParam Role role ){
//		return new ResponseEntity<ApiResponse>(userService.userRegister(userDTO, role),HttpStatus.CREATED);
//	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> userLogin(@RequestBody UserDTO userDTO) {
	    return new ResponseEntity<>(userService.userLogin(userDTO), HttpStatus.OK);
	}

	@PostMapping("/admin/register-user")
	public ResponseEntity<ApiResponse> registerUserByAdmin(@RequestBody UserDTO userDTO, HttpServletRequest request) {
	    User admin = (User) request.getAttribute("admin");
	    return new ResponseEntity<>(userService.userRegister(userDTO, admin.getUserEmail()), HttpStatus.CREATED);
	}

	
//	@PostMapping("/login")
//	public ResponseEntity<ApiResponse>userLogin(@RequestBody UserDTO userDTO,@RequestParam Role role){
//		return new ResponseEntity<ApiResponse>(userService.userLogin(userDTO, role),HttpStatus.CREATED);
//	}
	
	@GetMapping("/admin/get-all-users")
	public ResponseEntity<ApiResponse> getAllUsers(HttpServletRequest request) {
	    User admin = (User) request.getAttribute("admin");
	    return new ResponseEntity<ApiResponse>(userService.getAllUsers(admin),HttpStatus.OK);
	}

}
