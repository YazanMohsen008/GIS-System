package com.fiteprojects.fitegis.Utils.Services;
import com.fiteprojects.fitegis.Models.*;
import com.fiteprojects.fitegis.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import static com.fiteprojects.fitegis.Utils.Enums.Roles.*;
import static com.fiteprojects.fitegis.Utils.Enums.ToolTypes.*;


@Component
public class InitService {

    @Autowired
    RoleService roleService;

    @Autowired
    ToolTypeService toolTypeService;

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("initializing service...");
        Role SystemManagerRole = roleService.getByEnglishName(SYSTEM_MANAGER_EN.getKey());
        if (SystemManagerRole == null) {
            SystemManagerRole = new Role(SYSTEM_MANAGER_EN.getKey(), SYSTEM_MANAGER_AR.getKey());
            roleService.add(SystemManagerRole);
        }
        Role ShiftManagerRole = roleService.getByEnglishName(SHIFT_MANAGER_EN.getKey());
        if (ShiftManagerRole == null) {
            ShiftManagerRole = new Role(SHIFT_MANAGER_EN.getKey(), SHIFT_MANAGER_AR.getKey());
            roleService.add(ShiftManagerRole);
        }
        Role ToolsManagerRole = roleService.getByEnglishName(TOOLS_MANAGER_EN.getKey());
        if (ToolsManagerRole == null) {
            ToolsManagerRole = new Role(TOOLS_MANAGER_EN.getKey(), TOOLS_MANAGER_AR.getKey());
            roleService.add(ToolsManagerRole);
        }
        System.out.println("ADDING Users");
        if (userService.loadUserByUsername("yzn") == null)
            userService.add(new User("yzn", "1234", SystemManagerRole));
        if (userService.loadUserByUsername("cezar") == null)
            userService.add(new User("cezar", "1234", ToolsManagerRole));
        if (userService.loadUserByUsername("joseph") == null)
            userService.add(new User("joseph", "1234", ShiftManagerRole));

        if (toolTypeService.getByEnglishName(CAMERAS_EN.getKey()) == null)
            toolTypeService.add(new ToolType(CAMERAS_EN.getKey(), CAMERAS_AR.getKey(), false));
//        if (toolTypeService.getByEnglishName(PROJECTORS_EN.getKey()) == null)
//            toolTypeService.add(new ToolType(PROJECTORS_EN.getKey(), PROJECTORS_AR.getKey(), false));

    }


}
