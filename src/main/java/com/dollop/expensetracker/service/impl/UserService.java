package com.dollop.expensetracker.service.impl;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.expensetracker.Repository.IUserRepository;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.enums.Status;
import com.dollop.expensetracker.exception.BadRequestException;
import com.dollop.expensetracker.exception.DuplicateResourceException;
import com.dollop.expensetracker.exception.ResourceNotFoundException;
import com.dollop.expensetracker.exception.UnauthorzationException;
import com.dollop.expensetracker.payload.UserDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.IUserService;
@Service
public class UserService implements IUserService {
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public ApiResponse adminLogin(UserDTO userDTO) {
	   
	    if (userDTO.getUserEmail() == null || userDTO.getUserEmail().isBlank()) {
	        throw new BadRequestException("Email is required");
	    }

	    if (userDTO.getUserPassword() == null || userDTO.getUserPassword().isBlank()) {
	        throw new BadRequestException("Password is required");
	    }

	  
	    User user = userRepository.findByUserEmail(userDTO.getUserEmail())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

	    if (user.isDeleted()) {
	        throw new ResourceNotFoundException("This account is deleted");
	    }

	    
	    if (!user.getRole().equals(Role.ADMIN)) {
	        throw new UnauthorzationException("Only admin is allowed to login here");
	    }

	    if (!user.getUserStatus().equals(Status.ACTIVE)) {
	        throw new ResourceNotFoundException("Account is not active");
	    }

	    
	    if (!userDTO.getUserPassword().equals(user.getUserPassword())) {
	        throw new ResourceNotFoundException("Invalid email or password");
	    }
	    user.setIsLoggedIn(true); // ✅ mark admin as logged in
	    userRepository.save(user);


	  
	    return ApiResponse.builder()
	            .message("Admin login successful")
	            .response(user.getUserId())
	            .build();
	}


	
	@Override
	public ApiResponse userRegister(UserDTO userDTO, String loggedInAdminEmail) {
	    User admin = userRepository.findByUserEmail(loggedInAdminEmail)
	            .orElseThrow(() -> new UnauthorzationException("Unauthorized access"));

	    if (!Role.ADMIN.equals(admin.getRole())) {
	        throw new UnauthorzationException("Only Admin can register users");
	    }

	    // ✅ NEW CHECK: Admin must be logged in
	    if (!Boolean.TRUE.equals(admin.getIsLoggedIn())) {
	        throw new UnauthorzationException("Please login as admin before registering users");
	    }

	    if (userDTO.getUserEmail() == null || userDTO.getUserEmail().isBlank()) {
	        throw new BadRequestException("Email is required");
	    }

	    if (userDTO.getUserName() == null || userDTO.getUserName().isBlank()) {
	        throw new BadRequestException("User name is required");
	    }

	    if (userDTO.getUserPassword() == null || userDTO.getUserPassword().isBlank()) {
	        throw new BadRequestException("Password is required");
	    }

	    Optional<User> existUser = userRepository.findByUserEmail(userDTO.getUserEmail().trim());
	    if (existUser.isPresent()) {
	        throw new DuplicateResourceException("Email is already registered");
	    }

	    User newUser = new User();
	    BeanUtils.copyProperties(userDTO, newUser);
	    newUser.setUserEmail(userDTO.getUserEmail().trim());
	    newUser.setUserStatus(Status.ACTIVE);
	    newUser.setRole(Role.USER);
	    newUser.setDeleted(false);

	    userRepository.save(newUser);

	    return ApiResponse.builder()
	            .message("User Successfully Registered")
	            .response(newUser)
	            .build();
	}



	@Override
	public ApiResponse userLogin(UserDTO userDTO) {
	    if (userDTO.getUserEmail() == null || userDTO.getUserEmail().isBlank()) {
	        throw new BadRequestException("Email is required");
	    }

	    if (userDTO.getUserPassword() == null || userDTO.getUserPassword().isBlank()) {
	        throw new BadRequestException("Password is required");
	    }

	    User user = userRepository.findByUserEmail(userDTO.getUserEmail())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

	    if (user.isDeleted()) {
	        throw new ResourceNotFoundException("Your account has been deleted");
	    }

	    if (!user.getUserStatus().equals(Status.ACTIVE)) {
	        throw new ResourceNotFoundException("Your account is not active");
	    }

	    if (!userDTO.getUserPassword().equals(user.getUserPassword())) {
	        throw new ResourceNotFoundException("Invalid email or password");
	    }

	    return ApiResponse.builder()
	            .message("User login successful")
	            .response(user.getUserId())
	            .build();
	}
	
	@Override
	public ApiResponse adminLogout(String email, String password) {
	    if (email == null || email.isBlank()) {
	        throw new BadRequestException("Email is required for logout");
	    }

	    if (password == null || password.isBlank()) {
	        throw new BadRequestException("Password is required for logout");
	    }

	    User admin = userRepository.findByUserEmail(email.trim())
	            .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

	    if (admin.isDeleted()) {
	        throw new ResourceNotFoundException("This admin account is deleted");
	    }

	    if (!admin.getUserStatus().equals(Status.ACTIVE)) {
	        throw new ResourceNotFoundException("This admin account is not active");
	    }

	    if (!admin.getRole().equals(Role.ADMIN)) {
	        throw new UnauthorzationException("Only admins can perform logout here");
	    }

	    if (!admin.getUserPassword().equals(password)) {
	        throw new ResourceNotFoundException("Invalid email or password");
	    }

	    // ✅ FIX: Set isLoggedIn = false
	    admin.setIsLoggedIn(false);
	    userRepository.save(admin);

	    return ApiResponse.builder()
	            .message("Admin logged out successfully")
	            .response(admin.getUserId())
	            .build();
	}
	
	@Override
	public ApiResponse getAllUsers(User admin) {
	    if (!Boolean.TRUE.equals(admin.getIsLoggedIn())) {
	        throw new UnauthorzationException("Admin must be logged in");
	    }

	    if (!admin.getRole().equals(Role.ADMIN)) {
	        throw new UnauthorzationException("Only admins can access this");
	    }

	    List<User> allUsers = userRepository.findAll();

	    List<UserDTO> userDTOs = new ArrayList<>();
	    for (User user : allUsers) {
	        if (!user.isDeleted()) {
	            UserDTO dto = new UserDTO();
	            BeanUtils.copyProperties(user, dto);
	            userDTOs.add(dto);
	        }
	    }

	    return ApiResponse.builder()
	            .message("Users fetched successfully")
	            .response(userDTOs)
	            .build();
	}
	@Override
	public User findById(Long userId) {
		 return   userRepository.findById(userId)
	        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
	    
	   
	}


}
