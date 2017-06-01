package com.natodroid.flaggamenew.model;

/**
 * Created by natiqmustafa on 01.03.2017.
 */

public class Country {
    private int id;
    private String shorName;
    private String longName;
    private String capital;
    private String phoneCode;
    private String tdl;
    private String region;
    private String imgName;

    public Country() {
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShorName() {
        return shorName;
    }

    public void setShorName(String shorName) {
        this.shorName = shorName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getTdl() {
        return tdl;
    }

    public void setTdl(String tdl) {
        this.tdl = tdl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
