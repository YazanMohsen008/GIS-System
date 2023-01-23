package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.GenericModel;
import com.fiteprojects.fitegis.Repositories.GenericRepository;
import com.fiteprojects.fitegis.Services.GenericService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GenericController<
        Service extends GenericService<Repository, Model>,
        Repository extends GenericRepository<Model>,
        Model extends GenericModel> {

    @Autowired
    Service service;

    @PostMapping("/add")
    ResponseObject<Model> add(HttpServletResponse response, @RequestBody Model model) {
        try {
            Model returnedModel = service.add(model);
            return new ResponseObject<>(Response.Success.getMessage(), returnedModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @GetMapping("/get-all")
    ResponseObject<Model> getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            List<Model> returnedModels = service.getAll(request.getHeader("lang"),page,pageSize);
            return new ResponseObject<>(Response.Success.getMessage(), returnedModels);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @GetMapping(value = "/get-by-id")
    ResponseObject<Model> getById(HttpServletResponse response, HttpServletRequest request,@RequestParam(name = "id") Integer id) {
        try {
            Model returnedModel = service.getById(id,request.getHeader("lang"));
            return new ResponseObject<>(Response.Success.getMessage(), returnedModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @PostMapping("/update")
    ResponseObject<Model> update(HttpServletResponse response,@RequestBody Model model) {
        try {
            Model returnedModel = service.update(model);
            return new ResponseObject<>(Response.Success.getMessage(), returnedModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    ResponseObject<Model> delete(HttpServletResponse response,@RequestParam(name = "id") Integer id) {
        try {
            service.delete(id);
            return new ResponseObject<>(Response.Success.getMessage());
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

}
