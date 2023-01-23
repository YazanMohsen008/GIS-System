package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.ToolType;
import com.fiteprojects.fitegis.Repositories.ToolTypeRepository;
import com.fiteprojects.fitegis.Services.ToolTypeService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/toolTypes")
public class ToolTypeController extends GenericController<ToolTypeService, ToolTypeRepository, ToolType> {
    @Override
         @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<ToolType> add(HttpServletResponse response, @RequestBody ToolType model) {
        return super.add(response, model);
    }

    @Override
        @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<ToolType> update(HttpServletResponse response,@RequestBody ToolType model) {
        return super.update(response, model);
    }

    @Override
        @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<ToolType> delete(HttpServletResponse response, @RequestParam(name = "id")  Integer id) {
        return super.delete(response, id);
    }
}
