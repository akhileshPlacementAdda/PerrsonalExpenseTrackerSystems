package com.dollop.expensetracker.service;

import com.dollop.expensetracker.entity.Transaction;
import com.dollop.expensetracker.payload.TransactionDTO;
import com.dollop.expensetracker.response.ApiResponse;

public interface ITransactionService {
	 Transaction findById(Long transactionId);
	 ApiResponse createTransaction(TransactionDTO transactionDTO);

}
