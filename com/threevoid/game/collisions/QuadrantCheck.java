/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.collisions;

import com.threevoid.game.geometry.*;

/**
 * One method for determining if the test point is in a polygon
 * to divide the polygon into quadrants centered on the test point.
 * Start at the first vertex in the polygon and set a counter to 0.
 * Anytime an edge crosses from one quadrant to the next, add one to the
 * counter if it crosses clockwise around the test point and subtract one
 * if it crosses counter-clockwise. If the edge crosses diagonally across
 * two quadrants, you need to determine which side of the test point it
 * crossed, and then either add or subtract 2.
 * http://www.gamasutra.com/view/feature/3429/crashing_into_the_new_year_.php?page=1
 * @author Max
 */
public class QuadrantCheck {

    /**
     * Checks if a point is within a (possibly) concave 2D polygon
     * @param polygon Polygon to see if point is in
     * @param point Point to check if is in polygon
     * @return true if point is in the polygon, false otherwise
     */
    public static boolean pointInPolygon(Polygon2D polygon, Vector2f point){
        Vector2f[] vertices = polygon.toGlobalArray();
        if(vertices.length==0) return false;
        int total = 0;
        Vector2f currentVertex = vertices[0];
        int vertexQuadrant = whichQuadrant(point, currentVertex);

        Vector2f nextVertex;
        int nextVertexQuadrant;
        int quadrantChange;
        for(int i = 1; i <= vertices.length; i++){
            if(i == vertices.length){
                nextVertex = vertices[0];
            }else{
                nextVertex = vertices[i];
            }
            nextVertexQuadrant = whichQuadrant(point, nextVertex);
            quadrantChange = nextVertexQuadrant-vertexQuadrant;
            LineSegment2f edge = new LineSegment2f(currentVertex, nextVertex);
            switch (quadrantChange) {
                case 2:
                    //Will appear back to front on screen because of flipped y
                    if (edge.isPointRightOfLine(point)){
                        quadrantChange = - 2;
                    }
                break;
                case -2:
                    //Will appear back to front on screen because of flipped y
                    if (edge.isPointLeftOfLine(point)){
                        quadrantChange = 2;
                    }
                break;
                case 3: // MOVING 3 QUADS IS LIKE MOVING BACK 1
                    quadrantChange = -1;
                break;
                case -3:	// MOVING BACK 3 IS LIKE MOVING FORWARD 1 (e.g., 4->1)
                    quadrantChange =	 1;
                break;
            }
            total += quadrantChange;
            vertexQuadrant = nextVertexQuadrant;
            currentVertex = nextVertex;
        }
        return (Math.abs(total)==4);
    }

    public static int whichQuadrant(Vector2f point, Vector2f vertex){
        return (vertex.getX()<point.getX()?
                    (vertex.getY()<point.getY()? 1 : 4):
                    (vertex.getY()<point.getY()? 2 : 3)
                );
    }
}

