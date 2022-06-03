package com.matthewadev.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private Vector3 pos;
    private float height = 2.0f;
    private Vector3 vel;
    private PerspectiveCamera camera;

    public Player(float x, float y, float z, PerspectiveCamera camera){
        this.pos = new Vector3(x,y,z);
        this.vel = new Vector3(0,0,0);
        this.camera = camera;
    }

    public void setPos(float x, float y, float z){
        this.pos.set(x,y,z);
        this.camera.position.set(x,y,z);
    }
    public void setPos(Vector3 pos){
        this.pos.set(pos);
        this.camera.position.set(pos);
    }
    public Vector3 getPos(){
        return pos;
    }
    public void setVel(Vector3 vec){
        vel.set(vec);
    }
    public Vector3 getVel(){
        return vel;
    }

}
