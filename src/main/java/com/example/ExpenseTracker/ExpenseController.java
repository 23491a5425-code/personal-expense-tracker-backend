package com.example.ExpenseTracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ExpenseController {

        private final ExpenseService expenseService;
        private final UserService userService;

        public ExpenseController(ExpenseService expenseService, UserService userService){
                this.expenseService=expenseService;
                this.userService=userService;
        }
    // 1. Get expenses by date
    @GetMapping("/expenses/day/{date}")
    public ResponseEntity<List<Expense>> getByDate(@PathVariable String date, Authentication authentication)
     {
        String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        List<Expense> expenses= expenseService.getExpenseByDay(date, user.getId());

        return ResponseEntity.ok(expenses);
    }

    // 2. Get expenses by category + month
    @GetMapping("/expenses/category/{category}/month")
    public ResponseEntity<List<Expense>> getByCategoryAndMonth(
            @PathVariable String category,
            @RequestParam String month, Authentication authentication) {
                String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        List<Expense> expenses= expenseService.getExpenseByMonthAndCategory(category, month, user.getId());
        return ResponseEntity.ok(expenses);
    }
      
    // 3. Get all categories
    @GetMapping("/expenses/categories")
    public ResponseEntity<List<String>> getCategories(Authentication authentication) {
        String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        List<String>categories = expenseService.getCategories(user.getId());
        if(categories.isEmpty()){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Optional<Expense>> getExpenseById(@PathVariable Long id,
         Authentication authentication){
            String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        return ResponseEntity.ok(expenseService.getExpenseById(id, user.getId()));
    }
    @PostMapping("/expenses")
    public ResponseEntity<Expense> addExpense(@RequestBody Expense expense ,
        Authentication authentication){
        String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        Expense newExpense= expenseService.addExpense(expense, user.getId());
        return new ResponseEntity<>(newExpense, HttpStatus.OK);
    }
    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense,
        Authentication authentication
    ){
        String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        expense.setId(id);
        boolean isUpdated = expenseService.updateExpense(expense,user.getId());
        if(isUpdated){
                return new ResponseEntity<>(expense, HttpStatus.OK);
        }
        else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id, Authentication authentication){
        
        String username= authentication.getName();
        AppUser user= userService.findByUsername(username);
        boolean isDeleted = expenseService.deleteExpense(id, user.getId());
        if(isDeleted){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}