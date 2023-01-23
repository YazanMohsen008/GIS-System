package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CameraRepository")
public interface CameraRepository extends GenericRepository<Camera>{
    @Query("select c from Camera c where (:floorNumber is null or c.tool.floor  = :floorNumber)")
    List<Camera> findAllWithFilter(@Param("floorNumber") Integer floorNumber);

}
