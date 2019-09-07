package com.findmyhome.abodeonline;

public class ImageUpload {
    public String ImageName,ImageUri;

    public ImageUpload(){}

    public ImageUpload(String imageName, String imageUri) {
        ImageName = imageName;
        ImageUri = imageUri;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }
}
