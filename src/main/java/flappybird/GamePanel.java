/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import static flappybird.Bird.RAD;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class GamePanel extends JPanel {
    
    private Bird bird;
    private ArrayList<EnhancedRectangle> rects;
    private FlappyBird fb;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(0, 158, 158);
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength, otherPipe,predator,fire;
    private Skeleton skeleton;
    

    public GamePanel(FlappyBird fb, Bird bird, Skeleton skeleton, ArrayList<EnhancedRectangle> rects) {
        this.fb = fb;
        this.bird = bird;
        this.skeleton = skeleton;
        this.rects = rects;
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);
        
        try {
        	pipeHead = ImageIO.read(new File("78px-Pipe.png"));
        	pipeLength = ImageIO.read(new File("pipe_part.png"));
                otherPipe = ImageIO.read(new File("download.png"));
                predator = ImageIO.read(new File("bird3.png"));
                fire = ImageIO.read(new File("fire.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(bg);
        g.fillRect(0,0,FlappyBird.WIDTH,FlappyBird.HEIGHT);
        bird.update(g);
        //g.setColor(Color.RED);
        //one pipe for each rects
        for(EnhancedRectangle r : rects){ 
            drawRectangle(r.r, g);
            
            if (r.flyingPredator != null) {
                r.flyingPredator.update(g, r.r);
                continue;
            } else if (r.isSkeleton) {
                skeleton.update(g, r.r);
                continue;
            }
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            //g2d.fillRect(r.x, r.y, r.width, r.height);
            AffineTransform old = g2d.getTransform();
            g2d.translate(r.r.x+PIPE_W/2, r.r.y+PIPE_H/2);
            
            
            if(r.r.y < FlappyBird.HEIGHT/2) {
                g2d.translate(0, r.r.height);
                g2d.rotate(Math.PI);
            }
            
            int height = r.r.height;
            int sub=0;
            if (r.isBird||r.isFire) {
                sub = 75;
                height -=sub;
            }
            
            int correction = r.isTop ? PIPE_H : 0;

            if(!r.isRed){
                g2d.drawImage(pipeHead, -PIPE_W/2, -PIPE_H/2+sub+correction, PIPE_W, PIPE_H, null);
                g2d.drawImage(pipeLength, -PIPE_W/2, PIPE_H/2+sub+correction, PIPE_W, height-correction, null);
            }
            else{
                g2d.drawImage(otherPipe, -PIPE_W/2, -PIPE_H/2+sub+correction, PIPE_W, height+correction, null);
            }            
            g2d.setTransform(old);  
            if (r.isBird) {
                g.drawImage(predator, Math.round(r.r.x-RAD)+20,Math.round(r.r.y-RAD+50),2*RAD,2*RAD, null); 
            }
            else if(r.isFire){
                g.drawImage(fire, Math.round(r.r.x-RAD)+20,Math.round(r.r.y-RAD+50),2*RAD,2*RAD, null);
            }
        }
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: "+fb.getScore(), 10, 20);
        
        if(fb.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0,0,0,170));
            g.drawString("PAUSED", FlappyBird.WIDTH/2-100, FlappyBird.HEIGHT/2-100);
            g.drawString("GET 20 TO WIN THE GAME", FlappyBird.WIDTH/2-300, FlappyBird.HEIGHT/2+50);
        }
    }
    
    private void drawRectangle(Rectangle r, Graphics g) {
        if (true) return;
        Graphics2D g2;
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2;
        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRect(r.x, r.y, r.width, r.height);
        g2d.setStroke(oldStroke);        
    }
}