package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.GIS.GISModel;
import com.fiteprojects.fitegis.Models.OtherTool;
import com.fiteprojects.fitegis.Repositories.OtherToolRepository;
import com.fiteprojects.fitegis.Services.OtherToolService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/other_tools")
public class OtherToolController extends GenericController<OtherToolService, OtherToolRepository, OtherTool> {

    @Override
        //    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject add(HttpServletResponse response, @RequestBody OtherTool otherTool) {
        try {
            OtherTool returnedModel = service.add(otherTool);
            GISModel model = new GISModel(1);
            model.addFeature(returnedModel, "others", "Point", OtherTool.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @Override
    ResponseObject getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            List<OtherTool> returnedModels = service.getAll();
            GISModel model = new GISModel(returnedModels.size());
            model.addFeature(returnedModels, "others", "Point", OtherTool.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }


    @Override
    ResponseObject getById(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "id") Integer id) {
        try {
            OtherTool returnedModel = service.getById(id);
            GISModel model = new GISModel(1);
            model.addFeature(returnedModel, "others", "Point", OtherTool.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }
    @GetMapping("/get-all-filter")
    ResponseObject<GISModel> all(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "floorNumber", required = false) Integer floorNumber) {
        try {
            List<OtherTool> returnedModels = service.getAllWithFilter(floorNumber);
            GISModel model = new GISModel(returnedModels.size());
            model.addFeature(returnedModels, "others", "Point", OtherTool.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }


    @Override
        //    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject update(HttpServletResponse response, @RequestBody OtherTool otherTool) {
        try {
            OtherTool returnedModel = service.update(otherTool);
            GISModel gisModel = new GISModel(1);
            gisModel.addFeature(returnedModel, "others", "Point", OtherTool.class);
            return new ResponseObject<>(Response.Success.getMessage(), gisModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @Override
        //    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<OtherTool> delete(HttpServletResponse response, @RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }
}
