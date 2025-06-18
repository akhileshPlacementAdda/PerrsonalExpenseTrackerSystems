package com.dollop.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.expensetracker.entity.User;
import com.dollop.expensetracker.exception.UnauthorzationException;
import com.dollop.expensetracker.payload.CategoryDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.ICategoryService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class Categoryontroller {
	@Autowired
	private ICategoryService categoryService;
	
	  @PostMapping("/admin/register")
	    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryDTO categoryDTO, HttpServletRequest request) {
	        User admin = (User) request.getAttribute("admin"); //  from filter 
	        return new ResponseEntity<ApiResponse>(categoryService.createCategory(categoryDTO, admin), HttpStatus.CREATED);
	    }
	  
	  @PutMapping("/admin/update/{categoryId}")
	  public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId,@RequestBody CategoryDTO categoryDTO, HttpServletRequest request)throws UnauthorzationException {
		  User admin = (User) request.getAttribute("admin"); // set from filter 
		  System.err.println(categoryId+"idddddddddd"+categoryDTO+"ddddddddddttttttttooooooooo"+request);
		    return new ResponseEntity<ApiResponse>(categoryService.updateCategory(categoryId, categoryDTO, admin), HttpStatus.OK);
			  }
	      
	  @DeleteMapping("/admin/delete/{categoryId}")
	  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId, HttpServletRequest request) {
	      User admin = (User) request.getAttribute("admin");
	      return new ResponseEntity(categoryService.deleteCategory(categoryId, admin),HttpStatus.OK);
	  }

} 
