package com.example.ugcablefaultdetection;

public class users {

    String fname;
    String email;
    String phone;
    String isAdmin;
    String isUser;

    public users(){}

    public users(String fname, String email, String phone, String isAdmin, String isUser) {
        this.fname = fname;
        this.email = email;
        this.phone = phone;
        this.isAdmin = isAdmin;
        this.isUser = isUser;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getIsUser() {
        return isUser;
    }

    public void setIsUser(String isUser) {
        this.isUser = isUser;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
