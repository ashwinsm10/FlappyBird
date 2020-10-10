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
public class MovingRectangle  extends Rectangle {
    int originalHeight;
    int originalY;
    
    public MovingRectangle(int x, int y, int width, int height, int otherRectHeight) {
        super(x, y+height-25, width, 75);
        originalHeight = otherRectHeight + 50;
        originalY = y+height-25;
    }
    
}
