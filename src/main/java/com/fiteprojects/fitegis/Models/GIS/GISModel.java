package com.fiteprojects.fitegis.Models.GIS;


import com.fiteprojects.fitegis.Models.Camera;
import com.fiteprojects.fitegis.Models.GenericModel;
import com.fiteprojects.fitegis.Models.Location;
import com.fiteprojects.fitegis.Utils.Services.GeometryService;
import org.springframework.security.core.parameters.P;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GISModel implements Serializable {
    String type;
    ArrayList<Feature> Features;
    Integer totalFeatures;
    Integer numberMatched;
    Integer numberReturned;
    Timestamp timeStamp;
    HashMap<String, Serializable> crs;

    public GISModel(Integer size) {
        this.type = "FeatureCollection";
        this.Features = new ArrayList<>();
        this.totalFeatures = size;
        this.numberMatched = size;
        this.numberReturned = size;
        this.timeStamp = new Timestamp(new Date().getTime());
        this.crs = new HashMap<>();
        this.crs.put("type", "name");
        HashMap<String, Serializable> properties = new HashMap<>();
        properties.put("name", "urn:ogc:def:crs:EPSG::4326");
        this.crs.put("properties", properties);
    }

    public void addFeature(GenericModel model, String modelName, String modelType, Class modelClass) throws IllegalAccessException {
            Feature feature = new Feature();
            feature.setType("Feature");
            feature.setId(modelName + "." + model.getId());
            feature.setGeometry(configureGeometry(modelType, (GISCoordinates) model));
            feature.setGeometry_name("geom");
            feature.addProperty("id",model.getId());
        for (Field field : modelClass.getDeclaredFields()) {
                if(field.getName().equals("geom"))
                    continue;
                field.setAccessible(true);
                feature.addProperty(field.getName(), (Serializable) field.get(model));
            }
            this.Features.add(feature);

    }

    public void addFeature(List<? extends GenericModel> models, String modelName, String modelType, Class modelClass) {
        models.forEach(model -> {
            try {
                addFeature(model, modelName, modelType, modelClass);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }

    private static Geometry configureGeometry(String modelType, GISCoordinates model) {
        try {
            if (modelType.equals("Polygon")) {
                Polygon polygon = new Polygon();
                polygon.setCoordinatesList(GeometryService.serializePolygon(model.getGeom()));
                polygon.setType(modelType);
                return polygon;
            }
            if (modelType.equals("Point")) {
                Point point = new Point();
                point.setCoordinates(GeometryService.serializePoint(model.getGeom()));
                point.setType(modelType);
                return point;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Feature> getFeatures() {
        return Features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        Features = features;
    }

    public Integer getTotalFeatures() {
        return totalFeatures;
    }

    public void setTotalFeatures(Integer totalFeatures) {
        this.totalFeatures = totalFeatures;
    }

    public Integer getNumberMatched() {
        return numberMatched;
    }

    public void setNumberMatched(Integer numberMatched) {
        this.numberMatched = numberMatched;
    }

    public Integer getNumberReturned() {
        return numberReturned;
    }

    public void setNumberReturned(Integer numberReturned) {
        this.numberReturned = numberReturned;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HashMap<String, Serializable> getCrs() {
        return crs;
    }

    public void setCrs(HashMap<String, Serializable> crs) {
        this.crs = crs;
    }

    public GISModel(String type, ArrayList<Feature> features, Integer totalFeatures, Integer numberMatched, Integer numberReturned, Timestamp timeStamp, HashMap<String, Serializable> crs) {
        this.type = type;
        Features = features;
        this.totalFeatures = totalFeatures;
        this.numberMatched = numberMatched;
        this.numberReturned = numberReturned;
        this.timeStamp = timeStamp;
        this.crs = crs;
    }

    public void addProperty(String key, Serializable value) {
        Features.get(0).addProperty(key, value);
    }
}
