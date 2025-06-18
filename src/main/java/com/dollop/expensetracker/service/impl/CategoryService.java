package com.dollop.expensetracker.service.impl;

import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.expensetracker.Repository.ICategoryRepository;
import com.dollop.expensetracker.entity.Category;
import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.exception.BadRequestException;
import com.dollop.expensetracker.exception.DuplicateResourceException;
import com.dollop.expensetracker.exception.ResourceNotFoundException;
import com.dollop.expensetracker.exception.UnauthorzationException;
import com.dollop.expensetracker.payload.CategoryDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.ICategoryService;

import jakarta.transaction.Transactional;
@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private ICategoryRepository categoryRepository;

	@Override
	public ApiResponse createCategory(CategoryDTO categoryDTO, User admin) {
	    if (admin == null || !Boolean.TRUE.equals(admin.getIsLoggedIn()) || !admin.getRole().equals(Role.ADMIN)) {
	        throw new UnauthorzationException("Only a logged-in admin can create categories");
	    }

	    if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isBlank()) {
	        throw new BadRequestException("Category name is required");
	    }

	 

	    boolean categoryExists = categoryRepository.existsByCategoryName(categoryDTO.getCategoryName().trim());
	    if (categoryExists) {
	        throw new DuplicateResourceException("Category already exists. Please use a different name.");
	    }

	    Category category = new Category();
	    BeanUtils.copyProperties(categoryDTO, category);
	    category.setCategoryName(categoryDTO.getCategoryName()); 
	    category.setUser(admin);
	    category.setCategoryId(categoryDTO.getCategoryId());

	    categoryRepository.save(category);

	    return ApiResponse.builder()
	            .message("Category added successfully")
	            .response(category)
	            .build();
	}

	@Override
	@Transactional
	public ApiResponse updateCategory(Long categoryId, CategoryDTO categoryDTO, User admin) {
	    if (admin == null || !Boolean.TRUE.equals(admin.getIsLoggedIn()) || !admin.getRole().equals(Role.ADMIN)) {
	        throw new UnauthorzationException("Only a logged-in admin can update categories");
	    }

	    if (categoryDTO.getCategoryName() == null || categoryDTO.getCategoryName().isBlank()) {
	        throw new BadRequestException("Category name must be provided");
	    }

	    Category existingCategory = categoryRepository.findById(categoryId)
	        .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

	    String newName = categoryDTO.getCategoryName().trim();

	    // Allow same name for current category, but prevent for others
	    if (!existingCategory.getCategoryName().equalsIgnoreCase(newName) &&
	            categoryRepository.existsByCategoryName(newName)) {
	        throw new DuplicateResourceException("Category name already exists");
	    }

	   
	    existingCategory.setCategoryName(newName);
	   
	    existingCategory.setUser(admin);  // Optional if tracking who updated

	    categoryRepository.save(existingCategory);

	    // Prepare response DTO
	    CategoryDTO updatedDTO = new CategoryDTO();
	    BeanUtils.copyProperties(existingCategory, updatedDTO);
	    updatedDTO.setCategoryId(existingCategory.getCategoryId()); 

	    return ApiResponse.builder()
	            .message("Category updated successfully")
	            .response(updatedDTO)
	            .build();
	}
	
	@Override
	public ApiResponse deleteCategory(Long categoryId, User admin) {
	    if (admin == null || !Boolean.TRUE.equals(admin.getIsLoggedIn()) || !admin.getRole().equals(Role.ADMIN)) {
	        throw new UnauthorzationException("Only a logged-in admin can delete categories");
	    }

	    System.err.println("=========  > 1 "+categoryId);
	    Category category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
	    
	    System.err.println("=========  > 2 "+category.getIsDeleted());

	    if (category.getIsDeleted()) {
	        throw new BadRequestException("Category is already deleted");
	    }
	    category.setUser(admin);
	    category.setIsDeleted(true);
	    categoryRepository.save(category);

	    return ApiResponse.builder()
	            .message("Category deleted successfully (soft delete)")
	            .response(null)
	            .build();
	}

	@Override
	public Category findById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Not Found with ID"));
	}


	

}
