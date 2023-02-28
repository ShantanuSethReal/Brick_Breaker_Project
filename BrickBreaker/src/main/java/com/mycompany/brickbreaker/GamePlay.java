/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.brickbreaker;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author chinm
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    
     private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }
     //Paint is a method of JPanel 
     public void paint(Graphics g) {
        //Code to Paint the Jframe Black from (1,1) to(692+1,592+1)
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        //Draw the map using Map Generator class- Draw is a method of MapGenerator
        map.draw((Graphics2D) g);
        //Code to paint boundary of Jframe yellow- can be removed if not needed
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        //Code to show live score at the upper right corner
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);
        //Code to show yellow slider at the bottom
        g.setColor(Color.yellow);
        g.fillRect(playerX, 550, 100, 8);
        //Code to show ball using ball Position Cordinates
        g.setColor(Color.GREEN);
        g.fillOval(ballposX, ballposY, 20, 20);
        //Condition I - Game Over
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        //Condition II- All Bricks broken
        if(totalbricks == 0){
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString(" Finished Stage I: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();
        if (play) {
            //Code to reverse direction of ball as soon as it hits slider at bottom
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }
            //Syntax to break out 
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;
                        //Declaring two rectangles -one for ball and one for brick to check whether they are intersecting
                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;
                        //Checking if ball intersects with brick at any instant
                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score += 5;
                            //Condition -Ball can hit brick in X boundary|| else Y boundary--
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }


                }
            }
            //No Intersection then continue game
            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

       }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


        }

        public void moveRight ()
        {
            play = true;
            playerX += 20;
        }
        public void moveLeft ()
        {
            play = true;
            playerX -= 20;
        }
        
    
    
}

