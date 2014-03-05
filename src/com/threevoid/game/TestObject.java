/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game;

import com.threevoid.game.objects.GameObject;

/**
 *
 * @author Max
 */
public class TestObject extends GameObject{
    public TestObject(){
        this.setPosition(200, 200);
        this.setDrag(.01f, .01f);
        //Rhombus
//        this.addVertex(0, 0);
//        this.addVertex(100, 0);
//        this.addVertex(150, 100);
//        this.addVertex(50, 100);
//        this.addVertex(0, 0);
        //Slightly Odd Object
//        this.addVertex(0, 0);
//        this.addVertex(100, 100);
//        this.addVertex(100, 150);
//        this.addVertex(200, 150);
//        this.addVertex(150, 300);
//        this.addVertex(0, 0);
        //Spiky shape with concavity
//        this.addVertex(0, 0);
//        this.addVertex(100, 100);
//        this.addVertex(50, 75);
//        this.addVertex(100, 350);
//        this.addVertex(0, 0);
        //Hexagon
        this.addVertex(0, 0);
        this.addVertex(100, 0);
        this.addVertex(150, 50);
        this.addVertex(150, 150);
        this.addVertex(100, 200);
        this.addVertex(0, 200);
        this.addVertex(-50, 150);
        this.addVertex(-50, 50);
        this.addVertex(0, 0);

    }
}
