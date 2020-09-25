package com.faizan.myexpenses.DataLayer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.faizan.myexpenses.Utils.Constants;

@Entity(tableName = "credits_table")
public class Credits {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String amount;

    @NonNull
    private String to;

    @NonNull
    private String date;

    private String description;

    @NonNull
    private String from;

    // You need a constructor and a "getter" method for the data model class,
    // because that's how Room knows to instantiate your objects.


    public Credits(String amount, String to, String date, String description) {
        this.amount = amount;
        this.to = to;
        this.date = date;
        this.description = description;
        this.from = Constants.AUTHOR;
    }

    public Credits() {
    }

    public Credits(String amount, String to, String date, String description, String from) {
        this.amount = amount;
        this.to = to;
        this.date = date;
        this.description = description;
        this.from = from;
    }

    public int getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(@NonNull String amount) {
        this.amount = amount;
    }

    public void setTo(@NonNull String to) {
        this.to = to;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    public void setFrom(@NonNull String from) {
        this.from = from;
    }
}
