package applab.client.search.utils;

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package com.jts.test;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import java.util.ArrayList;

/**
 * @author grameen
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        final GeometryFactory gf = new GeometryFactory();

        final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
        points.add(new Coordinate(-10, -10));
        points.add(new Coordinate(-10, 10));
        points.add(new Coordinate(10, 10));
        points.add(new Coordinate(10, -10));
        points.add(new Coordinate(-10, -10));
        final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
                .toArray(new Coordinate[points.size()])), gf), null);

        // final Coordinate coord = new Coordinate(0, 0);
        //final Point point = gf.createPoint(coord);

        System.out.println(polygon.getArea());


    }

}