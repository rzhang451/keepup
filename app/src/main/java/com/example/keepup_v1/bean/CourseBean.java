package com.example.keepup_v1.bean;

import java.io.Serializable;

public class CourseBean implements Serializable {
    private String img;
    private String duration;
    private String name;
    private String way;


    public CourseBean(String img, String duration, String name,String way) {
        this.img = img;
        this.duration = duration;
        this.name = name;
        this.way= way;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }
}
