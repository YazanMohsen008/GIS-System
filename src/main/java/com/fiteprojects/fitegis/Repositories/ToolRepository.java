package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Tool;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ToolRepository")
public interface ToolRepository extends GenericRepository<Tool>{
    List<Tool> findAllByToolTypeId(Integer toolTypeId);

}
