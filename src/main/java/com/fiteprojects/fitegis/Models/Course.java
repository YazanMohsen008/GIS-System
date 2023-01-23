package com.fiteprojects.fitegis.Models;


import javax.persistence.*;

@Entity
@Table(name = "courses", schema = "fite_geometry", catalog = "postgres")
public class Course extends GenericModel {

    @Column(name = "english_name")
    String en_name;
    @Column(name = "arabic_name")
    String ar_name;
    @Transient
    String name;

    @Column(name = "year")
    Integer year;
    @Column(name = "specialization")
    Integer specialization;


    public void setEn_name(String englishName) {
        this.en_name = englishName;
    }

    public void setAr_name(String arabicName) {
        this.ar_name = arabicName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Integer specialization) {
        this.specialization = specialization;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setNameByLang(String lang) {
        if (lang.equals("ar"))
            this.name = ar_name;
        else
            this.name = en_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
