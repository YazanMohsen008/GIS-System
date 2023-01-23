package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.GIS.GISModel;
import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Models.Location;
import com.fiteprojects.fitegis.Repositories.LocationRepository;
import com.fiteprojects.fitegis.Services.GISService;
import com.fiteprojects.fitegis.Services.LectureService;
import com.fiteprojects.fitegis.Services.LocationService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/locations")
public class LocationController extends GenericController<LocationService, LocationRepository, Location> {

    @Autowired
    GISService gisService;

    @Autowired
    LectureService lectureService;

    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
   public ResponseObject add(HttpServletResponse response, @RequestBody Location model) {
        try {
            Location returnedModel = service.add(model);
            GISModel gisModel = new GISModel(1);
            gisModel.addFeature(returnedModel, "hall", "Polygon", Location.class);
            return new ResponseObject<>(Response.Success.getMessage(), gisModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @Override
    public ResponseObject getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            List<Location> returnedModels = service.getAll();
            GISModel model = new GISModel(returnedModels.size());
            model.addFeature(returnedModels, "hall", "Polygon", Location.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @GetMapping("/get-all-filter")
    ResponseObject<GISModel> all(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "floorNumber", required = false) Integer floorNumber,@RequestParam(value = "isLecutreHolder", required = false) Boolean isLecutreHolder) {
        try {
            List<Location> returnedModels = service.getAllWithFilter(floorNumber,isLecutreHolder);
            GISModel model = new GISModel(returnedModels.size());
            model.addFeature(returnedModels, "hall", "Polygon", Location.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @Override
    ResponseObject getById(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "id") Integer id) {
        try {
            Location returnedModel = service.getById(id);
            List<Location> locations = new ArrayList<>();
            locations.add(returnedModel);
            GISModel model = new GISModel(1);
            model.addFeature(returnedModel, "hall", "Polygon", Location.class);

            Map<String, Lecture> currentAndNextLecture = lectureService.getCurrentAndNextLecture(id);
            model.addProperty("currentLecture", currentAndNextLecture.get("current"));
            model.addProperty("nextLecture", currentAndNextLecture.get("next"));

            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }


    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    public ResponseObject update(HttpServletResponse response, @RequestBody Location model) {
        try {
            Location returnedModel = service.update(model);
            GISModel gisModel = new GISModel(1);
            gisModel.addFeature(returnedModel, "hall", "Polygon", Location.class);
            return new ResponseObject<>(Response.Success.getMessage(), gisModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @Override
     @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    public ResponseObject<Location> delete(HttpServletResponse response, @RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }

    //GeoServer Request
    @GetMapping("/geoserver/get-all")
    ResponseObject getAll(HttpServletResponse response) {
        try {
            Map<String, String> responseEntity = gisService.getAll();
            return new ResponseObject<>(Response.Success.getMessage(), responseEntity);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @GetMapping("/geoserver/get-all-filter")
    ResponseObject getAllWithFilter(HttpServletResponse response, @RequestParam(value = "floorNumber", required = false) Integer floorNumber) {
        try {
            Map<String, String> responseEntity = gisService.getAllWithFilter(floorNumber);
            return new ResponseObject<>(Response.Success.getMessage(), responseEntity);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @GetMapping(value = "/geoserver/get-by-id")
    ResponseObject getById(HttpServletResponse response, @RequestParam("id") Integer id) {
        try {
            GISModel returnedModel = gisService.getByID(id);
            Map<String, Lecture> currentAndNextLecture = lectureService.getCurrentAndNextLecture(id);
            returnedModel.addProperty("currentLecture", currentAndNextLecture.get("current"));
            returnedModel.addProperty("nextLecture", currentAndNextLecture.get("next"));
            return new ResponseObject<>(Response.Success.getMessage(), returnedModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

}
