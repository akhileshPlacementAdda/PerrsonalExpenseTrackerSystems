package com.dollop.expensetracker.payload;

import java.util.List;


import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.enums.Status;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	 private Long userId; 

	    @NotBlank(message = "User name is required")
		@Size(min = 2,max = 50,message = "Name Char Is Out of range")
	    private String userName;

	    @NotBlank(message = "Email is required")
		@Column(unique = true, nullable = false)
		@Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z]+\\.[A-Za-z]{2,}$", message = "Invalid email. Use format: local@domain.com")
	    private String userEmail;

	    @NotBlank(message = "Password is required")
	    @Size(min = 6, message = "Password must be at least 6 characters long")
		@Pattern(regexp = "^[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&).")
	    private String userPassword;

	    @NotNull(message = "Role is required")
	    private Role role;
	    @NotNull(message = "Status is required")
	    
	    private Status userStatus;
	    
	    private List<Long> categoryIds;
	    
	    private List<Long> transactionIds;
	    private List<Long> budgetIds;
	    private List<Long> goalIds;
	    
	    private boolean isDeleted=false;
	   

	}



