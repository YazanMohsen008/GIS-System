package com.fiteprojects.fitegis.Models;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fiteprojects.fitegis.Models.GIS.GISCoordinates;
import com.fiteprojects.fitegis.Models.GIS.PointToJsonSerializer;
import com.vividsolutions.jts.geom.Geometry;

import javax.persistence.*;
import java.util.ArrayList;


@Entity
@Table(name = "other_tools",schema = "fite_geometry",catalog = "postgres")
public class OtherTool extends GenericModel  implements GISCoordinates {



    @JsonSerialize(using = PointToJsonSerializer.class)
    @Column(name = "geom", columnDefinition = "Geometry")
    Geometry geom;
    @ManyToOne(targetEntity = Tool.class)
    Tool tool;
    @Column(name = "tool_id", insertable = false, updatable = false)
    Integer toolId;
    @Transient
    ArrayList<Double> coordinates;
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
}
