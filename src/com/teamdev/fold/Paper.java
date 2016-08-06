package com.teamdev.fold;

import com.teamdev.OPolygon;

import java.util.List;

/**
 * @author Vladislav Kovchug
 */
public class Paper {
    private List<OPolygon> polygons;

    public Paper(List<OPolygon> vertices) {
        this.polygons = vertices;
    }

    public List<OPolygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<OPolygon> polygons) {
        this.polygons = polygons;
    }

    public void add(OPolygon polygon){
        this.polygons.add(polygon);
    }

}
