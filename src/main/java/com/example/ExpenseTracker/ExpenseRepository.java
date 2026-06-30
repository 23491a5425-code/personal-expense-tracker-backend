package com.example.ExpenseTracker;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    
    List<Expense> findByUserIdOrderByDateDesc(Long userId);
    Optional<Expense> findByIdAndUserId( Long id, Long userId);
}
