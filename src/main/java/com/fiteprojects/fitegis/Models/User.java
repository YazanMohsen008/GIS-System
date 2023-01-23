package com.fiteprojects.fitegis.Models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users", schema = "fite_geometry", catalog = "postgres")
public class User extends GenericModel implements UserDetails {

    @Column(name = "name")
    String name;
    @Column(name = "password")
    String password;

    @Transient
    String Token;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", schema = "fite_geometry", catalog = "postgres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;

    public User() {
    }

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        roles = new ArrayList<>();
        roles.add(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        for (Role role : roles)
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getEn_name()));

        return simpleGrantedAuthorities;
    }


    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
