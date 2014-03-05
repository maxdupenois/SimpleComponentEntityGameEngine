/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.defaultcomponents;

import com.threevoid.game.engine.RenderComponent;
import com.threevoid.game.geometry.Vector2f;
import com.threevoid.game.resources.images.ImageLoader;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 *
 * @author mpd209
 */
public class RenderImageComponent extends RenderComponent{
    private BufferedImage img;
    
    public RenderImageComponent(String id, String imagekey){
        super(id);
        this.img = ImageLoader.get().getStaticImage(imagekey);
    }
    @Override
    public void render(Graphics2D g) {
        Vector2f pos = getOwner().getPosition();
        float scale = getOwner().getScale();
        float width = getOwner().getWidth();
        float height = getOwner().getHeight();
        g.drawImage(this.img, pos.getXi(), pos.getYi(), (int)(width*scale), (int)(height*scale), null);
        
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
    public void setImage(String imagekey) {
        this.img = ImageLoader.get().getStaticImage(imagekey);
    }


   

}
