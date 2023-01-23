package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Models.Log;
import com.fiteprojects.fitegis.Models.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LogRepository")
public interface LogRepository extends GenericRepository<Log>{
    @Query("select l from Log l where" +
            "(:action is null or l.action = :action) and " +
            "(:category is null or l.category = :category)")
    List<Log> getAllWithFilter(String action, String category);
}
