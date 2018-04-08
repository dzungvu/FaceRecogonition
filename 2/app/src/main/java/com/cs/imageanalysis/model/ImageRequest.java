package com.cs.imageanalysis.model;

/**
 * Use to
 * Created by DzungVu on 9/4/2017.
 */

public class ImageRequest {
    private int id;
    private String title;
    private String value;

    public ImageRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
