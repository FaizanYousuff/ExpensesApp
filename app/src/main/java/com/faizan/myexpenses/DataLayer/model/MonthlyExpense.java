package com.faizan.myexpenses.DataLayer.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MonthlyExpense {
    @PrimaryKey
    private int monthNumber;

    private String month;

    private String saved;

    private String expenses;

    private String income;

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSaved() {
        return saved;
    }

    public void setSaved(String saved) {
        this.saved = saved;
    }

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
