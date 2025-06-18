package com.dollop.expensetracker.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;

public interface IUserRepository extends JpaRepository<User, Long> {
	// Duplicate email check
	Optional<User> findByUserEmail(String userEmail);
	// Admin Check 
	List<User>findByRoleAndIsDeletedOrderByUserEmail(Role role,Boolean isDeleted);

}
