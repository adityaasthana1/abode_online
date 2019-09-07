package com.findmyhome.abodeonline.Information;

public class HostelInfo {
    public String hostelname,hostelcity,hosteladdress,hostelrent,hostelcontact,OwnerEmail;

    public HostelInfo(){

    }

    public HostelInfo(String hostelname, String hostelcity, String hosteladdress, String hostelrent, String hostelcontact,String ownerEmail) {
        this.hostelname = hostelname;
        this.hostelcity = hostelcity;
        this.hosteladdress = hosteladdress;
        this.hostelrent = hostelrent;
        this.hostelcontact = hostelcontact;
        this.OwnerEmail = ownerEmail;
    }

    public String getHostelname() {
        return hostelname;
    }

    public void setHostelname(String hostelname) {
        this.hostelname = hostelname;
    }

    public String getHostelcity() {
        return hostelcity;
    }

    public void setHostelcity(String hostelcity) {
        this.hostelcity = hostelcity;
    }

    public String getHosteladdress() {
        return hosteladdress;
    }

    public void setHosteladdress(String hosteladdress) {
        this.hosteladdress = hosteladdress;
    }

    public String getHostelrent() {
        return hostelrent;
    }

    public void setHostelrent(String hostelrent) {
        this.hostelrent = hostelrent;
    }

    public String getHostelcontact() {
        return hostelcontact;
    }

    public void setHostelcontact(String hostelcontact) {
        this.hostelcontact = hostelcontact;
    }
}
