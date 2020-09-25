package com.faizan.myexpenses.DataLayer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = OtherExpense.class,
        parentColumns = "id",childColumns = "expenseId",onDelete = CASCADE))
public class DetailOtherExpense {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int expenseId;

    @NonNull
    private int amount;

    @NonNull
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }
}
