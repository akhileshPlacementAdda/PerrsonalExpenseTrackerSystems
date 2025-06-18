package com.dollop.expensetracker.payload;

import java.io.ObjectInputFilter.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class BudgetDTO {
	 private Long budgetId;

	    @NotNull(message = "Category ID is required")
	    private Long categoryId;

	    @NotNull(message = "User ID is required")
	    private Long userId;



	    @Positive(message = "Budget limit amount must be positive")
	    @NotNull(message = "Budget limit amount is required")
	    private Double budgetLimitAmount;
	    
	    @Positive(message = "Budget Total amount must be positive")
	    @NotNull(message = "Budget Total amount is required")
	    private double totalBudget;

	    @NotNull(message = "Status is required")
	    private Status status;

}
