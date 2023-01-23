package com.fiteprojects.fitegis.Models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "lectures", schema = "fite_geometry", catalog = "postgres")
public class Lecture extends GenericModel implements Serializable {

    @Column(name = "day")
    Integer day;
    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm:ss" , timezone = "Asia/Damascus")
    Time startTime;
    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm:ss" , timezone = "Asia/Damascus")
    Time endTime;
    @Column(name = "actual_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Damascus")
    Timestamp actualStartTime;
    @Column(name = "actual_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "Asia/Damascus")
    Timestamp actualEndTime;
    @ManyToOne(targetEntity = Course.class)
    Course course;
    @JsonIgnore
    @ManyToOne(targetEntity = Location.class)
    Location location;
    @Column(name = "course_id", insertable = false, updatable = false)
    Integer courseId;
    @Column(name = "location_id", insertable = false, updatable = false)
    Integer locationId;

    @Column(name = "class")
    Integer classNumber;
    @Column(name = "group_number")
    Integer groupNumber;
    @Column(name = "section")
    Integer sectionNumber;
    @Column(name = "others")
    String others;
    @Column(name = "en_lecturer")
    String en_lecturer;
    @Column(name = "ar_lecturer")
    String ar_lecturer;
    @Transient
    String lecturer;
    @Transient
    String locationName;

    @Column(name = "type")
    Integer type;

    @Column(name = "is_active")
    Boolean isActive;
    @JsonIgnore
    @ManyToOne(targetEntity = Location.class)
    Location actualLocation;
    @Column(name = "actual_location_id", insertable = false, updatable = false)
    Integer actualLocationId;
    @Column(name = "updated")
    Boolean updated;
    @Column(name = "expiration_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Damascus")
    Timestamp expirationDate;
    @Transient
    Integer weeksExpirationCount;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Timestamp getActualStartTime() {
        return actualStartTime;
    }


    public void setActualStartTime(Timestamp actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Timestamp getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Timestamp actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer materialId) {
        this.courseId = materialId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classNumber) {
        this.classNumber = classNumber;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(Integer sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getEn_lecturer() {
        return en_lecturer;
    }

    public void setEn_lecturer(String en_lecturer) {
        this.en_lecturer = en_lecturer;
    }

    public String getAr_lecturer() {
        return ar_lecturer;
    }

    public void setAr_lecturer(String ar_lecturer) {
        this.ar_lecturer = ar_lecturer;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Location getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(Location actualLocation) {
        this.actualLocation = actualLocation;
    }

    public Integer getActualLocationId() {
        return actualLocationId;
    }

    public void setActualLocationId(Integer actualLocationId) {
        this.actualLocationId = actualLocationId;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getWeeksExpirationCount() {
        return weeksExpirationCount;
    }

    public void setWeeksExpirationCount(Integer weeksExpirationCount) {
        this.weeksExpirationCount = weeksExpirationCount;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setLecturerName(String lang) {
        if (lang.equals("ar"))
            this.lecturer = ar_lecturer;
        if (lang.equals("en"))
            this.lecturer = en_lecturer;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
