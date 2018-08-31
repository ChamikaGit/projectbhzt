package com.fidenz.chami.dev.bhooztapp.models;

/**
 * Created by fidenz on 6/19/18.
 */

public class Products {

    private String Days;
    private String PiesCount;
    private int imagename;

    public Products() {
    }

    public Products(String days, String piesCount, int imagename) {
        Days = days;
        PiesCount = piesCount;
        this.imagename = imagename;
    }

    public String getDays() {
        return Days;
    }

    public void setDays(String days) {
        Days = days;
    }

    public String getPiesCount() {
        return PiesCount;
    }

    public void setPiesCount(String piesCount) {
        PiesCount = piesCount;
    }

    public int getImagename() {
        return imagename;
    }

    public void setImagename(int imagename) {
        this.imagename = imagename;
    }
}
