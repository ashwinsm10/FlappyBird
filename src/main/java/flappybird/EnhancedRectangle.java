/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.Rectangle;

/**
 *
 * @author ashwinmurthy
 */
public class EnhancedRectangle {
    public Rectangle r;
    boolean isRed;
    boolean isBird;
    boolean isFire;
    boolean isSkeleton;
    FlyingPredator flyingPredator = null;
    boolean isTop;
    
    public EnhancedRectangle(boolean isTop, Rectangle r, boolean isRed, 
            boolean isBird, boolean isFire, FlyingPredator flyingPredator, boolean isSkeleton) {
        this.isTop = isTop;
        this.r = r;
        this.isRed = isRed;
        this.isBird = isBird;
        this.isFire = isFire;
        this.isSkeleton = isSkeleton;
        this.flyingPredator = flyingPredator;
    }
}
