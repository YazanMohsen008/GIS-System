package com.fiteprojects.fitegis.Services;

import com.fiteprojects.fitegis.Models.Location;
import com.fiteprojects.fitegis.Models.DTO.LocationDTO;
import com.fiteprojects.fitegis.Repositories.LocationRepository;
import com.fiteprojects.fitegis.Utils.Services.GeometryService;
import com.vividsolutions.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("LocationService")
public class LocationService extends GenericService<LocationRepository, Location> {


    @Autowired
    GeometryService geometryService;

    @Override
    public Location add(Location location) throws Exception {
        try {
            Geometry geometry = geometryService.deserializePolygon(location.getCoordinates());
            location.setGeom(geometry);
            repository.save(location);
            return location;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }



    public List<LocationDTO> getAllLikeWord(String word) {
        List<LocationDTO> locations = new ArrayList<>();
        List<Object[]> locationValues;

        locationValues = repository.getAllLikeWord(word);
        locationValues.forEach(
                location -> {
                    LocationDTO locationDTO = new LocationDTO();
                    locationDTO.setId((Integer) location[0]);
                    locationDTO.setEn_name((String) location[1]);
                    locationDTO.setAr_name((String) location[2]);
                    locationDTO.setType((String) location[3]);
                    locationDTO.setLectureHolder((Boolean) location[4]);
                    locationDTO.setFloor((Integer) location[5]);
                    locations.add(locationDTO);
                }
        );
        return locations;
    }

    public List<Location> getAllWithFilter(Integer floorNumber,Boolean isLectureHolder) {
        List<Location> models;
        try {
            models = new ArrayList<>(repository.findAllWithFilter(floorNumber,isLectureHolder));
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
        return models;
    }
}
