package com.ums3.payload;

import lombok.Data;

import java.util.Date;
@Data
public class ErrorDetails {
    private String message;
    private Date date;
    private String Description;

    public ErrorDetails(String message, Date date, String description) {
        this.message = message;
        this.date = date;
        Description = description;
    }
}
