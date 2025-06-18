package com.dollop.expensetracker.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dollop.expensetracker.Repository.IUserRepository;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.enums.Status;



@Component
public class TestRunner implements CommandLineRunner {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		List<User> users = userRepository.findByRoleAndIsDeletedOrderByUserEmail(Role.ADMIN, false);
		    if (users == null || users.isEmpty()) {
		         User user1 = User.builder()
		                .userEmail("kamalgupta123@gmail.com")
		                .isDeleted(false)
		                .userPassword("kamal@123")
		                .userName("Kamal")
		                .userStatus(Status.ACTIVE)
		                .role(Role.ADMIN)
		                .build();

		         userRepository.save(user1);
		    }
	}
	
	
	
}
