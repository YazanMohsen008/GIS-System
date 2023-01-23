package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.GIS.GISModel;
import com.fiteprojects.fitegis.Utils.Enums.keys;
import com.fiteprojects.fitegis.Utils.Services.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@PropertySource("classpath:gis.properties")
public class GISService {

    @Autowired
    HttpService httpService;
    @Autowired
    Environment environment;

    public Map<String, String> getAll() throws Exception {
        Map<String, String> response = null;
        try {
            response = (Map<String, String>) httpService.doGet(environment.getProperty("gethalls"), null, null, Map.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return response;
    }

    public Map<String, String> getAllWithFilter(Integer floorNumber) throws Exception {
        Map<String, String> response = null;
        try {

            if (floorNumber == null)
                return null;
            String url = environment.getProperty("gethalls");
            String filter = "";
            filter += keys.FLOOR.getKey() + keys.EQUALS.getKey() + floorNumber;
            url += environment.getProperty("CQLFilter") + filter;

            response = (Map<String, String>) httpService.doGet(url, null, null, Map.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return response;
    }

    public GISModel getByID(Integer locationId) throws Exception {
        GISModel response = null;
        try {
            String url = environment.getProperty("gethalls");
            if (locationId == null)
                return null;
            url += environment.getProperty("FeatureID") + locationId;

            response = (GISModel) httpService.doGet(url, null, null, GISModel.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return response;
    }


}
