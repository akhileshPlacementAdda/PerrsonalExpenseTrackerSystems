package com.dollop.expensetracker.service;


import java.util.Optional;

import com.dollop.expensetracker.entity.Budget;
import com.dollop.expensetracker.entity.Category;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.payload.BudgetDTO;
import com.dollop.expensetracker.response.ApiResponse;

public interface IBudgetService {
	ApiResponse createBudget(BudgetDTO budgetDTO);
	ApiResponse updateBudget(Long budgetId, BudgetDTO budgetDTO);
	Budget findById(Long budgetId);
	Budget findByCategoryAndUser(Category category, User user);
	

}
