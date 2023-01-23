package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.OtherTool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OtherToolRepository")
public interface OtherToolRepository extends GenericRepository<OtherTool>{
    @Query("select ot from OtherTool ot where ot.tool.floor =:floorNumber")
    List<OtherTool> findAllWithFilter(@Param("floorNumber") Integer floorNumber);

}
