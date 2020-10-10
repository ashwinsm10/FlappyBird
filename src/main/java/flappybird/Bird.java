/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author User
 */
public class Bird {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image img;
    public Bird() {
        x = FlappyBird.WIDTH/2;
        y = FlappyBird.HEIGHT/2;
        try {
            img = ImageIO.read(new File("sticker,375x360.u2.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void physics() {
        x+=vx;
        y+=vy;
        vy+=0.5f;
    }
    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        drawRectangle(Math.round(x-RAD), Math.round(y-RAD), 2*RAD, 2*RAD, g);
        g.drawImage(img, Math.round(x-RAD),Math.round(y-RAD),2*RAD,2*RAD, null);
    }
    public void jump() {
        vy = -8;
    }
    
    public void reset() {
        x = 640/2;
        y = 480/2;
        vx = vy = 0;
    }
    
    private void drawRectangle(int x, int y, int width, int height, Graphics g) {
        if (true) return;
        Graphics2D g2;
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRect(x, y, width, height);
        g2d.setStroke(oldStroke);        
    }
    
}