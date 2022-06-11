package com.matthewadev.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private Vector3 pos;
    private float height = 2.0f;
    private float width = 1.5f;
    private Vector3 vel;

    public Player(float x, float y, float z){
        this.pos = new Vector3(x,y,z);
        this.vel = new Vector3(0,0,0);
        Game.camera.position.set(x,y,z);
        Game.camera.update();
    }

    public void setPos(float x, float y, float z){
        Game.camera.position.set(x,y,z);
        Game.camera.update();
        this.pos.set(x,y,z);
    }
    public void setPos(Vector3 pos){
        Game.camera.position.set(pos);
        Game.camera.update();
        this.pos.set(pos);
    }
    public Vector3 getPos(){
        return pos;
    }
    public float getX(){
        return pos.x;
    }
    public float getY(){
        return pos.y;
    }
    public float getZ(){
        return pos.z;
    }
    public void setVel(Vector3 vec){
        vel.set(vec);
    }
    public Vector3 getVel(){
        return vel;
    }

}
