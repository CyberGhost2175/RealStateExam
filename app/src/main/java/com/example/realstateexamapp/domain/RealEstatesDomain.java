package com.example.realstateexamapp.domain;

import java.io.Serializable;
import java.util.List;

public class RealEstatesDomain implements Serializable {
    private Long id;
    private String title;
    private String address;
    private String description;
    private int bed;
    private int bath;
    private int price;
    private String titlePicture;
    private boolean wife;
    private String category;

    private String type;

    private List<String> pictures;
    public RealEstatesDomain(String title, String address, String description, int bed, int bath, int price, String pic, boolean wife) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.bed = bed;
        this.bath = bath;
        this.price = price;
        this.titlePicture = pic;
        this.wife = wife;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getBath() {
        return bath;
    }

    public void setBath(int bath) {
        this.bath = bath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitlePicture() {
        return titlePicture;
    }

    public void setTitlePicture(String titlePicture) {
        this.titlePicture = titlePicture;
    }

    public boolean isWife() {
        return wife;
    }

    public void setWife(boolean wife) {
        this.wife = wife;
    }
}
