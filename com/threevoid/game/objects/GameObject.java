/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.objects;

import com.threevoid.game.collisions.QuadrantCheck;
import com.threevoid.game.geometry.IsotheticBoundingBox2D;
import com.threevoid.game.geometry.Polygon2D;
import com.threevoid.game.geometry.Vector2f;

/**
 *
 * @author Max
 */
public abstract class GameObject {
    //Top Left
    private Vector2f position = new Vector2f(0, 0);
    private Vector2f velocity = new Vector2f(0, 0);
    private Vector2f drag = new Vector2f(0, 0);
    private Polygon2D polygon = new Polygon2D();




    /**
     * @return the position
     */
    public final Vector2f getPosition() {
        return position;
    }

    public final double getX() {
        return this.position.getX();
    }
    public final double getY() {
        return this.position.getY();
    }

    
    /**
     * @param position the position to set
     */
    public final void setPosition(Vector2f position) {
        this.position = position;
        this.polygon.setTopLeft(position);
    }
    public final void setPosition(float x, float y) {
        this.position.set(x, y);
        this.polygon.setTopLeft(position);
    }
    public final void moveBy(Vector2f vector) {
        this.position.add(vector);
        this.polygon.setTopLeft(position);
    }
    public final void moveBy(float x, float y) {
        this.position.add(x, y);
        this.polygon.setTopLeft(position);
    }
    /**
     * @return the polygon
     */
    public final Polygon2D getPolygon() {
        return polygon;
    }
    /**
     * @return the polygon as an array of vertices of global positions
     */
    public final Vector2f[] getVertices() {
        return polygon.toGlobalArray();
    }
    /**
     * @param shape the shape to set
     */
    public final void setPolygon(Polygon2D shape) {
        this.polygon = shape;
    }
    public final void addVertex(Vector2f vertex) {
        this.polygon.addVertex(vertex);
    }
    public final void addVertex(float x, float y) {
        this.polygon.addVertex(new Vector2f(x, y));
    }

    public final void closePolygon() {
        this.polygon.close();
    }

    public final IsotheticBoundingBox2D getBoundingBox(){
        return this.polygon.getIsotheticBoundingBox();
    }

    public boolean isWithin(Polygon2D polygon){
        return isWithin(this.getPosition(), polygon);
    }
    public boolean isWithin(Vector2f location, Polygon2D polygon){
        Polygon2D myPolygon = this.polygon.getPolygonFrom(location);
//        System.out.println("Got Polygons");
        if(!polygon.getIsotheticBoundingBox().
                containsExcl(myPolygon.getIsotheticBoundingBox())){
//            System.out.println("polygon does not contain me");
            return false;
        }
//        System.out.println("Bounding boxes contained");

        Vector2f[] vertices = myPolygon.toGlobalArray();
        boolean vertexOutside = false;
        for(int i=0; i<vertices.length && !vertexOutside; i++){
            vertexOutside = !QuadrantCheck.pointInPolygon(polygon, vertices[i]);
        }
        return (!vertexOutside);
    }
    public boolean collidesWith(Vector2f myNextLocation, GameObject other){
        Polygon2D nextPolygon = this.polygon.getPolygonFrom(myNextLocation);
        //check rectangles first to save time
        if(!nextPolygon.getIsotheticBoundingBox().collidesIncl(other.getBoundingBox())) return false;
        Vector2f[] vertices = nextPolygon.toGlobalArray();
        boolean collisionFound = false;
        for(int i=0; i<vertices.length && !collisionFound; i++){
            collisionFound = QuadrantCheck.pointInPolygon(other.getPolygon(), vertices[i]);
        }
        if(collisionFound) return true;
        vertices = other.getPolygon().toGlobalArray();
        for(int i=0; i<vertices.length && !collisionFound; i++){
            collisionFound = QuadrantCheck.pointInPolygon(this.getPolygon(), vertices[i]);
        }
        return collisionFound;
    }

    /**
     * @return the velocity
     */
    public final Vector2f getVelocity() {
        return velocity;
    }

    /**
     * @param velocity the velocity to set
     */
    public final void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
    public final void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }
    public final void addVelocity(Vector2f velocity) {
        this.velocity.add(velocity);
    }
    public final void addVelocity(float x, float y) {
        this.velocity.add(x, y);
    }
    /**
     * @return the drag
     */
    public final Vector2f getDrag() {
        return drag;
    }

    /**
     * @param drag the drag to set
     */
    public final void setDrag(Vector2f drag) {
        this.drag = drag;
    }
    public final void setDrag(float x, float y) {
        this.drag.set(x, y);
    }

}
