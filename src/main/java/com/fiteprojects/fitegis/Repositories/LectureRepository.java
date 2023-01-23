package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Lecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LectureRepository")
public interface LectureRepository extends GenericRepository<Lecture> {
    @Query("select l from Lecture l where l.location.id=:LocationID")
    List<Lecture> findAllByLocationId(Integer LocationID);

    @Query("select l from Lecture l where l.location.id=:LocationID order by l.actualStartTime")
    List<Lecture> findAllByLocationIdSortByActualStartTime(Integer LocationID);


    @Query("select L.id,L.course.ar_name,L.actualStartTime,L.actualEndTime,L.classNumber " +
            ",L.groupNumber,L.sectionNumber,L.others from Lecture L where L.course.ar_name like %:word%")
    List<Object[]> getAllLikeWordArabic(@Param("word") String word);

    @Query("select L.id,L.course.en_name,L.actualStartTime,L.actualEndTime,L.classNumber " +
            ",L.groupNumber,L.sectionNumber,L.others from Lecture L where L.course.en_name like %:word%")
    List<Object[]> getAllLikeWordEnglish(@Param("word") String word);

    @Query("select L.id,L.course.ar_name,L.course.en_name,L.actualStartTime,L.actualEndTime,L.classNumber " +
            ",L.groupNumber,L.sectionNumber,L.others from Lecture L where L.course.ar_name like %:word%")
    List<Object[]> getAllLikeWord(@Param("word") String word);

    @Query("select L from Lecture L where LOWER(L.course.ar_name) like %:word% or LOWER(L.course.en_name) like %:word%")
    List<Lecture> getAllLectureLikeWord(@Param("word") String word);

    @Query("select l from Lecture l where" +
            "(:hallId is null or l.actualLocationId = :hallId) and " +
            "(:year is null or l.course.year = :year) and " +
            "(:specialization is null or l.course.specialization = :specialization) and " +
            "(:day is null or l.day = :day) and " +
            "(:courseId is null or l.course.id = :courseId) and" +
            "(:classNumber is null or l.classNumber = :classNumber)and " +
            "(:groupNumber is null or l.groupNumber = :groupNumber) and" +
            "(:sectionNumber is null or l.sectionNumber = :sectionNumber)")
    List<Lecture> getLectureFilter(Integer hallId, Integer year, Integer specialization, Integer day, Integer courseId, Integer classNumber, Integer groupNumber, Integer sectionNumber);
}
