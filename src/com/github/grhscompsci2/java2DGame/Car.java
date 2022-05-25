package com.github.grhscompsci2.java2DGame;

public class Car extends Actor {
    private final static String leftImg = "images/car1_left.png";
    private final static String rightImg = "images/car1_right.png";

    public Car() {
        super(leftImg, Utility.gameWidth/2, Utility.gameHeight-40);
        setDX(-1 * getSpeed());

    }
}
