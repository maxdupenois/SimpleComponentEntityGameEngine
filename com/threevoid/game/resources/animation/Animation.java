/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threevoid.game.resources.animation;

import com.threevoid.game.resources.images.ImageLoader;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author mpd209
 */
public class Animation {
    private static HashMap<String, Animation> animations = new HashMap<String, Animation>();
    private BufferedImage[] animation;
    private long[] perFrame;
    private boolean looping;
    private String key;

    public static Animation create(String key, String[] staticImageKeys, long[] perframe, boolean looping){
        if(staticImageKeys.length<1){
            throw new IllegalArgumentException("Animation must have at least one image");
        }
        if(staticImageKeys.length!=perframe.length){
            throw new IllegalArgumentException("Length of image array and timing array must match.");
        }
        BufferedImage[] animation = new BufferedImage[staticImageKeys.length];
        for(int i=0; i< staticImageKeys.length;i++){
            animation[i] = ImageLoader.get().getStaticImage(staticImageKeys[i]);
        }
        Animation animationObject = new Animation();
        animationObject.animation = animation;
        animationObject.perFrame = perframe;
        animationObject.looping = looping;
        animationObject.key = key;
        animations.put(key, animationObject);
        return animationObject;
    }
    public static AnimationInstance getAnimationInstance(String key){
        return new AnimationInstance(animations.get(key));
    }
    private Animation(){
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    public static class AnimationInstance{
        private Animation animation;
        private boolean running;
        private boolean started;
        private boolean paused;
        private long timeframelastupdated = -1;
        private int currentFrame;
        private AnimationInstance(Animation animation){
            this.currentFrame = 0;
            this.animation = animation;
            this.timeframelastupdated = -1;
            this.running = false;
        }
        public BufferedImage getFrame(){
            if(paused){
                return animation.animation[this.currentFrame];
            }
            long currentTime = System.nanoTime();
            if(timeframelastupdated!=-1){
                long diff = currentTime-this.timeframelastupdated;
                if(diff>=animation.perFrame[this.currentFrame]){
                    this.timeframelastupdated = currentTime;
                    next();
                }
            }else{
                this.started = true;
                this.running = true;
                this.timeframelastupdated = currentTime;
            }
            return animation.animation[this.currentFrame];
        }
        public void pause(){
            this.timeframelastupdated = -1;
            this.paused = true;
        }
        public void resume(){
            if(paused){
                paused = false;
            }
        }
        public void stop(){
            this.started = false;
            this.currentFrame = 0;
            this.timeframelastupdated = -1;
            this.paused = true;
        }
        public void reset(){
            this.started = false;
            this.currentFrame = 0;
            this.timeframelastupdated = -1;
            this.paused = false;
        }

        private void next(){
            if(this.currentFrame+1 == animation.animation.length){
                if(animation.looping){
                    this.currentFrame= 0;
                }else{
                    this.running = false;
                }
            }else{
                this.currentFrame++;
            }
        }



        /**
         * @return the running
         */
        public boolean isRunning() {
            return running;
        }

        /**
         * @return the currentFrame
         */
        public int getCurrentFrame() {
            return currentFrame;
        }

        /**
         * @param currentFrame the currentFrame to set
         */
        public void setCurrentFrame(int currentFrame) {
            this.currentFrame = currentFrame;
        }
        /**
         * @return the started
         */
        public boolean isStarted() {
            return started;
        }

    }

    

  

}
