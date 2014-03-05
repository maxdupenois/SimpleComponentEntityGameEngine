/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.geometry;

/**
 *
 * @author Max
 */
public class LineSegment2f {
    private Vector2f start;
    private Vector2f end;
    public LineSegment2f(Vector2f start, Vector2f end){
        this.start = start;
        this.end = end;
    }

    public boolean isHorizontal(){
        return (this.start.getY()==this.end.getY());
    }
    public boolean isVertical(){
        return (this.start.getX()==this.end.getX());
    }

    public double getSlope(){
        if(isVertical()) return Double.POSITIVE_INFINITY;
        if(isHorizontal()) return 0;
        return (this.end.getY()-this.start.getY())/
                (this.end.getX()-this.start.getX());
    }
    public double getYIntercept(){
        if(isVertical()) throw new IllegalArgumentException("Line is vertical, does not cross the y axis");
        if(isHorizontal()) return this.getStart().getY();
        return this.start.getY()-(this.getSlope()*this.start.getX());
    }

    public double getXIntercept(){
        if(isHorizontal()) throw new IllegalArgumentException("Line is horizontal, does not cross the x axis");
        if(isVertical()) return this.getStart().getX();
        return (-getYIntercept()/this.getSlope());
    }


    public Vector2f intersect(LineSegment2f other){
        float x1 = start.x;
        float y1 = start.y;
        float x2 = end.x;
        float y2 = end.y;
        float x3 = other.start.x;
        float y3 = other.start.y;
        float x4 = other.end.x;
        float y4 = other.end.y;
        float x = ((x1*y2-y1*x2)*(x3-x4) - (x1-x2)*(x3*y4 - y3*x4))/
                ((x1-x2)*(y3-y4) - (y1 - y2)*(x3 - x4));
        float y = ((x1*y2-y1*x2)*(y3-y4) - (y1 - y2)*(x3*y4 - y3*x4))/
                ((x1 - x2)*(y3-y4) - (y1-y2)*(x3-x4));
        Vector2f intersect = new Vector2f(x, y);
        if(distanceFromPoint(intersect)<=0.00001){
            //close enough
            return intersect;
        }
        return null;
    }
    public float distanceFromPoint(Vector2f point){
        //Get projection of point onto line
        //(tangent of line that collides with point)
        //http://paulbourke.net/geometry/pointline/
        float x1 = start.x;
        float y1 = start.y;
        float x2 = end.x;
        float y2 = end.y;
        float x3 = point.x;
        float y3 = point.y;
        float u = ((x3-x1)*(x2-x1) + (y3-y1)*(y2-y1))/lengthSq();
        if (u < 0.0) return point.distance(start);       // Beyond the start  of the segment
        else if (u > 1.0) return point.distance(end);  // Beyond the end of the segment
        Vector2f projection =  new Vector2f(
                x1 + u*(x2-x1),
                y1 + u*(y2-y1));
        return point.distance(projection);
    }

    public float lengthSq(){
        return start.distanceSq(end);
    }

    public boolean isPointRightOfLine(Vector2f point){
       return (pointInRelationToLine(point)<0);
    }
    public boolean isPointLeftOfLine(Vector2f point){
       return (pointInRelationToLine(point)>0);
    }
    public boolean isPointOnLine(Vector2f point){
       return (pointInRelationToLine(point)==0);
    }

    /**
     * determine  x =   (y0-y1)(x2-x1)/(y2-y1) + x1<br/>
     *  <ul>
     *   <li>if x &lt; x0 then point is right of line</li>
     *   <li>if x = x0 then point is on line</li>
     *   <li>if x gt; x0 then point is left of line</li>
     * </ul><br/>
     *  So we return:
     *  <ul>
     *   <li>-1 if x &lt; 0 (Right(</li>
     *   <li>0 if x = 0 (On)</li>
     *   <li>1 if x &gt; 0 (Left)</li>
     * </ul>
     * @param point Point to check
     * @return position of the coordinate relative to line
     */
    public int pointInRelationToLine(Vector2f point){
        /*determine  x =   (y0-y1)(x2-x1)/(y2-y1) + x1
            if x < x0 then point is right of line
            if x= x0 then point is on line
            if x > x0 then point is left of line
         */
         /*
           For three points (x1,y1), (x2,y2) and (x3,y3),
          simply compute the direction of the cross product
          of the two vectors defined by points (x1,y1), (x2,y2) and (x1,y1), (x3,y3),
          characterized by the sign of the expression (x2 − x1)(y3 − y1) − (y2 − y1)(x3 − x1).
          If the result is 0, the points are collinear; if it is positive,
          the three points constitute a "left turn", otherwise a "right turn"
          */

        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();
        double x3 = point.getX();
        double y3 = point.getY();

        double val = ((x2 - x1)*(y3 - y1)) - ((y2 - y1)*(x3 - x1));
//        double val = (this.finish.getX()-this.start.getX()*(coord.getY()-this.start.getY()))-
//        (this.finish.getY()-this.start.getY())*(coord.getX()-this.start.getX());

        return (int)Math.signum(val);
    }



    /**
     * @return the start
     */
    public Vector2f getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Vector2f start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Vector2f getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Vector2f end) {
        this.end = end;
    }

    public void set(Vector2f start, Vector2f end) {
        this.start = start;
        this.end = end;
    }

}
