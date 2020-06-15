package com.JEFFREY1830180.schoolmanagementsystem;

import com.google.firebase.database.PropertyName;

public class User {

    public String Email;
    public String Id;
    public String Name;
    public String TypeOfAccount;


    public User(){

    }

    public User(String email, String id, String name,String accountType) {
        Email = email;
        Id = id;
        Name = name;
        TypeOfAccount = accountType;


    }

}
