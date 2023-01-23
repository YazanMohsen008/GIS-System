package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Role;
import com.fiteprojects.fitegis.Models.User;
import com.fiteprojects.fitegis.Repositories.RoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RoleService")
public class RoleService extends GenericService<RoleRepository, Role> {
    @Override
    public List<Role> getAll(String lang, Integer page, Integer pageSize) throws Exception {
        List<Role> roles = new ArrayList<>();
        try {

            repository.findAll().forEach(roles::add);
            roles.forEach(role -> {
                role.setNameByLang(lang);
            });
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return roles;
    }
    public Role getByEnglishName(String roleName) throws UsernameNotFoundException {
        return repository.findByEnglishName(roleName);
    }

    @Override
    public Role getById(Integer id, String lang) throws Exception {
        Role role;
        try {
            role = repository.findById(id).get();
            role.setNameByLang(lang);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return role;

    }


}
