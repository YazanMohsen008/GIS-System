package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LocationRepository")
public interface LocationRepository extends GenericRepository<Location> {
    @Query("select L.id,L.ar_name ,L.type ,L.isLectureHolder ,L.floor from Location L where L.ar_name like %:word%")
    List<Object[]> getAllLikeWordArabic(@Param("word") String word);

    @Query("select L.id,L.en_name ,L.type ,L.isLectureHolder,L.floor  from Location L where L.en_name like %:word%")
    List<Object[]> getAllLikeWordEnglish(@Param("word") String word);

    @Query("select L.id,L.en_name,L.ar_name ,L.type ,L.isLectureHolder,L.floor  from Location L where L.ar_name like %:word% or L.en_name like %:word%")
    List<Object[]> getAllLikeWord(@Param("word") String word);

    @Query("select l from Location l where " +
            "(:floorNumber is null or l.floor = :floorNumber) and " +
            "(:isLectureHolder is null or l.isLectureHolder = :isLectureHolder)")
    List<Location> findAllWithFilter(@Param("floorNumber") Integer floorNumber,@Param("isLectureHolder") Boolean isLectureHolder);
}
