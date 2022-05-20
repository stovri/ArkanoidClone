package com.github.grhscompsci2.java2DGame;

import java.awt.event.KeyEvent;

public class Frog extends Actor {
    private final static String upImg = "images/frog_up.png";
    private final static String downImg = "images/frog_down.png";
    private final static String leftImg = "images/frog_left.png";
    private final static String rightImg = "images/frog_right.png";

    public Frog() {
        super(upImg, 112, 244);
    }

    public void act() {
        int oldY = getY();
        if (oldY + getDY() > 251) {
            setDY(0);
        }
        int oldX = getX();
        if (oldX + getDX() > 218 || oldX + getDX() < 6) {
            setDX(0);
        }
        super.act();
    }

    /**
     * Adds on to the Actor keyPressed method by updating the sprite to be facing
     * the
     * direction it is travelling.
     * 
     * @param e the KeyEvent
     */
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        // Get which key was pressed.
        int key = e.getKeyCode();

        // going left?
        if (key == KeyEvent.VK_LEFT) {
            setImage(leftImg);
        }
        // going right?
        if (key == KeyEvent.VK_RIGHT) {
            setImage(rightImg);
        }

        // going down?
        if (key == KeyEvent.VK_DOWN) {
            setImage(downImg);
        }

        // going up?
        if (key == KeyEvent.VK_UP) {
            setImage(upImg);
        }
    }
}