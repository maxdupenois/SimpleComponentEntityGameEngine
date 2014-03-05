/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.engine;

import java.awt.Graphics2D;

/**
 *
 * @author mpd209
 */
public abstract class RenderComponent extends Component{

    public RenderComponent(String id){
        super(id);
    }

    public abstract void render(Graphics2D g);

    @Override
    public void update(Input input, float updatesPerSecond) throws EntityException {
    }
}
