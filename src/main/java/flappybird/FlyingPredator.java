/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class FlyingPredator {
    public float vx, vy;
    public static final int RAD = 25;
    private Image img;
    private int sign = -1;
    public FlyingPredator() {
        try {
            img = ImageIO.read(new File("kindpng.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update(Graphics g, Rectangle r) {
        
        MovingRectangle obj = (MovingRectangle) r;

        r.x+=vx;
        r.y+=2*sign;
        
        if (r.y <= obj.originalHeight) {
            sign *= -1;
        }
        else if (r.y >= obj.originalY) {
            sign *= -1;            
        }
        
        g.setColor(Color.BLACK);
        g.drawImage(img, Math.round(r.x+-RAD),Math.round(r.y-RAD),2*RAD,2*RAD, null);
    }
    
    public void reset() {
        vx = vy = 0;
        sign = -1;
    }
}