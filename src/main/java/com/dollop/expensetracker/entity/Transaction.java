package com.dollop.expensetracker.entity;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import com.dollop.expensetracker.audit.Audittable;
import com.dollop.expensetracker.enums.Role;
import com.dollop.expensetracker.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Transaction extends Audittable {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @PositiveOrZero
    @Column(nullable = false)
    private double transactionAmount;
    

    private String transactionNote;

    private boolean transactionRecurring;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;
    
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Temporal transactionDate;
//    
//    @Temporal(TemporalType.TIMESTAMP)
//    private Timestamp transactionStartDate;
//
//  
//    @Temporal(TemporalType.TIMESTAMP)
//    private Timestamp transactionEndDate;


}
