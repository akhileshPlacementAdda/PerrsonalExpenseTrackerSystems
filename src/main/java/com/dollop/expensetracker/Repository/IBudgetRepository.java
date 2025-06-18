package com.dollop.expensetracker.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dollop.expensetracker.entity.Budget;
import com.dollop.expensetracker.entity.Category;
import com.dollop.expensetracker.entity.User;


public interface IBudgetRepository extends JpaRepository<Budget, Long>{
//	@Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.category.id = :categoryId AND FUNCTION('MONTH', t.date) = :month AND FUNCTION('YEAR', t.date) = :year AND t.type = 'EXPENSE'")
//	Double getTotalExpensesForCategoryInMonth(Long userId, Long categoryId, int month, int year);

	@Query(value = "SELECT * FROM budget b " + "WHERE b.user_id = :userId " +"AND b.category_category_id = :categoryId " +"AND MONTH(b.created_at) = MONTH(CURDATE()) " +"AND YEAR(b.created_at) = YEAR(CURDATE())", nativeQuery = true)
	Optional<Budget> findCurrentMonthBudget(Long userId, Long categoryId);   
            
        // 
	Optional<Budget> findByCategoryAndUser(Category category,User user);
            

}
