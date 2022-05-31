package com.matthewadev.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;

public class Player {
    private float x;
    private float y;
    private float z;
    private float height = 2.0f;
    private float velx;
    private float vely;
    private float velz;
    private PerspectiveCamera camera;

    public Player(float x, float y, float z, PerspectiveCamera camera){
        this.x = x;
        this.y = y;
        this.z = z;
        velx = 0;
        vely = 0;
        velz = 0;
        this.camera = camera;
    }

}
