package com.abode.abodeonline;

public class ReviewAdapter {
    public String userid,ratingawarded,ratingdescription,timestamp;

    public ReviewAdapter(){}

    public ReviewAdapter(String userid, String ratingawarded, String ratingdescription, String timestamp) {
        this.userid = userid;
        this.ratingawarded = ratingawarded;
        this.ratingdescription = ratingdescription;
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRatingawarded() {
        return ratingawarded;
    }

    public void setRatingawarded(String ratingawarded) {
        this.ratingawarded = ratingawarded;
    }

    public String getRatingdescription() {
        return ratingdescription;
    }

    public void setRatingdescription(String ratingdescription) {
        this.ratingdescription = ratingdescription;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
