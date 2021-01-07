package com.abode.abodeonline;

import android.os.Parcel;
import android.os.Parcelable;

public class EntityAdapter implements Parcelable {
    public String entityid,entityhashstring,entityname,entitydescription,entityphone,entityemail,entitystate,entitycity,entityaddress,entityrent;

    public  EntityAdapter(){}

    public EntityAdapter(String entityid, String entityhashstring, String entityname, String entitydescription, String entityphone, String entityemail, String entitystate, String entitycity, String entityaddress, String entityrent) {
        this.entityid = entityid;
        this.entityhashstring = entityhashstring;
        this.entityname = entityname;
        this.entitydescription = entitydescription;
        this.entityphone = entityphone;
        this.entityemail = entityemail;
        this.entitystate = entitystate;
        this.entitycity = entitycity;
        this.entityaddress = entityaddress;
        this.entityrent = entityrent;
    }

    protected EntityAdapter(Parcel in) {
        entityid = in.readString();
        entityhashstring = in.readString();
        entityname = in.readString();
        entitydescription = in.readString();
        entityphone = in.readString();
        entityemail = in.readString();
        entitystate = in.readString();
        entitycity = in.readString();
        entityaddress = in.readString();
        entityrent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entityid);
        dest.writeString(entityhashstring);
        dest.writeString(entityname);
        dest.writeString(entitydescription);
        dest.writeString(entityphone);
        dest.writeString(entityemail);
        dest.writeString(entitystate);
        dest.writeString(entitycity);
        dest.writeString(entityaddress);
        dest.writeString(entityrent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EntityAdapter> CREATOR = new Creator<EntityAdapter>() {
        @Override
        public EntityAdapter createFromParcel(Parcel in) {
            return new EntityAdapter(in);
        }

        @Override
        public EntityAdapter[] newArray(int size) {
            return new EntityAdapter[size];
        }
    };

    public String getEntityid() {
        return entityid;
    }

    public void setEntityid(String entityid) {
        this.entityid = entityid;
    }

    public String getEntityhashstring() {
        return entityhashstring;
    }

    public void setEntityhashstring(String entityhashstring) {
        this.entityhashstring = entityhashstring;
    }

    public String getEntityname() {
        return entityname;
    }

    public void setEntityname(String entityname) {
        this.entityname = entityname;
    }

    public String getEntitydescription() {
        return entitydescription;
    }

    public void setEntitydescription(String entitydescription) {
        this.entitydescription = entitydescription;
    }

    public String getEntityphone() {
        return entityphone;
    }

    public void setEntityphone(String entityphone) {
        this.entityphone = entityphone;
    }

    public String getEntityemail() {
        return entityemail;
    }

    public void setEntityemail(String entityemail) {
        this.entityemail = entityemail;
    }

    public String getEntitystate() {
        return entitystate;
    }

    public void setEntitystate(String entitystate) {
        this.entitystate = entitystate;
    }

    public String getEntitycity() {
        return entitycity;
    }

    public void setEntitycity(String entitycity) {
        this.entitycity = entitycity;
    }

    public String getEntityaddress() {
        return entityaddress;
    }

    public void setEntityaddress(String entityaddress) {
        this.entityaddress = entityaddress;
    }

    public String getEntityrent() {
        return entityrent;
    }

    public void setEntityrent(String entityrent) {
        this.entityrent = entityrent;
    }


}
