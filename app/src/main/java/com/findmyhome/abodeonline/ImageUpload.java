package com.findmyhome.abodeonline;

public class ImageUpload {
   public String imagename,imageurl;

   public ImageUpload(){

   }

    public ImageUpload(String imagename, String imageurl) {
        this.imagename = imagename;
        this.imageurl = imageurl;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
