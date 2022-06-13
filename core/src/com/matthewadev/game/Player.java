package com.matthewadev.game;

import com.badlogic.gdx.math.Vector3;
import com.matthewadev.physics.Physics;

import java.util.ArrayList;

public class Player {
    private Vector3 pos;
    private float height = 1.8f;
    private float width = 1.5f;
    private Vector3 vel;
    public boolean isFlying = true;
    public boolean isOnGround = false;
    private float maxXvel = 5f;
    private float maxYvel = 2.5f;
    private float maxZvel = 5f;

    public Player(float x, float y, float z){
        this.pos = new Vector3(x,y,z);
        this.vel = new Vector3(0,0,0);
        updateCam();
    }
    public void updatePlayerPos(){
        if(isColliding() && !Game.player.isFlying){
            this.vel.set(0,0,0);
        }else {
            dealWithColliding();
/*            this.pos.add(this.vel);
            updateCam();*/
        }
    }
    public void dealWithColliding(){
        boolean hasCollided = false;
        for (int addx = -1; addx < 2; addx += 2) {
            for (int addy = -1; addy < 2; addy += 2) {
                for (int addz = -1; addz < 2; addz += 2) {
                    Vector3 origin = new Vector3(pos.x + (width / 2f) * addx, pos.y + (height) * addy, pos.z + (width / 2f) * addz);
                    Vector3[] collision = Physics.calcCols(origin, vel, 0f, false, false, 100);
                    if(collision != null){
                        Vector3 normal = collision[1];
                        if(normal.x != 0){
                            pos.z = collision[0].z + (width / 2f) * Physics.signum(collision[1].z) + (width / 2f) * collision[1].z;
                            pos.y = collision[0].y + (height) * Physics.signum(collision[1].y) - (height);
                            vel.x = 0;
                        }
                        if(normal.y != 0){
                            pos.x = collision[0].x + (width / 2f) * Physics.signum(collision[1].x) + (width / 2f) * collision[1].x;
                            pos.z = collision[0].z + (width / 2f) * Physics.signum(collision[1].z) + (width / 2f) * collision[1].z;
                            vel.y = 0;
                        }
                        if(normal.z != 0){
                            pos.x = collision[0].x + (width / 2f) * Physics.signum(collision[1].x) + (width / 2f) * collision[1].x;
                            pos.y = collision[0].y + (height) * Physics.signum(collision[1].y) - (height);
                            vel.z = 0;
                        }
                        hasCollided = true;
                    }
                    //cornerCollisions.add(Physics.calcCols(origin, vel, 0f, false));
                }
            }
        }
        if(!hasCollided){
            this.pos.add(this.vel);
            updateCam();
        }
    }
    public boolean isColliding(){ // checks if hit box is inside any blocks
        float x = pos.x;
        float y = pos.y;
        float z = pos.z;
        return Game.crenderer.getBlock(x - width / 2f, y - height, z - width / 2f) != null ||
                Game.crenderer.getBlock(x - width / 2f, y, z - width / 2f) != null ||
                Game.crenderer.getBlock(x + width / 2f, y - height, z - width / 2f) != null ||
                Game.crenderer.getBlock(x + width / 2f, y, z - width / 2f) != null ||
                Game.crenderer.getBlock(x - width / 2f, y - height, + width / 2f) != null ||
                Game.crenderer.getBlock(x - width / 2f, y, z + width / 2f) != null ||
                Game.crenderer.getBlock(x + width / 2f, y - height, z + width / 2f) != null ||
                Game.crenderer.getBlock(x + width / 2f, y, z + width / 2f) != null;
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
        if(vel.x > maxXvel){
            vel.x = maxXvel;
        }else if(vel.x < -maxXvel){
            vel.x = -maxXvel;
        }
        if(vel.y > maxYvel){
            vel.y = maxYvel;
        }else if(vel.y < -maxYvel){
            vel.y = -maxYvel;
        }
        if(vel.z > maxZvel){
            vel.z = maxZvel;
        }else if(vel.z < -maxZvel){
            vel.z = -maxZvel;
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
    public void setVelX(float x){
        if(x > maxXvel){
            vel.x = maxXvel;
            return;
        }else if(x < -maxXvel){
            vel.x = -maxXvel;
            return;
        }
        vel.x = x;
    }
    public void setVelY(float y){
        if(y > maxYvel){
            vel.y = maxYvel;
            return;
        }else if(y < -maxYvel){
            vel.y = -maxYvel;
            return;
        }
        vel.y = y;
    }
    public void setVelZ(float z){
        if(z > maxZvel){
            vel.z = maxZvel;
            return;
        }else if(z < -maxZvel){
            vel.z = -maxZvel;
            return;
        }
        vel.z = z;
    }
    public Vector3 getVel(){
        return vel;
    }

}