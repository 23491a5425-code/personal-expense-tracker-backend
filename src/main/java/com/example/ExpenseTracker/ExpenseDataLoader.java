package com.example.ExpenseTracker;


import jakarta.annotation.PostConstruct;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.InputStream;
import java.util.List;

@Component
public class ExpenseDataLoader {

    private static List<Expense> expenses;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = getClass().getResourceAsStream("/expenses.json");

            expenses = mapper.readValue(is, new TypeReference<List<Expense>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> getExpenses() {
        return expenses;
    }
}