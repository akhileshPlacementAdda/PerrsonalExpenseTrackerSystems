package com.dollop.expensetracker.entity;

import com.dollop.expensetracker.audit.Audittable;
import com.dollop.expensetracker.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Budget extends Audittable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long budgetid;

	@ManyToOne(optional = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Positive
	@Column( nullable = false)
	private double budgetlimitAmount;
	@Column( nullable = false)
	private double totalBudget;

	private Status status;
	
	

}
