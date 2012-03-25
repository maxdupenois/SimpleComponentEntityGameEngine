/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.engine;

/**
 *
 * @author mpd209
 */
public abstract class Component {
    private String id;
    private Entity owner;
    public Component(String id){
        this.id = id;
    }

    public final String getId(){
        return id;
    }

    public final Entity getOwner(){
        return owner;
    }
    final void setOwner(Entity owner){
        this.owner = owner;
    }

    public final boolean isRender(){
        return (this instanceof RenderComponent);
    }

    public abstract void update(Input input, float updatesPerSecond) throws EntityException;
}
