package com.fiteprojects.fitegis.Models.GIS;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PointToJsonSerializer extends JsonSerializer<Geometry> {

    @Override
    public void serialize(Geometry value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {

        ArrayList<ArrayList<Double>> coordinates = new ArrayList<>();
        try {
            if (value != null) {
                    ArrayList<Double> pointCoordinates = new ArrayList<>();
                    Double x = value.getCoordinates()[0].x;
                    pointCoordinates.add(convertDouble(x));
                    Double y = value.getCoordinates()[0].y;
                    pointCoordinates.add(convertDouble(y));
                    coordinates.add(pointCoordinates);
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