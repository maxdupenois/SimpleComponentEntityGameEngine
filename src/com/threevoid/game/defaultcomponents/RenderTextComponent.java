/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.defaultcomponents;

import com.threevoid.game.engine.RenderComponent;
import com.threevoid.game.engine.Renderer;
import com.threevoid.game.geometry.Vector2f;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author mpd209
 */
public class RenderTextComponent extends RenderComponent{
    private String text;
    private String[] lines;
    private Font font;
    private Color colour;
    private TextPositionH positionHoriz;
    private TextPositionV positionVert;
    public static enum TextPositionV{
        TOP,
        CENTER,
        BOTTOM
    }
    public static enum TextPositionH{
        LEFT,
        CENTER,
        RIGHT
    }
    public RenderTextComponent(String id, String defaultText, Font font, Color colour){
        this(id, defaultText, font, colour, TextPositionH.LEFT, TextPositionV.TOP);
    }
    public RenderTextComponent(String id, String defaultText, Font font, Color colour, TextPositionH positionHoriz, TextPositionV positionVert){
        super(id);
        this.font = font;
        this.text =defaultText;
        this.lines = this.text.split("\\n");
        this.colour = colour;
        this.positionHoriz = positionHoriz;
        this.positionVert = positionVert;
        
    }
    private Vector2f getDrawPosition(){
        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
        int height = fm.getAscent()+fm.getDescent(); //Don't want leading
        int width = getTextWidth();
        float x;
        float y;
        if(this.positionHoriz==TextPositionH.CENTER){
            x = getOwner().getPosition().x-width/2;
        }else if(this.positionHoriz==TextPositionH.RIGHT){
            x = getOwner().getPosition().x-width;
        }else{
            x = getOwner().getPosition().x;
        }
        if(this.positionVert==TextPositionV.CENTER){
            y = getOwner().getPosition().y+height/2;
        }else if(this.positionVert==TextPositionV.BOTTOM){
            y = getOwner().getPosition().y-fm.getDescent();
        }else{
            y = getOwner().getPosition().y+height;
//            System.out.println("HEIGHT: "+height);
        }
        return new Vector2f(x, y);
    }

    public int getLeading(){
        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
        return fm.getLeading();
    }

    public int getTextHeight(){
        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
        return fm.getHeight();
    }
    public int getDescent(){
        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
        return fm.getDescent();

    }
    public int getAscent(){
        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
        return fm.getAscent();
    }
    public int getTextWidth(){
        if(this.lines.length==1){
            return getTextWidth(text);
        }else{
            int maxWidth = 0;
            int width;
            for(String line : lines){
                width = getTextWidth(line);
                if(width>maxWidth) maxWidth = width;
            }
            return maxWidth;
        }
    }
    public int getTextWidth(String t){
        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
        return fm.stringWidth(t);
    }

    public Vector2f getTextCenter(){
        Vector2f pos = getDrawPosition();
        return new Vector2f(pos.x + (getTextWidth()/2f), pos.y - (getTextHeight()/2f));
    }

    @Override
    public void render(Graphics2D g) {
        Vector2f pos = getDrawPosition();
        
        Font origf = g.getFont();
        Color origc = g.getColor();
        g.setColor(colour);
        g.setFont(font);
        if(this.lines.length==1){
            g.drawString(text, pos.getX(), pos.getY());
        }else{
            float y = pos.getY();
            for(String line : lines){
//                System.out.println(line+" at "+pos.getX()+", "+ y);
                g.drawString(line, pos.getX(), y);
                y += Renderer.get().getFontMetrics(this.font).getHeight();
            }
        }
//        LineMetrics lm = this.font.getLineMetrics(text, g.getFontRenderContext());
//        FontMetrics fm = Renderer.get().getFontMetrics(this.font);
//        int height = (int)lm.getAscent(); //Don't want leading
//        int width = fm.stringWidth(text);
//        Rectangle2D box = font.getStringBounds(text, g.getFontRenderContext());
////        g.drawRect((int)getOwner().getPosition().x-width/2, (int)getOwner().getPosition().y-height, width, height);
//        g.setColor(Color.ORANGE);

//        float x0 = (float) (getOwner().getPosition().x + (box.getWidth() - width)/2f);
//        float y0 = (float) (getOwner().getPosition().y + (box.getHeight() - lm.getHeight())/2f + lm.getAscent());


//        g.draw(new Rectangle2D.Float(x0, y0, (float)box.getWidth(), (float)box.getHeight()));
        g.setColor(origc);
        g.setFont(origf);
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
        this.lines = this.text.split("\\n");
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the colour
     */
    public Color getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }


   

}
