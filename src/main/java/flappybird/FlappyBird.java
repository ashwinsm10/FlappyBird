
package flappybird;

import flappybird.Bird;
import flappybird.GamePanel;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class FlappyBird implements ActionListener, KeyListener {
    
    public static final int FPS = 60, WIDTH = 640, HEIGHT = 480;
    
    private Bird bird;
    private Skeleton skeleton;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<EnhancedRectangle> rects;
    private int time, scroll;
    private Timer t;
    private Clip clip;
    private int framePos;
    
    private boolean paused;
    private boolean coloring = true;
    
    public void go() {
        frame = new JFrame("Flappy Bird");
        bird = new Bird();
        skeleton = new Skeleton();
        
        rects = new ArrayList<EnhancedRectangle>();
        panel = new GamePanel(this, bird, skeleton, rects);
        frame.add(panel);
        
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        
        paused = true;
        
        try {
            File fs = new File("1953.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(fs);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            framePos = clip.getFramePosition();
        }
        catch(Exception ex) {
        }
                
        //every 16ms, 1s = 1000ms
        t = new Timer(1000/FPS, this);
        t.start();
    }
    public static void main(String[] args) throws Exception {
        new FlappyBird().go();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if(!paused) {
            // the bird
            bird.physics();
            
            if(scroll % 90 == 0) {
                Rectangle r = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, 
                        (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT));
                int h2 = (int) ((Math.random()*HEIGHT)/5f + (0.2f)*HEIGHT);
                double randoms = Math.random();
                
                Rectangle r2 = new Rectangle(WIDTH, HEIGHT-h2, GamePanel.PIPE_W, h2);

                boolean isPredator = randoms >= .80 ? true : false;
                boolean isFire = randoms >= 0.6 && randoms < 0.80 ? true : false;
                FlyingPredator flyingPredator = randoms >= 0.50 && randoms < 0.6 ? new FlyingPredator() : null;
                boolean isSkeleton = randoms >= 0.4 && randoms < 0.50 ? true : false;
                
                if (flyingPredator != null) {
                    r2 = new MovingRectangle(r2.x, r2.y, r2.width, r2.height, r.y + r.height);
                }

                rects.add(new EnhancedRectangle(true, r, coloring, false,false, null, isSkeleton));
                rects.add(new EnhancedRectangle(false, r2, !coloring, isPredator && h2 > 100, isFire && h2 > 100, flyingPredator, false));
                coloring = !coloring;
            }
            ArrayList<EnhancedRectangle> toRemove = new ArrayList<EnhancedRectangle>();
            boolean game = true;
            for(EnhancedRectangle r : rects) {
                r.r.x-=3;
                if(r.r.x + r.r.width <= 0) {
                    toRemove.add(r);
                }
                if(r.r.contains(bird.x, bird.y)) {
                    if(getScore()==20){
                        JOptionPane.showMessageDialog(frame, "You won!");
                        JOptionPane.showMessageDialog(frame, "Your score: " + getScore() + "!");
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "You lose!\n"+"Your score was: "+getScore()+".");
                    }
                    game = false;
                }
            }
            rects.removeAll(toRemove);
            time++;
            scroll++;

            if(bird.y > HEIGHT || bird.y+bird.RAD < 0) {
                game = false;
            }

            if(!game) {
                rects.clear();
                bird.reset();
                
                time = 0;
                scroll = 0;
                paused = true;
            }
        }
        else {
            
        }
    }
    
    public int getScore() {
        return time/100;
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W) {
            bird.jump();
            playSound();
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
            paused = !paused;
        }
        
           
        
    }
    public void keyReleased(KeyEvent e) {
        
    }
    public void keyTyped(KeyEvent e) {
        
    }
    
    public boolean paused() {
        return paused;
    }
    
    public void playSound() {
        try {
            clip.setFramePosition(framePos);
            clip.start(); 
        }
        catch (Exception ex) {
            
        }
    }
}