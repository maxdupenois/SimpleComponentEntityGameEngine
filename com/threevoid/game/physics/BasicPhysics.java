/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.physics;

import com.threevoid.game.geometry.Vector2f;
import com.threevoid.game.objects.GameObject;

/**
 *
 * @author Max
 */
public class BasicPhysics {
    public static Vector2f GRAVITY = new Vector2f(0, 0);
    public static <T extends GameObject> Vector2f nextPosition(T object, float updatesPerSecond){
        object.setVelocity(object.getVelocity().proportion(object.getDrag()));
        object.addVelocity(GRAVITY);
        Vector2f nextPos = object.getPosition().clone();
        nextPos.add(
                    object.getVelocity().getX()/updatesPerSecond,
                    object.getVelocity().getY()/updatesPerSecond
                    );
        return nextPos;
    }
}
