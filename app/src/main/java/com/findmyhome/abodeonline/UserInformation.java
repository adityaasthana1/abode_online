package com.findmyhome.abodeonline;

public class UserInformation {

    public String firstname,lastname,living,workplace;
    public long phone;

    public UserInformation(String firstname, String lastname, long phone,String living,String workplace) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.living = living;
        this.workplace=workplace;
    }

    public String getLiving() {
        return living;
    }

    public String getWorkplace() {
        return workplace;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public long getPhone() {
        return phone;
    }
}
