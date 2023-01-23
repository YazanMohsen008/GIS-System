package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Course;
import com.fiteprojects.fitegis.Models.Lecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CourseRepository")
public interface CourseRepository extends GenericRepository<Course> {
    @Query("select c from Course c where" +
            "(:year is null or c.year = :year) and " +
            "(:specialization is null or c.specialization = :specialization) ")
    List<Course> getCourseFilter( Integer year, Integer specialization);

}

