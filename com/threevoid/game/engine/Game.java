/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threevoid.game.engine;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Max
 */
public abstract class Game {

    static final int DefaultFPS = 60;
    static final String DefaultTitle = "No Title";
    static final boolean DefaultShowFPS = false;
    static final Dimension DefaultDimensions = new Dimension(300, 300);
    private String gameTitle=DefaultTitle;
    private Dimension dimensions = DefaultDimensions;
    private boolean showFPS = false;
    private int requiredFPS = DefaultFPS;
    
    private Engine gameEngine; 
    private GameContainer container;
    
    private boolean runningAsApplet = false;
    
    public abstract void initialise();
    
    public Game(String title, int width, int height){
        this.gameTitle = title;
        this.dimensions = new Dimension(width, height);
    }
    public abstract void addScenes()throws SceneException; 
    
    
    protected final void addScene(Scene scene)throws SceneException{
        SceneController.get().addScene(scene);
    }
    public final void start()throws SceneException{
        initialise();
        if(!runningAsApplet){
            GameFrame.instantiate(gameTitle);
            GameFrame.get().setPreferredSize(dimensions);
            GameFrame.get().setMinimumSize(dimensions);
            GameFrame.get().setMaximumSize(dimensions);
            GameFrame.get().setSize(dimensions);
            GameFrame.get().setResizable(false);
            GameFrame.get().addWindowListener(new WindowListener() {
                public void windowOpened(WindowEvent e) { }
                public void windowClosing(WindowEvent e) {System.exit(0);}
                public void windowClosed(WindowEvent e) {}
                public void windowIconified(WindowEvent e) {}
                public void windowDeiconified(WindowEvent e) {}
                public void windowActivated(WindowEvent e) {}
                public void windowDeactivated(WindowEvent e) {}
            });
            GameFrame.get().setVisible(true);
            container = GameFrame.get();
        }else{
            GameApplet.get().setPreferredSize(dimensions);
            GameApplet.get().setMinimumSize(dimensions);
            GameApplet.get().setMaximumSize(dimensions);
            GameApplet.get().setSize(dimensions);
            GameApplet.get().setVisible(true);
            container = GameApplet.get();
        }
        gameEngine = Engine.instantiate(this);
        gameEngine.setRequiredFPS(requiredFPS);
        if(isShowFPS()) gameEngine.showFPS();
        
        
        addScenes();
        gameEngine.start();
    }
    

    /**
     * @return the gameTitle
     */
    public final String getGameTitle() {
        return gameTitle;
    }

    /**
     * @param gameTitle the gameTitle to set
     */
    protected final void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    /**
     * @return the dimensions
     */
    public final Dimension getDimensions() {
        return dimensions;
    }

    /**
     * @param dimensions the dimensions to set
     */
    protected final void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * @return the showFPS
     */
    public final boolean isShowFPS() {
        return showFPS;
    }

    /**
     * @param showFPS the showFPS to set
     */
    protected final void setShowFPS(boolean showFPS) {
        this.showFPS = showFPS;
    }

    /**
     * @return the requiredFPS
     */
    public final int getRequiredFPS() {
        return requiredFPS;
    }

    /**
     * @param requiredFPS the requiredFPS to set
     */
    protected final void setRequiredFPS(int requiredFPS) {
        this.requiredFPS = requiredFPS;
    }


    /**
     * @return the runAsApplet
     */
    public final boolean isRuningAsApplet() {
        return runningAsApplet;
    }

    /**
     * @param runAsApplet the runAsApplet to set
     */
    final void setRunningAsApplet(boolean runAsApplet) {
        this.runningAsApplet = runAsApplet;
    }

    /**
     * @return the container
     */
    public GameContainer getContainer() {
        return container;
    }
    
}
