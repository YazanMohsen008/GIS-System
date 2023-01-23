package com.fiteprojects.fitegis.Models.DTO;


public class LocationDTO {
    Integer Id;
    String en_name;
    String ar_name;
    Integer floor;
    String type;
    Boolean isLectureHolder;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getLectureHolder() {
        return isLectureHolder;
    }

    public void setLectureHolder(Boolean lectureHolder) {
        isLectureHolder = lectureHolder;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
