package com.matthewadev.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class Player {
    private Vector3 pos;
    private float height = 2.0f;
    private float width = 1.5f;
    private Vector3 vel;
    public boolean isFlying = true;
    public boolean isOnGround = false;

    public Player(float x, float y, float z){
        this.pos = new Vector3(x,y,z);
        this.vel = new Vector3(0,0,0);
        updateCam();
    }
    public void updatePlayerPos(){
        this.pos.add(this.vel);
        updateCam();
    }
    public void reduceVelocity(){
        if(isFlying) {
            this.vel.x *= 0.9;
            this.vel.z *= 0.9;
            this.vel.y *= 0.5;
        }
    }

    public void setPos(float x, float y, float z){
        this.pos.set(x,y,z);
        updateCam();
    }
    public void setPos(Vector3 pos){
        updateCam();
        this.pos.set(pos);
    }
    public void addPos(Vector3 pos){
        this.pos.add(pos);
        updateCam();
    }
    public void addVel(Vector3 vel){
        this.vel.add(vel);
        if(vel.x > 5){
            vel.x = 5;
        }else if(vel.x < -5){
            vel.x = -5;
        }
        if(vel.y > 2.5){
            vel.y = 2.5f;
        }else if(vel.y < -2.5){
            vel.y = -2.5f;
        }
        if(vel.z > 5){
            vel.z = 5;
        }else if(vel.z < -5){
            vel.z = -5;
        }
    }
    public void addVel(float x, float y, float z){
        this.vel.x += x;
        this.vel.y += y;
        this.vel.z += z;
    }
    public void updateCam(){
        Game.camera.position.set(this.pos);
        Game.camera.update();
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
    public void setVel(float x, float y, float z){
        vel.x = x;
        vel.y = y;
        vel.z = z;
    }
    public Vector3 getVel(){
        return vel;
    }

}
