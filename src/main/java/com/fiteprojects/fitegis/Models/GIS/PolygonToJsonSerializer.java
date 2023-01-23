package com.fiteprojects.fitegis.Models.GIS;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.springframework.stereotype.Component;

public class PolygonToJsonSerializer extends JsonSerializer<Geometry> {

    @Override
    public void serialize(Geometry value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        try {
            if (value != null) {
                for (int i = 0; i < value.getCoordinates().length; i++) {
                    ArrayList<Double> pointCoordinates = new ArrayList<>();
                    Double x = value.getCoordinates()[i].x;
                    pointCoordinates.add(convertDouble(x));
                    Double y = value.getCoordinates()[i].y;
                    pointCoordinates.add(convertDouble(y));
                    Double z = value.getCoordinates()[i].z;
                    pointCoordinates.add(convertDouble(z));
                    coordinates.add(pointCoordinates);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jgen.writeObject(coordinates);
    }

    private static Double convertDouble(Double value) {
        DecimalFormat df = new DecimalFormat("#.########");
        df.setRoundingMode(RoundingMode.FLOOR);
        return Double.parseDouble(df.format(value));
    }

}