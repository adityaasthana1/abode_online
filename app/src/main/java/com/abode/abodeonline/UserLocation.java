package com.abode.abodeonline;

public class UserLocation {
    String usercountry, userstate, usercity,userpostalcode,useraddress,userlongitude,userlatitude;

    public UserLocation(){
        this.usercountry = "NONE";
        this.userstate = "NONE";
        this.usercity = "NONE";
        this.userpostalcode = "NONE";
        this.useraddress = "NONE";
        this.userlongitude = "NONE";
        this.userlatitude = "NONE";
    }

    public UserLocation(String usercountry, String userstate, String usercity, String userpostalcode, String useraddress, String userlongitude, String userlatitude) {
        this.usercountry = usercountry;
        this.userstate = userstate;
        this.usercity = usercity;
        this.userpostalcode = userpostalcode;
        this.useraddress = useraddress;
        this.userlongitude = userlongitude;
        this.userlatitude = userlatitude;
    }

    public String getUsercountry() {
        return usercountry;
    }

    public String getUserstate() {
        return userstate;
    }

    public String getUsercity() {
        return usercity;
    }

    public String getUserpostalcode() {
        return userpostalcode;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public String getUserlongitude() {
        return userlongitude;
    }

    public String getUserlatitude() {
        return userlatitude;
    }

    public void setUsercountry(String usercountry) {
        this.usercountry = usercountry;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }

    public void setUsercity(String usercity) {
        this.usercity = usercity;
    }

    public void setUserpostalcode(String userpostalcode) {
        this.userpostalcode = userpostalcode;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public void setUserlongitude(String userlongitude) {
        this.userlongitude = userlongitude;
    }

    public void setUserlatitude(String userlatitude) {
        this.userlatitude = userlatitude;
    }
}
