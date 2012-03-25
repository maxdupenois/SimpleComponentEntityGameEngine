/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.resources.images;


/**
 *
 * @author mpd209
 */
public class ImageException extends Exception {

    /**
     * Creates a new instance of <code>ClipException</code> without detail message.
     */
    public ImageException() {
    }


    /**
     * Constructs an instance of <code>ClipException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ImageException(String msg) {
        super(msg);
    }

    public ImageException(Throwable throwable) {
        super(throwable);
    }

    public ImageException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
