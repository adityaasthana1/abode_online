package com.findmyhome.abodeonline.Information;

public class InformationPg {
    public String pgname,pgcity,pgaddress,pgrent,pgcontact,pgowneremail;

    public InformationPg(){

    }

    public InformationPg(String pgname, String pgcity, String pgaddress, String pgrent, String pgcontact, String pgowneremail) {
        this.pgname = pgname;
        this.pgcity = pgcity;
        this.pgaddress = pgaddress;
        this.pgrent = pgrent;
        this.pgcontact = pgcontact;
        this.pgowneremail = pgowneremail;
    }

    public String getPgname() {
        return pgname;
    }

    public void setPgname(String pgname) {
        this.pgname = pgname;
    }

    public String getPgcity() {
        return pgcity;
    }

    public void setPgcity(String pgcity) {
        this.pgcity = pgcity;
    }

    public String getPgaddress() {
        return pgaddress;
    }

    public void setPgaddress(String pgaddress) {
        this.pgaddress = pgaddress;
    }

    public String getPgrent() {
        return pgrent;
    }

    public void setPgrent(String pgrent) {
        this.pgrent = pgrent;
    }

    public String getPgcontact() {
        return pgcontact;
    }

    public void setPgcontact(String pgcontact) {
        this.pgcontact = pgcontact;
    }

    public String getPgowneremail() {
        return pgowneremail;
    }

    public void setPgowneremail(String pgowneremail) {
        this.pgowneremail = pgowneremail;
    }
}

