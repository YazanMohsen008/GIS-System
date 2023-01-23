package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Role;
import com.fiteprojects.fitegis.Models.ToolType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("ToolTypeRepository")
public interface ToolTypeRepository extends GenericRepository<ToolType>{
    @Query("select TT from ToolType TT where TT.en_name=:toolType")
    ToolType findByEnglishName(@Param("toolType")String toolType);
}
