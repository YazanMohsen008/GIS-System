package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.Location;
import com.fiteprojects.fitegis.Models.Tool;
import com.fiteprojects.fitegis.Repositories.CameraRepository;
import com.fiteprojects.fitegis.Repositories.ToolRepository;
import com.fiteprojects.fitegis.Utils.Services.GeometryService;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fiteprojects.fitegis.Utils.Enums.ToolTypes.ACTIVE_STATUS;
import static com.fiteprojects.fitegis.Utils.Enums.ToolTypes.INACTIVE_STATUS;

@Service("CameraService")
public class CameraService extends GenericService<CameraRepository, Camera> {
    @Autowired
    ToolService toolService;

    @Autowired
    GeometryService geometryService;

    @Override
    public Camera add(Camera camera) throws Exception {
        Geometry geometry = geometryService.deserializePoint(camera.getCoordinates());
        camera.setGeom(geometry);
        Tool tool = toolService.add(camera.getTool());
        tool.setStatus(ACTIVE_STATUS.getKey());
        camera.setTool(tool);
        return super.add(camera);
    }

    @Override
    public Camera update(Camera camera) throws Exception {
        if (camera.getTool() != null) {
            Tool tool = camera.getTool();
            tool.setId(repository.findById(camera.getId()).get().getTool().getId());
            tool = toolService.update(tool);
            camera.setTool(tool);
        }
        return super.update(camera);
    }

    @Override
    public void delete(Integer id) throws Exception {
        Camera camera = repository.findById(id).get();
        toolService.deleteImage(camera.getTool());
        super.delete(id);
    }

    public List<Camera> getAllWithFilter(Integer floorNumber) {
        List<Camera> models;
        try {
            models = new ArrayList<>(repository.findAllWithFilter(floorNumber));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return models;
    }

    public Map<String, Integer> getStatics() {
        List<Camera> cameras;
        Map<String, Integer> statics = new HashMap<>();
        Integer AllCameras;
        Integer FloorOneCameras = 0;
        Integer FloorTwoCameras = 0;
        Integer FloorThreeCameras = 0;

        Integer AllInActiveCameras = 0;
        Integer FloorOneInActive = 0;
        Integer FloorTwoInActive = 0;
        Integer FloorThreeInActive = 0;
        try {
            cameras = new ArrayList<>((List<Camera>) repository.findAll());
            AllCameras = cameras.size();
            for (Camera camera : cameras) {
                if (camera.getTool().getFloor() == 1) {
                    FloorOneCameras++;
                    if (camera.getTool().getStatus().equals(INACTIVE_STATUS.getKey())) {
                        FloorOneInActive++;
                        AllInActiveCameras++;
                    }
                }
                if (camera.getTool().getFloor() == 2) {
                    FloorTwoCameras++;
                    if (camera.getTool().getStatus().equals(INACTIVE_STATUS.getKey())) {
                        FloorTwoInActive++;
                        AllInActiveCameras++;
                    }
                }
                if (camera.getTool().getFloor() == 3) {
                    FloorThreeCameras++;
                    if (camera.getTool().getStatus().equals(INACTIVE_STATUS.getKey())) {
                        FloorThreeInActive++;
                        AllInActiveCameras++;
                    }
                }
            }

            statics.put("AllCameras", AllCameras);
            statics.put("AllInActiveCameras", AllInActiveCameras);
            statics.put("FloorOneCameras", FloorOneCameras);
            statics.put("FloorTwoCameras", FloorTwoCameras);
            statics.put("FloorOneInActive", FloorOneInActive);
            statics.put("FloorTwoInActive", FloorTwoInActive);
            statics.put("FloorThreeCameras", FloorThreeCameras);
            statics.put("FloorThreeInActive", FloorThreeInActive);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return statics;
    }

}
