package com.fiteprojects.fitegis.Models;



import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tools",schema = "fite_geometry",catalog = "postgres")
public class Tool extends GenericModel   {

    @Column(name = "english_name")
    String en_name;
    @Column(name = "arabic_name")
    String ar_name;
    @Column(name = "english_description")
    String en_description;
    @Column(name = "arabic_description")
    String ar_description;
    @Column(name = "status")
    String status;
    @Column(name = "price")
    Float price;
    @Column(name = "installation_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date installationDate;
    @Column(name = "image")
    String imagePath;
    @Transient
    Byte[] image;
    @Column(name = "image_name")
    String imageName;
    @ManyToOne(targetEntity = ToolType.class)
    ToolType toolType;
    @Column(name = "tool_type_id", insertable = false, updatable = false)
    Integer toolTypeId;
    @Column(name = "floor")
    Integer floor;

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

    public String getEn_description() {
        return en_description;
    }

    public void setEn_description(String en_description) {
        this.en_description = en_description;
    }

    public String getAr_description() {
        return ar_description;
    }

    public void setAr_description(String ar_description) {
        this.ar_description = ar_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String image) {
        this.imagePath = image;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public Integer getToolTypeId() {
        return toolTypeId;
    }

    public void setToolTypeId(Integer toolTypeId) {
        this.toolTypeId = toolTypeId;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}

