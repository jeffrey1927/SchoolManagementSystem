package com.JEFFREY1830180.schoolmanagementsystem;

public class Student {

    public String Email;
    public String Id;
    public String Name;
    public String Intake;
    public String Course;
    public String TypeOfAccount;


    public Student(){

    }

    public Student(String email, String id, String name,String intake,String course,String accountType) {
        Email = email;
        Id = id;
        Name = name;
        Intake = intake;
        Course = course;
        TypeOfAccount = accountType;


    }

}
