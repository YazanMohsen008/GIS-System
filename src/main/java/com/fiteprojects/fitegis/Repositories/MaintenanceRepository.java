package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.Maintenance;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
@Repository("MaintenanceRepository")
public interface MaintenanceRepository extends GenericRepository<Maintenance> {
    @Query("select M from Maintenance M" +
            " where (:floorNumber is null or M.tool.floor  = :floorNumber)" +
            "and (:description is null or M.description  like %:description% )" +
            "and (:tool_id is null or M.tool.id  = :tool_id)" +
            "and (:toolTypeId is null or M.tool.toolTypeId  = :toolTypeId)")
    List<Maintenance> findAllWithFilter(Integer floorNumber, String description, Integer tool_id,Integer toolTypeId);
}
