/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.engine;


/**
 *
 * @author mpd209
 */
public class SceneException extends Exception {

    /**
     * Creates a new instance of <code>SceneException</code> without detail message.
     */
    public SceneException() {
    }


    /**
     * Constructs an instance of <code>SceneException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SceneException(String msg) {
        super(msg);
    }

    public SceneException(Throwable throwable) {
        super(throwable);
    }

    public SceneException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
