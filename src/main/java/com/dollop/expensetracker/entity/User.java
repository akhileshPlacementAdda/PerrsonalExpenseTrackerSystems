package com.dollop.expensetracker.entity;


import java.util.ArrayList;
import java.util.List;


import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long userId;

	    @Column(nullable = false)
	    private String userName;

	    @Email
	    @Column(nullable = false, unique = true)
	    private String userEmail;

	    @Column(nullable = false)
	    private String userPassword;

	    @Enumerated(EnumType.STRING)
	    private Role role;
	    
	    @Enumerated(EnumType.STRING)
	    private Status userStatus ;

	    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Category> categories = new ArrayList<>();

	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Transaction> transactions = new ArrayList<>();

	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Budget> budgets ;

	    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JsonIgnore
	    private List<Goal> goals = new ArrayList<>();
	    
	    @Column(nullable = false)
	    private boolean isDeleted = false;  // Admin Register TestRunners
	    
	    @Column(name = "is_logged_in")
	    private Boolean isLoggedIn = false;

	}

