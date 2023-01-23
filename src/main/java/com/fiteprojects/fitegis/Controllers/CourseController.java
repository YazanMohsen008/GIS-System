package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Course;
import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Repositories.CourseRepository;
import com.fiteprojects.fitegis.Services.CourseService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/courses")
public class CourseController extends GenericController<CourseService, CourseRepository, Course> {
    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('shift_manager')")
    public ResponseObject<Course> add(HttpServletResponse response,@RequestBody Course model) {
        return super.add(response, model);
    }

    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('shift_manager')")
    public ResponseObject<Course> update(HttpServletResponse response, @RequestBody Course model) {
        return super.update(response, model);
    }

    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('shift_manager')")
    public ResponseObject<Course> delete(HttpServletResponse response,@RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }

    @GetMapping("/filter")
    ResponseObject<Course> getCourseFilter(HttpServletResponse response, HttpServletRequest request,
                                             @RequestParam(name = "year", required = false) Integer year,
                                             @RequestParam(name = "specialization", required = false) Integer specialization,
                                             @RequestParam(name = "count", required = false) Integer count
    ) {
        try {
            List<Course> courses = service.getCourseFilter(request.getHeader("lang"), year, specialization, count);
            return new ResponseObject<>(Response.Success.getMessage(), courses);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

}
