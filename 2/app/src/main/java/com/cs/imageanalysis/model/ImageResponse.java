package com.cs.imageanalysis.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Use to
 * Created by DzungVu on 9/4/2017.
 */

public class ImageResponse implements Parcelable{
    private int age;
    private String description;
    private String errorcode;
    private String gender;
    private String id;
    private String major;
    private String name;
    private String school;
    private String status;
    private String title;

    public ImageResponse() {
    }

    protected ImageResponse(Parcel in) {
        age = in.readInt();
        description = in.readString();
        errorcode = in.readString();
        gender = in.readString();
        id = in.readString();
        major = in.readString();
        name = in.readString();
        school = in.readString();
        status = in.readString();
        title = in.readString();
    }

    public static final Creator<ImageResponse> CREATOR = new Creator<ImageResponse>() {
        @Override
        public ImageResponse createFromParcel(Parcel in) {
            return new ImageResponse(in);
        }

        @Override
        public ImageResponse[] newArray(int size) {
            return new ImageResponse[size];
        }
    };

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(age);
        parcel.writeString(description);
        parcel.writeString(errorcode);
        parcel.writeString(gender);
        parcel.writeString(id);
        parcel.writeString(major);
        parcel.writeString(name);
        parcel.writeString(school);
        parcel.writeString(status);
        parcel.writeString(title);
    }
}
