package com.dollop.expensetracker.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dollop.expensetracker.payload.BudgetDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.IBudgetService;


@RestController
@RequestMapping("api/budget")
public class BudgetController {
	@Autowired
	private IBudgetService budgetService;
	
	  @PostMapping("/create")
	    public ResponseEntity<ApiResponse> createBudget(@RequestBody BudgetDTO budgetDTO) {
	        return new ResponseEntity<ApiResponse>(budgetService.createBudget(budgetDTO),HttpStatus.CREATED);
	    }

}
