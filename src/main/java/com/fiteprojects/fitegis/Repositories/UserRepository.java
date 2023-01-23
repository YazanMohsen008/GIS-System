package com.fiteprojects.fitegis.Repositories;

import com.fiteprojects.fitegis.Models.User;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends GenericRepository<User>{
    User findByName(String username);
}
