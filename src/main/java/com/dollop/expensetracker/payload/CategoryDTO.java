package com.dollop.expensetracker.payload;

import java.util.List;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 @Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
	 private Long categoryId;

	    @NotBlank(message = "Category name is required")
	    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
	    private String categoryName;

	    @NotNull(message = "User ID is required")
	    private Long userId;

	    private List<Long> transactionIds;
	    
	    
	    private Boolean isDeleted = false;
}
