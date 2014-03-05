/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game;

import com.threevoid.game.engine.Engine;
import com.threevoid.game.engine.Game;
import com.threevoid.game.engine.SceneException;

/**
 *
 * @author Max
 */
public class TestGame extends Game{
    public TestGame(){
        super("Test App", 950,580);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TestGame game = new TestGame();
        game.setShowFPS(true);
        game.setRequiredFPS(60);
        try {
            game.start();
        } catch (SceneException ex) {
            Engine.get().exceptionThrown(ex);
            Engine.get().fatalError("Scene Exception at Game Start: "+ex.getMessage());
        }
    }

    @Override
    public void initialise() {
    }

    @Override
    public void addScenes() throws SceneException {
        addScene(new TestScene());
    }

}
