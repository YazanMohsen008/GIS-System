package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.DTO.UserDTO;
import com.fiteprojects.fitegis.Models.User;
import com.fiteprojects.fitegis.Repositories.UserRepository;
import com.fiteprojects.fitegis.Services.UserService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<UserService, UserRepository, User> {


    @PreAuthorize("hasRole('system_manager')")
    @Override
    ResponseObject add(HttpServletResponse response, @RequestBody User model) {
        try {
            User returnedModel = service.add(model);
            UserDTO userDTO = new UserDTO(returnedModel);
            return new ResponseObject<>(Response.Success.getMessage(), userDTO);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @PreAuthorize("hasRole('system_manager')")
    @Override
    ResponseObject getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            List<User> returnedModels = service.getAll(request.getHeader("lang"), page, pageSize);
            List<UserDTO> usersDTO = new ArrayList<>();
            returnedModels.forEach(user -> {
                usersDTO.add(new UserDTO(user));
            });
            return new ResponseObject<>(Response.Success.getMessage(), usersDTO);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @PreAuthorize("hasRole('system_manager')")
    @Override
    ResponseObject getById(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "id") Integer id) {
        try {
            User returnedModel = service.getById(id, request.getHeader("lang"));
            UserDTO userDTO = new UserDTO(returnedModel);
            return new ResponseObject<>(Response.Success.getMessage(), userDTO);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @PreAuthorize("hasRole('system_manager')")
    @Override
    ResponseObject update(HttpServletResponse response, @RequestBody User model) {
        try {
            User returnedModel = service.update(model);
            UserDTO userDTO = new UserDTO(returnedModel);
            return new ResponseObject<>(Response.Success.getMessage(), userDTO);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @PreAuthorize("hasRole('system_manager')")
    @Override
    ResponseObject<User> delete(HttpServletResponse response, @RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }

    @PostMapping("/login")
    ResponseObject<UserDTO> login(HttpServletResponse response, @RequestParam(name = "applicationName") String applicationName, @RequestBody User user) {
        try {
            String token = service.login(user, applicationName);
            User authenticatedUser = service.getAuthenticatedUser();
            authenticatedUser.setToken(token);
            UserDTO userDTO = new UserDTO(authenticatedUser);
            return new ResponseObject<>(Response.Success.getMessage(), userDTO);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @GetMapping("/getCurrentUser")
    ResponseObject<UserDTO> getCurrentUser(HttpServletResponse response) {
        try {
            User authenticatedUser = service.getAuthenticatedUser();
            UserDTO userDTO = new UserDTO(authenticatedUser);
            return new ResponseObject<>(Response.Success.getMessage(), userDTO);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }
}
