package com.example.ExpenseTracker;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getAllUserExpenses(Long userId);
    List<Expense> getExpenseByDay(String day,Long userId);
    List<Expense> getExpenseByMonthAndCategory(String category, String month, Long userId);
    List<String> getCategories(Long userId);
    Optional<Expense> getExpenseById(Long id, Long userId);
    Expense addExpense(Expense expense, Long userId);
    boolean updateExpense(Expense expense, Long userId);
    boolean deleteExpense(Long id, Long userId);
}

