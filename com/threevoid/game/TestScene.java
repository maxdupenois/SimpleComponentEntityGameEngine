/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game;

import com.threevoid.game.engine.Scene;
import com.threevoid.game.engine.SceneException;
import com.threevoid.game.geometry.Polygon2D;
import com.threevoid.game.geometry.Vector2f;
import com.threevoid.game.physics.BasicPhysics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;

/**
 *
 * @author Max
 */
public class TestScene extends Scene{
    
    public TestScene(){
        super("testscene");
    }
    private float x = 0;
    private float y = 0;
//    private Point2D velocity = new Point2D.Double(.5, .5);
    private TestObject object1;
    private TestObject object2;
    private Vector2f point = new Vector2f(600, 300);
    private Polygon2D gameArea;
    boolean collision = false;
    @Override
    public void initialise() throws SceneException {
        object1 = new TestObject();
        object2 = new TestObject();
        BasicPhysics.GRAVITY = new Vector2f(0, 10);
        gameArea = new Polygon2D();
        gameArea.addVertex(0f, 0f);
        gameArea.addVertex(600f, 0f);
        gameArea.addVertex(600f, 600f);
        gameArea.addVertex(0f, 600f);
        gameArea.close();


    }

    @Override
    public void render(Graphics2D g) {
        GeneralPath path1 = object1.getPolygon().getPath();
        g.setColor((collision?Color.GREEN:Color.BLUE));
        g.draw(path1);
        GeneralPath path2 = object2.getPolygon().getPath();
        g.setColor((collision?Color.GREEN:Color.RED));
        g.draw(path2);

        g.setColor(Color.gray);
        g.draw(gameArea.getPath());

        g.drawString(""+object1.getVelocity().toString(1), (int)object1.getPosition().getX(),
                (int)object1.getPosition().getY()-20);
    }



    @Override
    public void update(float updatesPerSecond) throws SceneException {
        Vector2f nextPosition = BasicPhysics.nextPosition(object1, updatesPerSecond);
        collision = object1.collidesWith(nextPosition, object2);
        if(!object1.isWithin(nextPosition, gameArea)){
//            nextPosition = object1.getPosition();
            object1.getVelocity().multiplyY(0.9f);
            object1.getVelocity().flipY();
            object1.getVelocity().flipX();

        }
        object1.setPosition(nextPosition);
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_D){
            object1.addVelocity(20, 0);
        }
        if(e.getKeyCode()==KeyEvent.VK_A){
            object1.addVelocity(-20, 0);
        }
        if(e.getKeyCode()==KeyEvent.VK_W){
            object1.addVelocity(0, -20);
        }
        if(e.getKeyCode()==KeyEvent.VK_S){
            object1.addVelocity(0, 20);
        }
//        else if(e.getKeyCode()==KeyEvent.VK_SPACE){
//            System.out.println((object1.isWithin(gameArea)?"Is In":"Is Out"));
//        }

    }

    @Override
    public void onGameLoad() throws SceneException {
    }




}
