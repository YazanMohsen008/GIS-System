package com.fiteprojects.fitegis.Models.GIS;


import com.vividsolutions.jts.geom.Geometry;

import java.io.Serializable;

public interface GISCoordinates extends Serializable {

     Geometry getGeom();

}
