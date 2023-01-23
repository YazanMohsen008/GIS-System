package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("RoleRepository")
public interface RoleRepository extends GenericRepository<Role>{
    @Query("select R from Role R where R.en_name=:roleName")
    Role findByEnglishName(@Param("roleName")String roleName);
}
