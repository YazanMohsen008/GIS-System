package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Maintenance;
import com.fiteprojects.fitegis.Models.Tool;
import com.fiteprojects.fitegis.Repositories.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fiteprojects.fitegis.Utils.Enums.ToolTypes.ACTIVE_STATUS;
import static com.fiteprojects.fitegis.Utils.Enums.ToolTypes.INACTIVE_STATUS;

@Service("MaintenanceService")
public class MaintenanceService extends GenericService<MaintenanceRepository, Maintenance> {

    @Autowired
    ToolService toolService;
    @Autowired
    UserService userService;

    @Override
    public Maintenance add(Maintenance maintenance) throws Exception {
        maintenance.setRequestDate(new Timestamp(new Date().getTime()));
        Tool tool = toolService.getById(maintenance.getToolId());
        tool.setStatus(INACTIVE_STATUS.getKey());
        toolService.update(tool);
        maintenance.setTool(tool);
        maintenance.setRequesterName(userService.getAuthenticatedUser().getName());
        return super.add(maintenance);
    }


    public Maintenance fix(Integer maintenanceId) throws Exception {
        Maintenance maintenance = repository.findById(maintenanceId).get();
        Tool tool = maintenance.getTool();
        tool.setStatus(ACTIVE_STATUS.getKey());
        toolService.update(tool);
        maintenance.setRepairDate(new Timestamp(new Date().getTime()));
        maintenance.setRepairerName(userService.getAuthenticatedUser().getName());
        return repository.save(maintenance);
    }

    public List<Maintenance> getAllWithFilter(Integer floorNumber, String status, String description, Integer tool_id, Integer toolTypeId) {
        List<Maintenance> models;
        List<Maintenance> response = new ArrayList<>();
        try {
            models = repository.findAllWithFilter(floorNumber, description, tool_id, toolTypeId);
            if (status == null)
                response = models;
            else {
                for (Maintenance model : models) {
                    if (status.equals(ACTIVE_STATUS.getKey()) && model.getRepairerName() != null) {
                        response.add(model);
                    }
                    if (status.equals(INACTIVE_STATUS.getKey()) && model.getRepairerName() == null) {
                        response.add(model);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return response;
    }

    public Integer getAllInactive() {
        List<Maintenance> models = new ArrayList<>();
        Integer inActiveDevices = 0;
        try {
            repository.findAll().forEach(models::add);
            for (Maintenance maintenance : models) {
                if (maintenance.getTool().getStatus().equals(INACTIVE_STATUS.getKey())&&maintenance.getRepairerName() == null) {
                    inActiveDevices++;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return inActiveDevices;
    }
}
