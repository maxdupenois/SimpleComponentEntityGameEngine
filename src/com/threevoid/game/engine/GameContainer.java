/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threevoid.game.engine;

import java.awt.Graphics;

/**
 *
 * @author Max
 */
public interface GameContainer {
  
    public int getAvailableWidth();
    
    public int getAvailableHeight();

    
    public void repaint();

    public void paint(Graphics g);

    public void requestFocus();

}
