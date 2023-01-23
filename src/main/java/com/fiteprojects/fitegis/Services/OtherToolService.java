package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.OtherTool;
import com.fiteprojects.fitegis.Models.Tool;
import com.fiteprojects.fitegis.Repositories.OtherToolRepository;
import com.fiteprojects.fitegis.Utils.Services.GeometryService;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("OtherToolService")
public class OtherToolService extends GenericService<OtherToolRepository, OtherTool> {
    @Autowired
    ToolService toolService;
    @Autowired
    GeometryService geometryService;


    @Override
    public OtherTool add(OtherTool otherTool) throws Exception {
        Geometry geometry = geometryService.deserializePoint(otherTool.getCoordinates());
        otherTool.setGeom(geometry);
        Tool tool = toolService.add(otherTool.getTool());
        otherTool.setTool(tool);
        return super.add(otherTool);
    }

    @Override
    public OtherTool update(OtherTool otherTool) throws Exception {
        if (otherTool.getTool() != null) {
            Tool tool = otherTool.getTool();
            tool.setId(repository.findById(otherTool.getId()).get().getTool().getId());
            tool = toolService.update(tool);
            otherTool.setTool(tool);
        }
        return super.update(otherTool);
    }


    @Override
    public void delete(Integer id) throws Exception {
        OtherTool otherTool = repository.findById(id).get();
        toolService.deleteImage(otherTool.getTool());
        super.delete(id);
    }
    public List<OtherTool> getAllWithFilter(Integer floorNumber) {
        List<OtherTool> models;
        try {
            models = new ArrayList<>(repository.findAllWithFilter(floorNumber));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return models;
    }

}
