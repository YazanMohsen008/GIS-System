package com.fiteprojects.fitegis.Models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fiteprojects.fitegis.Models.GIS.GISCoordinates;
import com.fiteprojects.fitegis.Models.GIS.PointToJsonSerializer;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "cameras", schema = "fite_geometry", catalog = "postgres")
public class Camera extends GenericModel implements GISCoordinates {

    @Column(name = "ip_address")
    String ipAddress;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;

    @JsonSerialize(using = PointToJsonSerializer.class)
    @Column(name = "geom", columnDefinition = "Geometry")
    Geometry geom;
    @ManyToOne(targetEntity = Tool.class)
    Tool tool;
    @Column(name = "tool_id", insertable = false, updatable = false)
    Integer toolId;
    @Transient
    ArrayList<Double> coordinates;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Geometry getGeom() {
        return geom;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
