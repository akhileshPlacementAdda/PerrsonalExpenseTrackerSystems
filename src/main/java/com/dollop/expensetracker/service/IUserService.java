package com.dollop.expensetracker.service;

import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.payload.UserDTO;
import com.dollop.expensetracker.response.ApiResponse;

public interface IUserService {
	//ApiResponse userRegister(UserDTO userDTO , Role role); 
	ApiResponse userRegister(UserDTO userDTO, String loggedInAdminEmail);
	//ApiResponse userLogin(UserDTO userDTO,Role role);
	ApiResponse userLogin(UserDTO userDTO);
	ApiResponse adminLogin(UserDTO userDTO);
	ApiResponse adminLogout(String email, String password);
	ApiResponse getAllUsers(User admin);
	User findById(Long userId);
}
