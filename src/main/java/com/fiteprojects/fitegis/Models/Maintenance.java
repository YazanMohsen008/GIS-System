package com.fiteprojects.fitegis.Models;



import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "maintenance",schema = "fite_geometry",catalog = "postgres")
public class Maintenance extends GenericModel {


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "request_date")
    Timestamp requestDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "repair_date")
    Timestamp repairDate;

    @Column(name = "tool_id", insertable = false, updatable = false)
    Integer toolId;

    @ManyToOne(targetEntity = Tool.class)
    Tool tool;

    @Column(name = "requester_name")
    String requesterName;

    @Column(name = "repairer_name")
    String repairerName;


    @Column(name = "description")
    String description;

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public Timestamp getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Timestamp repairDate) {
        this.repairDate = repairDate;
    }

    public Integer getToolId() {
        return toolId;
    }

    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRepairerName() {
        return repairerName;
    }

    public void setRepairerName(String repairerName) {
        this.repairerName = repairerName;
    }
}
