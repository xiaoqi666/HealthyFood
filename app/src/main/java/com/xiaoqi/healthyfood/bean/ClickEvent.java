package com.xiaoqi.healthyfood.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ClickEvent implements Parcelable/*序列化，可以用intent传递*/ {
    private String name;
    private int position;

    public ClickEvent(String name, int position) {
        this.name = name;
        this.position = position;
    }

    protected ClickEvent(Parcel in) {
        name = in.readString();
        position = in.readInt();
    }

    public static final Creator<ClickEvent> CREATOR = new Creator<ClickEvent>() {
        @Override
        public ClickEvent createFromParcel(Parcel in) {
            return new ClickEvent(in);
        }

        @Override
        public ClickEvent[] newArray(int size) {
            return new ClickEvent[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(position);
    }
}
