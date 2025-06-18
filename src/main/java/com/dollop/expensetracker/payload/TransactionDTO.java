package com.dollop.expensetracker.payload;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.aspectj.lang.annotation.After;

import com.dollop.expensetracker.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Temporal;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionDTO {
	 private Long transactionId;

	    @NotNull(message = "Transaction type is required")
	    private TransactionType transactionType;

	    @PositiveOrZero(message = "Amount must be zero or positive")
	    @NotNull(message = "Amount is required")
	    private Double transactionAmount;
    
	    @NotBlank(message = "Transaction Note description must be required")
	    @Size(max =  500, message = "Transaction Note  must be less than 500 latter")
	    private String transactionNote;
	    
	    private boolean transactionRecurring;

	    @NotNull(message = "Category ID is required")
	    private Long categoryId;

	    @NotNull(message = "User ID is required")
	    private Long userId;
	    
	    private Timestamp createdAt;
	    
	    private Timestamp updatedAt;

	    
//	    @NotNull(message = "Start date is required")
//	    @FutureOrPresent(message = "Start date must be in the present or future")	    
//	    private Timestamp transactionStartDate;
//	    
//	    @NotNull(message = "Start date is required")
//	    @FutureOrPresent(message = "End date must be in the present or future")
//	    private Timestamp transactionEndDate;

	    //
//	    @NotNull(message = "Transaction date is required")
//	    @FutureOrPresent(message = "End date must be in the future")
//	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
//	    private LocalDateTime transactionDate;
	

	   
}
