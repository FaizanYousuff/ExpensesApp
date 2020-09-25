package com.faizan.myexpenses.DataLayer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class OtherExpense {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String expenseName;

    @NonNull
    private int totalAmount;

    private int spentAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(int spentAmount) {
        this.spentAmount = spentAmount;
    }

    @NonNull
    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(@NonNull String expenseName) {
        this.expenseName = expenseName;
    }
}

