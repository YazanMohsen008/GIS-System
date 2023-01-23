package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Lecture;
import com.fiteprojects.fitegis.Models.Log;
import com.fiteprojects.fitegis.Models.Tool;
import com.fiteprojects.fitegis.Models.ToolType;
import com.fiteprojects.fitegis.Repositories.LogRepository;
import com.fiteprojects.fitegis.Repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("LogService")
public class LogService {
    @Autowired
    LogRepository repository;

    public Log add(Log log) throws Exception {
        try {
            return repository.save(log);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public List<Log> getAllWithFilter(String action, String category, Integer page, Integer count) throws Exception {
        if (action.equals(""))
            action = null;
        if (category.equals(""))
            category = null;
        List<Log> logs = repository.getAllWithFilter(action, category);
        List<Log> result = new ArrayList<>();
        if (count == null)
            count = 10;
        if (count > logs.size())
            count = logs.size();
        if (count < logs.size() && count <= page)
            count = page + count;
        for (int i = page; i < count; i++) {
            Log log = logs.get(i);
            result.add(log);
        }
        return result;
    }

}
