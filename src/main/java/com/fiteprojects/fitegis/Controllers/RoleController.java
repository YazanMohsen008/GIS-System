package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Role;
import com.fiteprojects.fitegis.Models.User;
import com.fiteprojects.fitegis.Repositories.RoleRepository;
import com.fiteprojects.fitegis.Repositories.UserRepository;
import com.fiteprojects.fitegis.Services.RoleService;
import com.fiteprojects.fitegis.Services.UserService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@PreAuthorize("hasRole('system_manager')")
@RequestMapping("/roles")
public class RoleController extends GenericController<RoleService, RoleRepository, Role> {
    @Override
    ResponseObject<Role> add(HttpServletResponse response,@RequestBody Role model) {
        return super.add(response, model);
    }

    @Override
    ResponseObject<Role> getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        return super.getAll(response, request, page, pageSize);
    }

    @Override
    ResponseObject<Role> getById(HttpServletResponse response, HttpServletRequest request,@RequestParam(name = "id")  Integer id) {
        return super.getById(response, request, id);
    }

    @Override
    ResponseObject<Role> update(HttpServletResponse response,@RequestBody Role model) {
        return super.update(response, model);
    }

    @Override
    ResponseObject<Role> delete(HttpServletResponse response,@RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }
}
