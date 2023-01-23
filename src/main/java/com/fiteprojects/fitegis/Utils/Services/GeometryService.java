package com.fiteprojects.fitegis.Utils.Services;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class GeometryService {
    public Geometry wktToGeometry(String geometryString)
            throws ParseException {
        return new WKTReader().read(geometryString);
    }

    public Geometry deserializePolygon(ArrayList<ArrayList<Double>> polygonPoints) throws ParseException {

        StringBuilder polygon = new StringBuilder("POLYGON ((");
        for (int i = 0; i < polygonPoints.size(); i++) {
            Double x = polygonPoints.get(i).get(0);
            polygon.append(convertDouble(x)).append(" ");
            Double y = polygonPoints.get(i).get(1);
            polygon.append(convertDouble(y)).append(" ");
            Double z = polygonPoints.get(i).get(2);
            polygon.append(convertDouble(z));
            if (i < polygonPoints.size() - 1)
                polygon.append(",");
            else
                polygon.append("))");
        }
        return wktToGeometry(polygon.toString());
    }

    public static ArrayList<ArrayList<Double>> serializePolygon(Geometry value) throws IOException,
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
        return coordinates;
    }

    private static Double convertDouble(Double value) {
        DecimalFormat df = new DecimalFormat("#.########");
        df.setRoundingMode(RoundingMode.FLOOR);
        return Double.parseDouble(df.format(value));
    }

    public Geometry deserializePoint(ArrayList<Double> pointCoordinates) throws ParseException {

        StringBuilder point = new StringBuilder("POINT (");
        Double x = pointCoordinates.get(0);
        point.append(convertDouble(x)).append(" ");
        Double y = pointCoordinates.get(1);
        point.append(convertDouble(y));
        point.append(")");
        return wktToGeometry(point.toString());
    }

    public static ArrayList<Double> serializePoint(Geometry value) throws
            JsonProcessingException {
        ArrayList<Double> coordinates = new ArrayList<>();
        try {
            if (value != null) {
                Double x = value.getCoordinates()[0].x;
                coordinates.add(convertDouble(x));
                Double y = value.getCoordinates()[0].y;
                coordinates.add(convertDouble(y));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return coordinates;
    }

}
