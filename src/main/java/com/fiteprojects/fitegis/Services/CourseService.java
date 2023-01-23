package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Course;
import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Repositories.CourseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("CourseService")
public class CourseService extends GenericService<CourseRepository, Course> {
    @Override
    public List<Course> getAll(String lang, Integer page, Integer pageSize) throws Exception {
        if (page == null || pageSize == null) {
            System.out.println("Page or Page size is null ");
            page = 0;
            pageSize = 1000;
        }
        List<Course> courses = new ArrayList<>();
        try {
            repository.findAll(PageRequest.of(page, pageSize)).forEach(courses::add);
            courses.forEach(course -> {
                course.setNameByLang(lang);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
            return courses;

    }

    @Override
    public Course getById(Integer id, String lang) throws Exception {
        Course course;
        try {
            course = repository.findById(id).get();
            course.setNameByLang(lang);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return course;
    }

    public List<Course> getCourseFilter(String lang, Integer year, Integer specialization, Integer count) throws Exception {
        List<Course> courses = repository.getCourseFilter(year, specialization);
        List<Course> result = new ArrayList<>();
        if (count == null)
            count = 10;
        if (count > courses.size())
            count = courses.size();
        for (int i = 0; i < count; i++) {
            Course course = courses.get(i);
            course.setNameByLang(lang);
            result.add(course);
        }
        return result;
    }




}
