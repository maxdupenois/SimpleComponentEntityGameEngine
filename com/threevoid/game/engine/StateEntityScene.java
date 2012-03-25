/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.engine;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Max
 */
public abstract class StateEntityScene extends EntityScene{

   
    private final HashMap<String, State> states = new HashMap<String, State>();
    private State currentState;

    
    public StateEntityScene(String key){
        super(key);
    }
    

     public synchronized void addState(State state) throws SceneException{
        if(states.containsKey(state.getKey()))
            throw new SceneException("State '"+state.getKey()+"' already exists.");

        states.put(state.getKey(), state);
        state.setOwner(this);
        state.initialise();
    }

    public synchronized void clearStates(){
        states.clear();
    }
    public State getState(String key){
        return states.get(key);
    }
    public synchronized void removeState(String key){
        states.remove(key);
    }
    public synchronized void removeState(State state){
        String key = null;
        String k;
        Iterator<String> keys = states.keySet().iterator();
        while(keys.hasNext()&&key==null){
            k = keys.next();
            if(state.equals(states.get(k))){
                key = k;
            }
        }
        if(key!=null) removeState(key);
    }


    @Override
    public final void onGameLoad() throws SceneException{
        onGameLoad_PreStates();
        for(State s : states.values()){
            s.onGameLoad();
        }
    }

    public void onGameLoad_PreStates() throws SceneException{

    };

    public void preSwitchState(State from, State to){

    }

    public void postSwitchState(State from, State to){

    }
    public void switchState(String key) throws SceneException{
        if(!states.containsKey(key)) throw new SceneException("State '"+key+"' not found.");
        
        State next;
        State prev=null;
        synchronized(this){
            next = states.get(key);
            preSwitchState(getCurrentState(), next);
            next.onStatePreLoad();
            if(getCurrentState()!=null){
                prev = getCurrentState();
                prev.onStatePreLeave();
            }
            currentState = next;
        }
        if(prev!=null)prev.onStateLeave();
        getCurrentState().onStateLoad();
        postSwitchState(prev, getCurrentState());
    }

    @Override
    public void render(Graphics2D g){
       //First render this scenes entities
       super.render(g);
       //Now render current state
       if(getCurrentState() != null) getCurrentState().render(g);
    }
    @Override
    public void update(float updatesPerSecond) throws SceneException{
       //First update this scene
        synchronized(this){
           //Update scene
           sceneUpdate(input, updatesPerSecond);
           //Update scene entitites
           for(Entity entity :entities.getForUpdate()){
                try {
                    entity.update(input, updatesPerSecond);
                } catch (EntityException ex) {
                    throw new SceneException(ex);
                }
           }
           //We stick ourselves in here
           //as opposed to using the super.update call so we can avoid
           //a premature input clear
           if(getCurrentState() != null) getCurrentState().update(input, updatesPerSecond);
           input.clear();
       }
    }

    /**
     * @return the currentState
     */
    public State getCurrentState() {
        return currentState;
    }


    
}
