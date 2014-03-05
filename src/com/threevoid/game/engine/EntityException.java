/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.engine;


/**
 *
 * @author mpd209
 */
public class EntityException extends SceneException {

    /**
     * Creates a new instance of <code>SceneException</code> without detail message.
     */
    public EntityException() {
    }


    /**
     * Constructs an instance of <code>SceneException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public EntityException(String msg) {
        super(msg);
    }

    public EntityException(Throwable throwable) {
        super(throwable);
    }

    public EntityException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
