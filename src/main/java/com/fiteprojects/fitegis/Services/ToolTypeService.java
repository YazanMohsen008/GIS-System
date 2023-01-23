package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Role;
import com.fiteprojects.fitegis.Models.Tool;
import com.fiteprojects.fitegis.Models.ToolType;
import com.fiteprojects.fitegis.Repositories.ToolRepository;
import com.fiteprojects.fitegis.Repositories.ToolTypeRepository;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.fiteprojects.fitegis.Utils.Enums.ToolTypes.INACTIVE_STATUS;
import static org.omg.PortableServer.POAManagerPackage.State.INACTIVE;

@Service("ToolTypeService")
@PropertySource("classpath:application.properties")
public class ToolTypeService extends GenericService<ToolTypeRepository, ToolType> {
    @Autowired
    Environment environment;
    @Autowired
    ToolService toolService;

    @Override
    public ToolType add(ToolType toolType) throws Exception {
        if (toolType.getIcon() != null)
            saveIcon(toolType);
        toolType = super.add(toolType);
        if (toolType.getUpdatable() == null)
            toolType.setUpdatable(true);
        toolType.setIcon(null);
        return toolType;
    }

    @Override
    public List<ToolType> getAll(String lang, Integer page, Integer pageSize) throws Exception {
        List<ToolType> toolTypes = super.getAll();
        for (ToolType toolType : toolTypes) {
            List<Tool> tools = toolService.getByToolTypeId(toolType.getId());
            toolType.setToolsCount(tools.size());
            int ToolsCountInactive = 0;
            for (Tool tool : tools) {
                if (tool.getStatus().equals(INACTIVE_STATUS.getKey())) {
                    ToolsCountInactive++;
                }
            }
            toolType.setToolsCountInactive(ToolsCountInactive);
        }
        return toolTypes;
    }

    @Override
    public ToolType getById(Integer id, String lang) throws Exception {
        ToolType toolType = super.getById(id, lang);
        List<Tool> tools = toolService.getByToolTypeId(id);
        toolType.setToolsCount(tools.size());
        int ToolsCountInactive = 0;
        for (Tool tool : tools) {
            if (tool.getStatus().equals(INACTIVE_STATUS.getKey())) {
                ToolsCountInactive++;
            }
        }
        toolType.setToolsCountInactive(ToolsCountInactive);
        return toolType;
    }


    public ToolType getByEnglishName(String roleName) throws UsernameNotFoundException {
        return repository.findByEnglishName(roleName);
    }

    @Override
    public ToolType update(ToolType toolType) throws Exception {
        if (toolType.getIcon() != null)
            saveIcon(toolType);
        toolType = super.update(toolType);
        toolType.setIcon(null);
        return toolType;
    }

    private void saveIcon(ToolType toolType) throws IOException {
        Byte[] icon = toolType.getIcon();
        String path = new File(environment.getProperty("iconsStoragePath")).getAbsolutePath() + "/" + toolType.getIconName();
        System.out.println("path: " + path);
        path = "/app/" + toolType.getIconName();
        System.out.println("path: " + path);
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        for (int i = 0; i < icon.length; i++)
            fileOutputStream.write(icon[i]);
        fileOutputStream.close();
        toolType.setIconPath(environment.getProperty("iconsServePath") + toolType.getIconName());
    }

    @Override
    public void delete(Integer id) throws Exception {
        ToolType toolType = repository.findById(id).get();
        String path = new File(environment.getProperty("iconsStoragePath")).getAbsolutePath() + "/" + toolType.getIconName();
        boolean deleted = new File(path).delete();
        System.out.println(toolType.getIconName() + " deleted: " + deleted);
        super.delete(id);
    }
}
