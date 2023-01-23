package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Tool;
import com.fiteprojects.fitegis.Models.ToolType;
import com.fiteprojects.fitegis.Repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Service("ToolService")
public class ToolService extends GenericService<ToolRepository, Tool> {
    @Autowired
    ToolTypeService toolTypeService;
    @Autowired
    Environment environment;

    @Override
    public Tool add(Tool tool) throws Exception {
        tool.setToolType(toolTypeService.getById(tool.getToolTypeId()));
//        tool.setInstallationDate(new java.util.Date());
        saveImage(tool);
        Tool returnedModel = super.add(tool);
        returnedModel.setImage(null);
        return returnedModel;
    }

    public List<Tool> getByToolTypeId(Integer toolTypeId) throws Exception {
        return repository.findAllByToolTypeId(toolTypeId);
    }

    @Override
    public Tool update(Tool tool) throws Exception {
        if (tool.getToolType() != null)
            tool.setToolType(toolTypeService.getById(tool.getToolTypeId()));
        if (tool.getImage() != null)
            saveImage(tool);
        return super.update(tool);
    }

    private void saveImage(Tool tool) throws IOException {
        if (tool.getImageName() == null || tool.getImageName().equals(""))
            return;
        Byte[] icon = tool.getImage();
        String path = "/app/" + tool.getImageName();
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        for (int i = 0; i < icon.length; i++) {
            fileOutputStream.write(icon[i]);
        }
        fileOutputStream.close();
        tool.setImagePath(environment.getProperty("imagesServePath") + tool.getImageName());
    }

    public void deleteImage(Tool tool) throws Exception {
        String path = new File(environment.getProperty("imagesStoragePath")).getAbsolutePath() + "/" + tool.getImageName();
        boolean deleted = new File(path).delete();
        System.out.println(tool.getImageName() + " deleted: " + deleted);
        super.delete(tool.getId());
    }
}
