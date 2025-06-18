package com.dollop.expensetracker.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.expensetracker.Repository.IBudgetRepository;
import com.dollop.expensetracker.Repository.ICategoryRepository;
import com.dollop.expensetracker.Repository.ITransactionRepository;
import com.dollop.expensetracker.Repository.IUserRepository;
import com.dollop.expensetracker.entity.Budget;
import com.dollop.expensetracker.entity.Category;
import com.dollop.expensetracker.entity.Transaction;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Status;
import com.dollop.expensetracker.exception.BadRequestException;
import com.dollop.expensetracker.exception.DuplicateResourceException;
import com.dollop.expensetracker.exception.ResourceNotFoundException;
import com.dollop.expensetracker.payload.BudgetDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.IBudgetService;
import com.dollop.expensetracker.service.ICategoryService;
import com.dollop.expensetracker.service.ITransactionService;
import com.dollop.expensetracker.service.IUserService;
@Service
public class BudgetService implements IBudgetService{
	 @Autowired
	 private IBudgetRepository budgetRepository;
	 @Autowired
	 private ICategoryService categoryService;
	 @Autowired
	 private IUserService userService;
	 @Autowired 
	 private ITransactionService transactionService;
	 

	@Override
	public ApiResponse createBudget(BudgetDTO budgetDTO) {
		Category category = categoryService.findById(budgetDTO.getCategoryId());
		
		User user = userService.findById(budgetDTO.getUserId());
		
//		  Get total expenses
//        Double totalExpenses = transactionRepository.getTotalExpensesForCategoryInMonth(
//                budgetDTO.getUserId(), dto.getCategoryId(), budgetDTO.getMonthlyBudget(), budgetDTO.getYearBudget());
//
//        if (totalExpenses == null) totalExpenses = 0.0;
//
//        if (budgetDTO.getBudgetLimitAmount() < totalExpenses) {
//            throw new BadRequestException("Budget limit cannot be less than total expenses" + totalExpenses);
//        }
		
//		
		 // Calculate remaining budget limit after existing expenses
	    //double remainingLimit = dto.getTotalBudget() - totalExpenses;
		
		 Optional<Budget> existingBudget = budgetRepository.findCurrentMonthBudget(budgetDTO.getUserId(), budgetDTO.getCategoryId());
		    if (existingBudget.isPresent()) {
		        throw new DuplicateResourceException("A budget for this category already exists for the current month.");
		    }
		    
		
		Budget budget = Budget.builder().budgetid(budgetDTO.getBudgetId()).user(user).category(category).budgetlimitAmount(budgetDTO.getBudgetLimitAmount()).totalBudget(budgetDTO.getTotalBudget()).status(Status.ACTIVE).build();
		 
		Budget budgetSave = budgetRepository.save(budget);
		
		return  ApiResponse.builder().message("Budget Successfully created").response(budgetSave).build();
	}


	@Override
	public ApiResponse updateBudget(Long budgetId, BudgetDTO budgetDTO) {
		 Budget budget = budgetRepository.findById(budgetId)
		            .orElseThrow(() -> new ResourceNotFoundException("Budget not found"));
		 
		     //Get total expenses so far for this budget's category (and month if needed)
		    Double totalExpenses = transactionRepository.getTotalExpensesForCategoryInMonth(
		            dto.getUserId(), dto.getCategoryId());

		    if (totalExpenses == null) totalExpenses = 0.0;

		    // Increase total budget by the new amount
		    double updatedTotalBudget = budget.getTotalBudget() + dto.getTotalBudget();

		    if (updatedTotalBudget < totalExpenses) {
		        throw new BadRequestException("Total budget after update cannot be less than total expenses: " + totalExpenses);
		    }

		    // Recalculate remaining budget
		    double newRemainingLimit = updatedTotalBudget - totalExpenses;

		    // Update the budget
		    budget.setTotalBudget(updatedTotalBudget);
		    budget.setBudgetlimitAmount(newRemainingLimit);
		    

		
		return ApiResponse.builder().message("Budget Successfully update").response(budget).build();
	}


	@Override
	public Budget findById(Long budgetId) {
		return budgetRepository.findById(budgetId).orElseThrow(()->new ResourceNotFoundException("Budget is not found with budgetId"));
	}


	@Override
	public Budget findByCategoryAndUser(Category category, User user) {
		
		return budgetRepository.findByCategoryAndUser(category, user).orElseThrow(()->new ResourceNotFoundException("No budget set for this category and user"));
	}
	
	
	

}
