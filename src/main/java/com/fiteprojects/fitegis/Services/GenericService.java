package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.GenericModel;
import com.fiteprojects.fitegis.Repositories.GenericRepository;
import com.fiteprojects.fitegis.Utils.Services.GenericConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


public class GenericService<
        Repository extends GenericRepository<Model>,
        Model extends GenericModel> {
    @Autowired
    Repository repository;
    @Autowired
    GenericConfig<Model> genericConfig;

    public Model add(Model model) throws Exception {
        try {
            return repository.save(model);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public List<Model> getAll(String lang,Integer page,Integer pageSize) throws Exception {
        List<Model> models = new ArrayList<>();
        try {
            repository.findAll().forEach(models::add);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return models;
    }

    public List<Model> getAll() throws Exception {
        List<Model> models = new ArrayList<>();
        try {
            repository.findAll().forEach(models::add);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return models;
    }

    public Model getById(Integer id, String lang) throws Exception {
        try {
            return repository.findById(id).get();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Model getById(Integer id) throws Exception {
        try {
            return repository.findById(id).get();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Model update(Model model) throws Exception {
        try {
            Model old = getById(model.getId());
            Model newModel = genericConfig.update(old, model);
            return repository.save(newModel);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public void delete(Integer id) throws Exception {
        try {
            repository.deleteById(id);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

}
