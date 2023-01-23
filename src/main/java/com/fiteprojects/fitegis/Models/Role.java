package com.fiteprojects.fitegis.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "roles", schema = "fite_geometry", catalog = "postgres")
public class Role extends GenericModel {
    @Column(name = "english_name")
    String en_name;
    @Column(name = "arabic_name")
    String ar_name;
    @Transient
    String name;
    public Role() {
    }

    public Role(String en_name, String ar_name) {
        this.en_name = en_name;
        this.ar_name = ar_name;
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
