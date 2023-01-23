package com.fiteprojects.fitegis.Models.DTO;


import com.fiteprojects.fitegis.Models.Role;
import com.fiteprojects.fitegis.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    Integer id;
    String name;
    String token;
    List<Role> roles = new ArrayList<>();

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        token = user.getToken();
        roles = user.getRoles();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
