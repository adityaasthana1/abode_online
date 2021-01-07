package com.abode.abodeonline;

public class UserInfoAdapter {
    public String user_firstname, user_lastname, user_phone, user_city, user_Address, user_postalcode;

    public UserInfoAdapter() {}

    public UserInfoAdapter(String user_firstname, String user_lastname, String user_phone, String user_city, String user_Address, String user_postalcode) {
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_phone = user_phone;
        this.user_city = user_city;
        this.user_Address = user_Address;
        this.user_postalcode = user_postalcode;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_Address() {
        return user_Address;
    }

    public void setUser_Address(String user_Address) {
        this.user_Address = user_Address;
    }

    public String getUser_postalcode() {
        return user_postalcode;
    }

    public void setUser_postalcode(String user_postalcode) {
        this.user_postalcode = user_postalcode;
    }
}
