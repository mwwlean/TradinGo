package com.example.tradingo;


import com.google.firebase.auth.FirebaseUser;

public class User {

    public String First_name,Last_Name, email;

    public User(){

    }
    public User(String Fname, String Lname, String email) {
        this.First_name = Fname;
        this.Last_Name = Lname;
        this.email = email;
    }
}