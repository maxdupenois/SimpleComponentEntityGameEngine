/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

/**
 *
 * @author mpd209
 */
public abstract class State {

    private EntityMap entities = new EntityMap();

    private StateEntityScene owner;
    private String key;
    public State(String key){
        this.key = key;
    }

    public abstract void initialise() throws SceneException;
    public abstract void onGameLoad() throws SceneException;
    public abstract void onStatePreLoad() throws SceneException;
    public abstract void onStateLoad() throws SceneException;
    public abstract void onStatePreLeave() throws SceneException;
    public abstract void onStateLeave() throws SceneException;

    public synchronized void addEntity(Entity entity) throws EntityException{
        entity.setParentState(this);
        entities.add(entity);
    }
    public synchronized void removeEntity(Entity entity) throws EntityException{
        entity.setParentState(null);
        entities.remove(entity);
    }
    public synchronized void removeEntity(String entitykey) throws EntityException{
        getEntity(entitykey).setParentState(null);
        entities.remove(entitykey);
    }
    public synchronized void clearEntities(){
        for(Entity e : entities.getAll()) e.setParentState(null);
        entities.clear();
    }
    public Entity getEntity(String entityId){
        return entities.get(entityId);
    }


    public void render(Graphics2D g){
       synchronized(this){
           for(Entity entity : entities.getForRender()){
               entity.render(g);
           }
       }
    }
    public void update(Input input, float updatesPerSecond) throws SceneException{
        synchronized(this){
           for(Entity entity :entities.getForUpdate()){
                try {
                    entity.update(input, updatesPerSecond);
                } catch (EntityException ex) {
                    throw new SceneException(ex);
                }
           }
       }
    }

    

    /**
     * @return the owner
     */
    public StateEntityScene getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    void setOwner(StateEntityScene owner) {
        this.owner = owner;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
}
