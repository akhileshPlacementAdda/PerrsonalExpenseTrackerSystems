package com.dollop.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.expensetracker.payload.TransactionDTO;
import com.dollop.expensetracker.response.ApiResponse;
import com.dollop.expensetracker.service.ITransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
	@Autowired
    private ITransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        ApiResponse response = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<ApiResponse>(response,HttpStatus.CREATED);
    }

}
