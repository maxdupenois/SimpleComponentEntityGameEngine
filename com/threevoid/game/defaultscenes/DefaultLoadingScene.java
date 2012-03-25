/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.defaultscenes;

import com.threevoid.game.defaultcomponents.FlatRectangleRenderComponent;
import com.threevoid.game.defaultcomponents.LoadingBarComponent;
import com.threevoid.game.defaultcomponents.RenderTextComponent;
import com.threevoid.game.engine.Engine;
import com.threevoid.game.engine.Entity;
import com.threevoid.game.engine.EntityScene;
import com.threevoid.game.engine.Renderer;
import com.threevoid.game.engine.Scene;
import com.threevoid.game.engine.SceneController;
import com.threevoid.game.engine.SceneException;
import com.threevoid.game.geometry.Vector2f;
import com.threevoid.game.resources.images.ImageException;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author mpd209
 */
public abstract class DefaultLoadingScene extends EntityScene{
    private LoadingThread loadingThread;
    public class LoadingThread implements Runnable{
        public boolean loaded;
        public LoadingThread(){
            loaded = false;
        }
        public void run() {
            //ADDING IMAGES
            setText("Loading: images");
            try{
                loadImages();
            } catch (ImageException ex) {
                Engine.get().exceptionThrown(ex);
                Engine.get().fatalError("Failed to load images: "+ex.getMessage());
            }
            setText("Initialising: animations");
            try{
                initAnimations();
            } catch (Exception ex) {
                Engine.get().exceptionThrown(ex);
                Engine.get().fatalError("Failed to initialise animations: "+ex.getMessage());
            }
            getLoadingBar().setValue(0.25f);
            setText("Loading: sounds");
            loadSound();
            getLoadingBar().setValue(0.5f);
            try{
                setText("Loading: other assets");
                loadOtherAssets();
            } catch (Exception ex) {
                Engine.get().exceptionThrown(ex);
                Engine.get().fatalError("Failed to load assets: "+ex.getMessage());
            }
            getLoadingBar().setValue(0.75f);

            //Run On Game load of Scenes (deals with objects that require images)
           try {
                setText("Loading: scenes");
                for (Scene scene : SceneController.get().getScenesArray()) scene.onGameLoad();
            } catch (SceneException ex) {
                Engine.get().exceptionThrown(ex);
                Engine.get().fatalError("Failed when loading scenes: "+ex.getMessage());
            }

            getLoadingBar().setValue(1f);

            loaded = true;
        }

    }

    private String afterLoadScene;

    public DefaultLoadingScene(String key, String afterLoadScene){
        super(key);
        this.afterLoadScene = afterLoadScene;
    }

    public abstract void loadImages() throws ImageException;
    public abstract void initAnimations() throws ImageException;
    public abstract void loadSound();
    public abstract void loadOtherAssets() throws Exception;

    @Override
    public void update(float updatesPerSecond) throws SceneException{
        super.update(updatesPerSecond);
        if(loadingThread==null){
            loadingThread = new LoadingThread();
            new Thread(loadingThread).start();
        }else if(loadingThread!=null&&loadingThread.loaded){
            SceneController.get().setCurrentScene(afterLoadScene);
        }
    }
    @Override
    public void initialise() throws SceneException {
        Entity background = new Entity("BACKGROUND");
        background.setAwake(true);
        background.setVisible(true);
        background.setHeight(Renderer.get().getHeight());
        background.setWidth(Renderer.get().getWidth());
        background.addComponent(new FlatRectangleRenderComponent("RENDER", Color.black));

        Entity loadingBar = new Entity("LOADING");
        loadingBar.setAwake(true);
        loadingBar.setVisible(true);
        loadingBar.setHeight(50f);
        loadingBar.setWidth(300f);
        loadingBar.setPosition(new Vector2f(
                Renderer.get().getWidth()/2f-loadingBar.getWidth()/2f,
                Renderer.get().getHeight()/2f-loadingBar.getHeight()/2f
                ));
        loadingBar.setRenderOrdering(.1f);
        LoadingBarComponent lbcomp = new LoadingBarComponent("RENDER", Color.white, Color.red.darker());
        loadingBar.addComponent(lbcomp);
        addEntity(background);
        addEntity(loadingBar);

        Entity text = new Entity("TEXT");
        text.setAwake(true);
        text.setVisible(true);
        text.setPosition(new Vector2f(
                Renderer.get().getWidth()/2f,
                Renderer.get().getHeight()/2f+loadingBar.getHeight()
                ));
        text.setRenderOrdering(.2f);
        text.addComponent(new RenderTextComponent("RENDER", "", new Font(Font.MONOSPACED, Font.PLAIN, 20), Color.WHITE ,RenderTextComponent.TextPositionH.CENTER, RenderTextComponent.TextPositionV.TOP));
        addEntity(text);


    }
    public final LoadingBarComponent getLoadingBar(){
        return (LoadingBarComponent)getEntity("LOADING").getComponent("RENDER");
    }
    public final void setText(String text){
        ((RenderTextComponent)getEntity("TEXT").getComponent("RENDER")).setText(text);
    }
    public final void onGameLoad()throws SceneException {
        //Do nothing
    }

}
