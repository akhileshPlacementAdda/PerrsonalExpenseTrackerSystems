package com.dollop.expensetracker.entity;

import java.time.LocalDateTime;

import com.dollop.expensetracker.audit.Audittable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Goal extends Audittable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @NotBlank
    private String goalDescription;

    @Positive
    private double goalTargetAmount;

    private double goalSavedAmount;
    
   
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;


}
