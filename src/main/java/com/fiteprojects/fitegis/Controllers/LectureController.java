package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Repositories.LectureRepository;
import com.fiteprojects.fitegis.Services.LectureService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/lectures")
public class LectureController extends GenericController<LectureService, LectureRepository, Lecture> {
    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('shift_manager')")
    public ResponseObject<Lecture> add(HttpServletResponse response, @RequestBody Lecture model) {
        return super.add(response, model);
    }

    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('shift_manager')")
    public ResponseObject<Lecture> update(HttpServletResponse response, @RequestBody Lecture model) {
        return super.update(response, model);
    }

    @Override
            @PreAuthorize("hasRole('system_manager') or hasRole('shift_manager')")
    public  ResponseObject<Lecture> delete(HttpServletResponse response, @RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }

    @GetMapping("/filter")
    ResponseObject<Lecture> getLectureFilter(HttpServletResponse response, HttpServletRequest request,
                                             @RequestParam(name = "hallId", required = false) Integer hallId,
                                             @RequestParam(name = "year", required = false) Integer year,
                                             @RequestParam(name = "specialization", required = false) Integer specialization,
                                             @RequestParam(name = "day", required = false) Integer day,
                                             @RequestParam(name = "courseId", required = false) Integer courseId,
                                             @RequestParam(name = "classNumber", required = false) Integer classNumber,
                                             @RequestParam(name = "groupNumber", required = false) Integer groupNumber,
                                             @RequestParam(name = "sectionNumber", required = false) Integer sectionNumber,
                                             @RequestParam(name = "count", required = false) Integer count
    ) {
        try {
            List<Lecture> lectures = service.getLectureFilter(request.getHeader("lang"), hallId, year, specialization, day, courseId, classNumber, groupNumber, sectionNumber, count);
            return new ResponseObject<>(Response.Success.getMessage(), lectures);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }
}
