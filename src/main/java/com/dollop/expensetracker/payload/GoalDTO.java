package com.dollop.expensetracker.payload;

import java.time.LocalDateTime;

import com.dollop.expensetracker.audit.Audittable;
import com.dollop.expensetracker.entity.Goal;
import com.dollop.expensetracker.entity.User;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GoalDTO {
	  private Long goalId;

	    @NotBlank(message = "Goal description is required")
	    private String goalDescription;

	    @Positive(message = "Target amount must be greater than zero")
	    @NotNull(message = "Target amount is required")
	    private Double goalTargetAmount;

	    @PositiveOrZero(message = "Saved amount cannot be negative")
	    private Double goalSavedAmount;

	    @NotNull(message = "User ID is required")
	    private Long userId;
	    
//	    @Future(message = "Target date must be in the future")
//	    @NotNull(message = "Target date is required")
//	    private LocalDateTime goalTargetDate;
//	    

}
