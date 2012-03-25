/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.defaultcomponents;

import com.threevoid.game.engine.RenderComponent;
import com.threevoid.game.geometry.Vector2f;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author mpd209
 */
public class LoadingBarComponent extends RenderComponent{
    private Color border;
    private Color fill;
    private float value;
    public LoadingBarComponent(String id, Color border, Color fill){
        super(id);
        this.border = border;
        this.fill = fill;
        this.value = 0;
    }
    @Override
    public void render(Graphics2D g) {
        Vector2f pos = getOwner().getPosition();
        float scale = getOwner().getScale();
        float width = getOwner().getWidth();
        float height = getOwner().getHeight();
        float drawWidth = width*scale;
        float drawHeight = height*scale;
        float spacer = 10f;
        Rectangle2D b = new Rectangle2D.Float(pos.x, pos.y,
                drawWidth, drawHeight);
        float fillWidth = drawWidth * this.getValue();

        Rectangle2D f = new Rectangle2D.Float(pos.x+spacer, pos.y+spacer,
                fillWidth-(spacer*2f), drawHeight-(spacer*2f));
        Color orig = g.getColor();
        Stroke origs = g.getStroke();
        g.setStroke(new BasicStroke(3f));
        g.setColor(border);
        g.draw(b);
        g.setColor(fill);
        g.fill(f);
        g.setColor(orig);
        g.setStroke(origs);

    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        if(value>1)value=1f;
        if(value<0)value=0f;
        this.value = value;
    }

}
