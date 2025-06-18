package com.dollop.expensetracker.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.expensetracker.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long>{
	boolean existsByCategoryName(String categoryName);
	@Query("SELECT c FROM Category c WHERE c.isDeleted = false")
	List<Category> findAllActiveCategories();
	List<Category> findByIsDeletedFalse();


}
