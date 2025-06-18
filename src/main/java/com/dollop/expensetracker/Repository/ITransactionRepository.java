package com.dollop.expensetracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.expensetracker.entity.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long>{
	@Query("SELECT COALESCE(SUM(t.transactionAmount), 0) FROM Transaction t " +
		       "WHERE t.user.id = :userId " +
		       "AND t.category.id = :categoryId " +
		       "AND t.transactionType = com.dollop.expensetracker.enums.TransactionType.EXPENSE")
		double sumExpensesByUserAndCategory(
		        @Param("userId") Long userId,
		        @Param("categoryId") Long categoryId);
}
