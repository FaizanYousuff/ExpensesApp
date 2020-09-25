package com.faizan.myexpenses.DataLayer.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "expense_table" ,foreignKeys = @ForeignKey(entity = MonthlyExpense.class,
        parentColumns = "monthNumber", childColumns = "expenseOfMonth",onDelete = CASCADE))
public class Expense {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String amount;

    @NonNull
    private String expenseOf;

    @NonNull
    private String date;

    private String description;

    @NonNull
    private String expenseOfMonth;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    @NonNull
    public String getAmount() {
        return amount;
    }

    public void setAmount(@NonNull String amount) {
        this.amount = amount;
    }

    @NonNull
    public String getExpenseOf() {
        return expenseOf;
    }

    public void setExpenseOf(@NonNull String expenseOf) {
        this.expenseOf = expenseOf;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @NonNull
    public String getExpenseOfMonth() {
        return expenseOfMonth;
    }

    public void setExpenseOfMonth(@NonNull String expenseOfMonth) {
        this.expenseOfMonth = expenseOfMonth;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
