package com.fiteprojects.fitegis.Controllers;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.GIS.GISModel;
import com.fiteprojects.fitegis.Repositories.CameraRepository;
import com.fiteprojects.fitegis.Services.CameraService;
import com.fiteprojects.fitegis.Services.GISService;
import com.fiteprojects.fitegis.Utils.Enums.Response;
import com.fiteprojects.fitegis.Utils.Models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cameras")
public class CameraController extends GenericController<CameraService, CameraRepository, Camera> {
    @Autowired
    GISService gisService;

    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject add(HttpServletResponse response, @RequestBody Camera camera) {
        try {
            Camera returnedModel = service.add(camera);
            GISModel model = new GISModel(1);
            model.addFeature(returnedModel, "cameras", "Point", Camera.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject getAll(HttpServletResponse response, HttpServletRequest request, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            List<Camera> returnedModels = service.getAll();
            GISModel model = new GISModel(returnedModels.size());
            model.addFeature(returnedModels, "cameras", "Point", Camera.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }


    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject getById(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "id") Integer id) {
        try {
            Camera returnedModel = service.getById(id);
            GISModel model = new GISModel(1);
            model.addFeature(returnedModel, "cameras", "Point", Camera.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }

    @GetMapping("/get-all-filter")
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<GISModel> all(HttpServletResponse response, HttpServletRequest request, @RequestParam(value = "floorNumber", required = false) Integer floorNumber) {
        try {
            List<Camera> returnedModels = service.getAllWithFilter(floorNumber);
            GISModel model = new GISModel(returnedModels.size());
            model.addFeature(returnedModels, "cameras", "Point", Camera.class);
            return new ResponseObject<>(Response.Success.getMessage(), model);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }


    @GetMapping("/get-statics")
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Map<String, Integer>> getStatics(HttpServletResponse response, HttpServletRequest request) {
        try {
            Map<String, Integer> returnedModels = service.getStatics();
            return new ResponseObject<>(Response.Success.getMessage(), returnedModels);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }

    }


    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject update(HttpServletResponse response, @RequestBody Camera camera) {
        try {
            Camera returnedModel = service.update(camera);
            GISModel gisModel = new GISModel(1);
            gisModel.addFeature(returnedModel, "cameras", "Point", Camera.class);
            return new ResponseObject<>(Response.Success.getMessage(), gisModel);
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject<>(Response.failed.getMessage(), exception.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject<Camera> delete(HttpServletResponse response, @RequestParam(name = "id") Integer id) {
        return super.delete(response, id);
    }

    @Autowired
    Environment environment;

    @GetMapping("/start-streaming")
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject startStreaming(HttpServletResponse response) {
        System.out.println("Start Streaming .. .");
//        String command = "C:\\Users\\DELL\\ffmpeg-2022-02-10-git-b6bb6b9f22-full_build\\bin\\ffmpeg -fflags nobuffer -rtsp_transport tcp -i \"rtsp://demo:demo@ipvmdemo.dyndns.org:5541/onvif-media/media.amp?profile=profile_1_h264&sessiontimeout=60&streamtype=unicast\" -vsync 0 -copyts -vcodec copy -movflags frag_keyframe+empty_moov -an -hls_flags delete_segments+append_list -f segment -segment_list_flags live -segment_time 1 -segment_list_size 3 -segment_format mpegts -segment_list \"E:\\College\\Graduaion Project\\applications\\Backend-Code\\target\\classes\\static\\videos\\ipcam\\index.m3u8\" -segment_list_type m3u8 \"E:\\College\\Graduaion Project\\applications\\Backend-Code\\target\\classes\\static\\videos\\ipcam\\%d.ts\"";
        String command = "ffmpeg -fflags nobuffer -rtsp_transport tcp -i rtsp://demo:demo@ipvmdemo.dyndns.org:5541/onvif-media/media.amp?profile=profile_1_h264&sessiontimeout=60&streamtype=unicast -vsync 0 -copyts -vcodec copy -movflags frag_keyframe+empty_moov -an -hls_flags delete_segments+append_list -f segment -segment_list_flags live -segment_time 1 -segment_list_size 3 -segment_format mpegts -segment_list /app/index.m3u8 -segment_list_type m3u8 /app/%d.ts";
//            String command = "ffmpeg -fflags nobuffer -rtsp_transport tcp -i rtsp://rtsp.stream/pattern -vsync 0 -copyts -vcodec copy -movflags frag_keyframe+empty_moov -an -hls_flags delete_segments+append_list -f segment -segment_list_flags live -segment_time 1 -segment_list_size 3 -segment_format mpegts -segment_list /app/index.m3u8 -segment_list_type m3u8 /app/%d.ts";
        try {
            Process process = Runtime.getRuntime().exec(command);
            System.out.println(process.isAlive());

//            BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
//            while ((line = reader1.readLine()) != null) {
//                System.out.println(line);
//            }
            while ((line = reader2.readLine()) != null) {
                System.out.println(line);
            }

            process.getInputStream().close();
            process.getErrorStream().close();
            process.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject(Response.failed.getMessage(), e.getMessage());
        }
        String camerasServePath = environment.getProperty("camerasServePath");
        return new ResponseObject(Response.Success.getMessage(), camerasServePath);

    }

    @GetMapping("/stop-streaming")
    @PreAuthorize("hasRole('system_manager') or hasRole('tools_manager')")
    ResponseObject stopStreaming(HttpServletResponse response) {
        String killcommand = "pkill ffmpeg";
        try {
            Process process = Runtime.getRuntime().exec(killcommand);
            return new ResponseObject(Response.Success.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject(Response.failed.getMessage(), e.getMessage());
        }
    }


    @GetMapping("/upload-file")
    ResponseObject uploadFile(HttpServletResponse response, @RequestPart MultipartFile file) {
        try {
            String name = file.getOriginalFilename();
            System.out.println(name);
            Path dest = Paths.get("/app/" + name);
            System.out.println(dest);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);
            return new ResponseObject(Response.Success.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new ResponseObject(Response.failed.getMessage(), e.getMessage());
        }
    }
}
