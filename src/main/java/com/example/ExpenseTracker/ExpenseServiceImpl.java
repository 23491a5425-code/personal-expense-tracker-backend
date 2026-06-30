package com.example.ExpenseTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, UserService userService){
        this.expenseRepository=expenseRepository;
        this.userService=userService;
    }
   // private static final AtomicLong a= new AtomicLong();
    @Override
    public List<Expense> getExpenseByDay(String date, Long userId){
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .filter(e ->
                        e.getDate().equalsIgnoreCase(date))
                .toList();
    }
     @Override
    public List<Expense> getExpenseByMonthAndCategory(String category, String month,Long userId){
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .filter(e ->
                        e.getCategory().equalsIgnoreCase(category)
                        && e.getDate().startsWith(month))
                .toList();
    }
     @Override
    public List<String> getCategories(Long userId){
        return expenseRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(Expense::getCategory)
                .distinct()
                .toList();
                
    }

    public Optional<Expense> getExpenseById(Long id, Long userId){
        return expenseRepository.findByIdAndUserId(id, userId).stream().
                    filter(e->e.getId().equals(id)).findFirst();
    }

    @Override
    public Expense addExpense(Expense expense, Long userId){
        Optional<AppUser> userOptional=userService.findUserById(userId);
        if(userOptional.isPresent()){
            AppUser user = userOptional.get();
            expense.setUser(user);
            return expenseRepository.save(expense);
        }else{
            throw new RuntimeException("user not found");
        }
    }

    @Override
    public boolean updateExpense(Expense updatedExpense, Long userId){
        Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(
            updatedExpense.getId(), userId);
        if(existingExpense.isPresent()){
            updatedExpense.setUser(existingExpense.get().getUser());
            expenseRepository.save(updatedExpense);
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteExpense(Long id, Long userId){
        Optional<Expense> existingExpense = expenseRepository.findByIdAndUserId(id, userId);
        if(existingExpense.isPresent()){
           expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Expense> getAllUserExpenses(Long userId){
        return new ArrayList<>(expenseRepository.findByUserIdOrderByDateDesc(userId));
    }
}
