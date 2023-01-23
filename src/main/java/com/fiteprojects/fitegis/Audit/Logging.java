package com.fiteprojects.fitegis.Audit;

import com.fiteprojects.fitegis.Models.Log;
import com.fiteprojects.fitegis.Services.LogService;
import com.fiteprojects.fitegis.Services.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Aspect
@Component
public class Logging {
    @Autowired
    LogService logService;
    @Autowired
    UserService userService;

    @After(value = "" +
            "execution(* com.fiteprojects.fitegis.Services.ToolService.add(..))||" +
            "execution(* com.fiteprojects.fitegis.Services.ToolService.update(..))||" +
            "execution(* com.fiteprojects.fitegis.Services.ToolService.delete(..))"
    )
    public void afterToolAdvice(JoinPoint joinPoint) throws Exception {
        System.out.println("after :" + joinPoint.getSignature());
        Log log = new Log();
        log.setAction(joinPoint.getSignature().getName());
        log.setCategory("Tools");
        log.setCreatedAt(new Timestamp(new Date().getTime()));
        if (userService.getAuthenticatedUser() != null)
            log.setCreatedBy(userService.getAuthenticatedUser().getUsername());
        logService.add(log);
    }

    @After(value =
            "execution(* com.fiteprojects.fitegis.Controllers.MaintenanceController.add(..))" +
            "execution(* com.fiteprojects.fitegis.Controllers.MaintenanceController.fix(..))"
    )
    public void afterMaintenanceAdvice(JoinPoint joinPoint) throws Exception {
        System.out.println("after :" + joinPoint.getSignature());
        Log log = new Log();
        log.setAction(joinPoint.getSignature().getName());
        log.setCategory("Maintenance");
        log.setCreatedAt(new Timestamp(new Date().getTime()));
        if (userService.getAuthenticatedUser() != null)
            log.setCreatedBy(userService.getAuthenticatedUser().getUsername());
        logService.add(log);
    }

    @After(value =
            "execution(* com.fiteprojects.fitegis.Controllers.LocationController.add(..))||" +
            "execution(* com.fiteprojects.fitegis.Controllers.LocationController.update(..))||" +
            "execution(* com.fiteprojects.fitegis.Controllers.LocationController.delete(..))")
    public void afterLocationAdvice(JoinPoint joinPoint) throws Exception {
        System.out.println("after :" + joinPoint.getSignature());
        Log log = new Log();
        log.setAction(joinPoint.getSignature().getName());
        log.setCategory("Locations");
        log.setCreatedAt(new Timestamp(new Date().getTime()));
        if (userService.getAuthenticatedUser() != null)
            log.setCreatedBy(userService.getAuthenticatedUser().getUsername());
        logService.add(log);
    }

    @After(value =
            "execution(* com.fiteprojects.fitegis.Controllers.LectureController.add(..))||" +
            "execution(* com.fiteprojects.fitegis.Controllers.LectureController.update(..))||" +
            "execution(* com.fiteprojects.fitegis.Controllers.LectureController.delete(..))")
    public void afterLectureAdvice(JoinPoint joinPoint) throws Exception {
        System.out.println("after :" + joinPoint.getSignature());
        Log log = new Log();
        log.setAction(joinPoint.getSignature().getName());
        log.setCategory("Lecture");
        log.setCreatedAt(new Timestamp(new Date().getTime()));
        if (userService.getAuthenticatedUser() != null)
            log.setCreatedBy(userService.getAuthenticatedUser().getUsername());
        logService.add(log);
    }

    @After(value =
            "execution(* com.fiteprojects.fitegis.Controllers.CourseController.add(..))||" +
                    "execution(* com.fiteprojects.fitegis.Controllers.CourseController.update(..))||" +
                    "execution(* com.fiteprojects.fitegis.Controllers.CourseController.delete(..))")
    public void afterCourseAdvice(JoinPoint joinPoint) throws Exception {
        System.out.println("after :" + joinPoint.getSignature());
        Log log = new Log();
        log.setAction(joinPoint.getSignature().getName());
        log.setCategory("Course");
        log.setCreatedAt(new Timestamp(new Date().getTime()));
        if (userService.getAuthenticatedUser() != null)
            log.setCreatedBy(userService.getAuthenticatedUser().getUsername());
        logService.add(log);
    }
}

