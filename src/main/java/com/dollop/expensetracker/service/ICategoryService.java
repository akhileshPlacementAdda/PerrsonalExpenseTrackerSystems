package com.dollop.expensetracker.service;

import com.dollop.expensetracker.entity.Category;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.payload.CategoryDTO;
import com.dollop.expensetracker.response.ApiResponse;

public interface ICategoryService {
	ApiResponse createCategory(CategoryDTO categoryDTO, User admin);
	ApiResponse updateCategory(Long categoryId, CategoryDTO categoryDTO, User admin);
	ApiResponse deleteCategory(Long categoryId, User admin);
	//ApiResponse findById(Lo)
	Category findById(Long categoryId);
}
