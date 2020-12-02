package com.example.chatapp.API.dialog;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class DialogEntity {
    public String _id;
    public String from;
    public String message;
}
