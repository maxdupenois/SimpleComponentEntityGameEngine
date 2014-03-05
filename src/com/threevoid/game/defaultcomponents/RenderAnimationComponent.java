/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.defaultcomponents;

import com.threevoid.game.engine.RenderComponent;
import com.threevoid.game.geometry.Vector2f;
import com.threevoid.game.resources.animation.Animation;
import com.threevoid.game.resources.animation.Animation.AnimationInstance;
import java.awt.Graphics2D;

/**
 *
 * @author mpd209
 */
public class RenderAnimationComponent extends RenderComponent{
    private AnimationInstance animation;
    
    public RenderAnimationComponent(String id, String animationKey){
        super(id);
        this.animation = Animation.getAnimationInstance(animationKey);
    }
    @Override
    public void render(Graphics2D g) {
        Vector2f pos = getOwner().getPosition();
        float scale = getOwner().getScale();
        float width = getOwner().getWidth();
        float height = getOwner().getHeight();
        g.drawImage(this.animation.getFrame(), pos.getXi(), pos.getYi(), (int)(width*scale), (int)(height*scale), null);
        
//        Stroke origs = g.getStroke();
//        Color orig = g.getColor();
//        g.setStroke(new BasicStroke(3f));
//        g.setColor(Color.blue);
//        g.drawRect(pos.getXi(), pos.getYi(), (int)(width*scale), (int)(height*scale));
//        g.setColor(orig);
//        g.setStroke(origs);
    }

    /**
     * @param img the img to set
     */
    public void pause() {
        this.animation.pause();
    }

    public void stop() {
        this.animation.stop();
    }

    public void resume() {
        this.animation.stop();
    }
    public boolean isRunning() {
        return this.animation.isRunning();
    }
    public boolean isStarted() {
        return this.animation.isStarted();
    }
    public void reset() {
        this.animation.reset();
    }

}
