package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Course;
import com.fiteprojects.fitegis.Models.DTO.UserDTO;
import com.fiteprojects.fitegis.Models.Role;
import com.fiteprojects.fitegis.Models.User;
import com.fiteprojects.fitegis.Repositories.UserRepository;
import com.fiteprojects.fitegis.Security.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserService extends GenericService<UserRepository, User> implements UserDetailsService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    TokenHandler tokenHandler;
    @Autowired
    RoleService roleService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByName(username);
    }

    @Override
    public User update(User user) throws Exception {
        try {
            if (user.getPassword() != null)
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            List<Role> userRoles = user.getRoles();
            List<Role> roles = new ArrayList<>();
            if (userRoles != null) {
                for (Role role : userRoles) {
                    try {
                        roles.add(roleService.getById(role.getId()));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        throw exception;
                    }
                }
                user.setRoles(roles);
            }
            User old = getById(user.getId());
            User newModel = genericConfig.update(old, user);
            return repository.save(newModel);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public User add(User user) throws Exception {
        List<Role> roles = user.getRoles();
        List<Role> userRoles = new ArrayList<>();
        for (Role role : roles) {
            try {
                userRoles.add(roleService.getById(role.getId()));
            } catch (Exception exception) {
                exception.printStackTrace();
                throw exception;
            }
        }
        user.setRoles(userRoles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return repository.save(user);

    }

    @Override
    public List<User> getAll(String lang, Integer page, Integer pageSize) throws Exception {
        if (page == null || pageSize == null) {
            System.out.println("Page or Page size is null ");
            page = 0;
            pageSize = 1000;
        }
        List<User> users = new ArrayList<>();
        try {
            repository.findAll(PageRequest.of(page, pageSize)).forEach(user -> {
                user.getRoles().forEach(role -> {
                    role.setNameByLang(lang);
                });
                users.add(user);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return users;
    }

    @Override
    public User getById(Integer id, String lang) throws Exception {
        User user;
        try {
            user = repository.findById(id).get();
            user.getRoles().forEach(role -> {
                role.setNameByLang(lang);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return user;
    }

    public String login(User user, String applicationName) throws AuthenticationException {
        if (!checkRole(applicationName, user))
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        User applicationUser = loadUserByUsername(user.getUsername());
        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(applicationUser, null, applicationUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String token = tokenHandler.generateToken(user.getUsername());
        return token;
    }


    public User getAuthenticatedUser() throws AuthenticationException {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loadUserByUsername(authUser.getUsername());
        user.setPassword(null);
        return user;

    }

    boolean checkRole(String roleName, User user) {
        User userObject = repository.findByName(user.getUsername());
        if (userObject == null)
            return false;
        List<Role> roles = userObject.getRoles();
        for (Role role : roles) {
            if (role.getEn_name().equals(roleName)) {
                return true;
            }
        }
        return false;
    }


}
