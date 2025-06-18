package com.dollop.expensetracker.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dollop.expensetracker.Repository.ITransactionRepository;
import com.dollop.expensetracker.entity.Budget;
import com.dollop.expensetracker.entity.Category;
import com.dollop.expensetracker.entity.Transaction;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.exception.AmountException;
import com.dollop.expensetracker.exception.ResourceNotFoundException;
import com.dollop.expensetracker.payload.TransactionDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.IBudgetService;
import com.dollop.expensetracker.service.ICategoryService;
import com.dollop.expensetracker.service.ITransactionService;
import com.dollop.expensetracker.service.IUserService;
@Service
public class TransactionService implements ITransactionService{
	
	@Autowired
	private ITransactionRepository transactionRepository;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IUserService userService;
	@Lazy
	@Autowired
	private IBudgetService budgetService;
	
	
	@Override
	public Transaction findById(Long transactionId) {	
		return transactionRepository.findById(transactionId).orElseThrow(()->new ResourceNotFoundException("Transcation Not found with Id "));
	}


	@Override
	public ApiResponse createTransaction(TransactionDTO transactionDTO) {
		Category category = categoryService.findById(transactionDTO.getCategoryId());
		
		if(!category.getCategoryId().equals(transactionDTO.getCategoryId())) {
			throw new ResourceNotFoundException("category does not belong the user");
		}
		User user = userService.findById(transactionDTO.getUserId());
		
		Budget budget = budgetService.findByCategoryAndUser(category, user);
		
		//buget table we have two column one is total buget and another is bugetLimitAmount it is every trsaction Minus in 
		double budgetLimit = budget.getBudgetlimitAmount();  
		double totalSpent = transactionRepository.sumExpensesByUserAndCategory(user.getUserId(), category.getCategoryId());
		
		
		//totalSpent + TrsactionAmount(every time check trsacation amount)
		double latestTotal = totalSpent+transactionDTO.getTransactionAmount(); 
		double remainingBudget =budgetLimit - latestTotal;
		
		if(latestTotal > budgetLimit) {
			throw new AmountException("Transaction exceeds the allocated budget for this category");
			
		}
		
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(transactionDTO, transaction);
		transaction.setCategory(category);
		transaction.setUser(user);
		
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		
		TransactionDTO dto = new TransactionDTO();
		BeanUtils.copyProperties(savedTransaction, dto);
		dto.setCategoryId(savedTransaction.getCategory().getCategoryId());
		dto.setUserId(savedTransaction.getUser().getUserId());
		dto.setCreatedAt(savedTransaction.getCreatedAt());
		
		return ApiResponse.builder().message("Transaction Successfully Created ").response(dto).build();
	}
	
	
	

}
