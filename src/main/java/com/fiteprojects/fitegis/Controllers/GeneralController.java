package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Models.Location;
import com.fiteprojects.fitegis.Models.DTO.LectureDTO;
import com.fiteprojects.fitegis.Models.DTO.LocationDTO;
import com.fiteprojects.fitegis.Models.Log;
import com.fiteprojects.fitegis.Repositories.LocationRepository;
import com.fiteprojects.fitegis.Services.LectureService;
import com.fiteprojects.fitegis.Services.LocationService;
import com.fiteprojects.fitegis.Services.LogService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/general")
public class GeneralController  {

    @Autowired
    LocationService locationService;
    @Autowired
    LectureService lectureService;

    @GetMapping("/search")
    ResponseObject search(HttpServletResponse response, @RequestParam(name = "word") String word) {
        try {
            List<LocationDTO> locations = locationService.getAllLikeWord(word);
//            List<LectureDTO> lectures = lectureService.getAllLikeWord(word);
            List<Lecture> lectures = lectureService.getAllLectureLikeWord(word);
            Map<String, Object> locationsAndLectures = new HashMap<>();
            locationsAndLectures.put("locations", locations);
            locationsAndLectures.put("lectures", lectures);
            return new ResponseObject<>(Response.Success.getMessage(), locationsAndLectures);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }
    @Autowired
    LogService logService;

//    @PreAuthorize("hasRole('system_manager') ")
    @GetMapping("/logs")
    ResponseObject<Log> getAllWithFilter(HttpServletResponse response, HttpServletRequest request, @RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize, @RequestParam("action")String action, @RequestParam("category")String category) {
        try {
            List<Log> logs= logService.getAllWithFilter(action,category,page, pageSize);
            return new ResponseObject<>(Response.Success.getMessage(), logs);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

}
