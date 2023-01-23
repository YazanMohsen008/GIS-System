package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.GIS.GISModel;
import com.fiteprojects.fitegis.Models.Maintenance;
import com.fiteprojects.fitegis.Repositories.MaintenanceRepository;
import com.fiteprojects.fitegis.Services.MaintenanceService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/maintenances")
public class MaintenanceController extends GenericController<MaintenanceService, MaintenanceRepository, Maintenance> {
    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    public ResponseObject<Maintenance> add(HttpServletResponse response, @RequestBody Maintenance model) {
        return super.add(response, model);
    }

    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Maintenance> getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return super.getAll(response, request, page, pageSize);
    }

    @GetMapping("/get-all-filter")
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Maintenance> getAllWithFilter(HttpServletResponse response, HttpServletRequest request
            , @RequestParam(value = "floorNumber", required = false) Integer floorNumber
            , @RequestParam(value = "status", required = false) String status
            , @RequestParam(value = "search", required = false) String description
            , @RequestParam(value = "tool_id", required = false) Integer tool_id,
              @RequestParam(value = "toolTypeId", required = false) Integer toolTypeId) {
        try {
            List<Maintenance> returnedModels = service.getAllWithFilter(floorNumber,status,description,tool_id,toolTypeId);

            return new ResponseObject<>(Response.Success.getMessage(), returnedModels);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }
    @GetMapping("/get-all-inactive")
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Integer> getAllInactive(HttpServletResponse response, HttpServletRequest request) {
        try {
            Integer  inActiveDevices = service.getAllInactive();
            return new ResponseObject<>(Response.Success.getMessage(), inActiveDevices);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }
    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Maintenance> getById(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "id") Integer id) {
        return super.getById(response, request, id);
    }

    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    public ResponseObject<Maintenance> update(HttpServletResponse response, @RequestBody Maintenance model) {
        return super.update(response, model);
    }

    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    public  ResponseObject<Maintenance> delete(HttpServletResponse response, @RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }

    @GetMapping(value = "/fix")
            @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Maintenance> fix(HttpServletResponse response, @RequestParam(name = "maintenanceId") Integer maintenanceId) {
        try {
            Maintenance returnedModel = service.fix(maintenanceId);
            return new ResponseObject<>(Response.Success.getMessage(), returnedModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

}
