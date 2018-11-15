package com.sharnit.banglalinkqms.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Display implements Parcelable{

    String counter, token;

    public Display() {
    }

    public Display(String counter, String token) {

        this.counter = counter;
        this.token = token;
    }

    protected Display(Parcel in) {
        counter = in.readString();
        token = in.readString();
    }

    public static final Creator<Display> CREATOR = new Creator<Display>() {
        @Override
        public Display createFromParcel(Parcel in) {
            return new Display(in);
        }

        @Override
        public Display[] newArray(int size) {
            return new Display[0];
        }
    };

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(counter);
        parcel.writeString(token);
    }
}
