package com.fiteprojects.fitegis.Models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tool_types", schema = "fite_geometry", catalog = "postgres")
public class ToolType extends GenericModel   {

    @Column(name = "english_name")
    String en_name;
    @Column(name = "arabic_name")
    String ar_name;
    @Column(name = "icon_path")
    String iconPath;
    @Column(name = "updatable")
    Boolean updatable;

    @Transient
    Byte[] icon;
    @Column(name = "icon_name")
    String iconName;
    @Transient
    Integer toolsCount;
    @Transient
    Integer toolsCountInactive;

    public ToolType() {
    }

    public ToolType(String en_name, String ar_name, Boolean updatable) {
        this.en_name = en_name;
        this.ar_name = ar_name;
        this.updatable = updatable;
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


    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String icon) {
        this.iconPath = icon;
    }

    public Boolean getUpdatable() {
        return updatable;
    }

    public void setUpdatable(Boolean updatable) {
        this.updatable = updatable;
    }

    public Byte[] getIcon() {
        return icon;
    }

    public void setIcon(Byte[] icon) {
        this.icon = icon;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public Integer getToolsCount() {
        return toolsCount;
    }

    public void setToolsCount(Integer toolsCount) {
        this.toolsCount = toolsCount;
    }

    public Integer getToolsCountInactive() {
        return toolsCountInactive;
    }

    public void setToolsCountInactive(Integer toolsCountInactive) {
        this.toolsCountInactive = toolsCountInactive;
    }
}
