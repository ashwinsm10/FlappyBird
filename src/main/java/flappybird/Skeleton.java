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
public class Skeleton {
    private Image img;
    public Skeleton() {
        try {
            img = ImageIO.read(new File("skeleton.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update(Graphics g, Rectangle r) {        
        g.drawImage(img, r.x,r.y,r.width, r.height, null);
        
    }    
}