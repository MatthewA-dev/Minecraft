package com.matthewadev.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.physics.Physics;

import java.util.ArrayList;

public class Player {
    private Vector3 pos;
    public float height = 1.8f;
    public float width = 0.8f;
    private Vector3 vel;
    public boolean isFlying = true;
    public boolean isOnGround = false;
    private float maxXvel = 4f;
    private float maxYvel = 1000f;
    private float maxZvel = 4f;

    public Player(float x, float y, float z){
        this.pos = new Vector3(x,y,z);
        this.vel = new Vector3(0,0,0);
        updateCam();
    }
    public void updatePlayerPos(){
/*        if(isColliding() && !Game.player.isFlying){
            this.vel.set(0,0,0);
        }else {
            dealWithColliding();
*//*            this.pos.add(this.vel);
            updateCam();*//*
        }*/
        manageVelocities();
        dealWithColliding();
    }
    private void manageVelocities(){
        boolean shouldBeOnGround = false;
        // should be on ground
        Vector3 origin;
        // dealing with all bottom points
        Vector3 v = vel.cpy().scl(Gdx.graphics.getDeltaTime());
        v.x *= 3;
        v.z *= 3;
        for (int addx = -1; addx < 2; addx += 2) {
            for (float addy = 0; addy <= 1; addy += 0.5) {
                for (int addz = -1; addz < 2; addz += 2) {
                    origin = new Vector3(pos.x + (width / 2f) * addx, pos.y - (height) * addy, pos.z + (width / 2f) * addz);
                    if (Physics.calcCols(origin, new Vector3(0f, -0.02f, 0f), 0.01f, false, false, 100) != null) {
                        if (Physics.calcCols(origin.cpy().add(v), new Vector3(0f, -0.02f, 0f), 0.01f, false, false, 100) != null) {
                            shouldBeOnGround = true;
                        }
                    }
                    Vector3[] collision = Physics.calcCols(origin, v, 0f, false, false, 100);
                    try {
                        System.out.println(collision[0]);
                        // managing collisions
                        if (collision[1].x != 0) {
                            pos.x = (collision[0].x + 0.01f * collision[1].x) - ((width / 2f) * (addx));
                            pos.y = collision[0].y + height * addy;
                            pos.z = collision[0].z - (width / 2f) * (addz);
                            vel.x = 0;
                        }
                        if (collision[1].y != 0) {
                            pos.x = collision[0].x - (width / 2f) * addx;
                            pos.z = collision[0].z - (width / 2f) * addz;
                            //System.out.println("BEFORE: " + pos.y + " " + collision[0].y + " " + origin.y);
                            pos.y = (float) (collision[0].y + 0.02 * collision[1].y + (height) * addy);
                            // System.out.println(pos.y);
                            shouldBeOnGround = true;
                            vel.y = 0;
                        }
                        if (collision[1].z != 0) {
                            pos.x = collision[0].x - ((width / 2f) * (addx));
                            pos.y = collision[0].y + height * addy;
                            pos.z = (collision[0].z + 0.01f * collision[1].z) - (width / 2f) * (addz);
                            vel.z = 0;
                        }

                    } catch (NullPointerException e) {
                    }
                }
            }
        }
        isOnGround = shouldBeOnGround;
    }
    public void dealWithColliding(){
        //System.out.println("VEL " + vel.y);
        if(!isFlying && !isOnGround) {
            addVel(0, -9.81f * Gdx.graphics.getDeltaTime(), 0);
        }else if(isOnGround){
            setVelY(0f);
        }
        if(isFlying) {
            this.vel.x *= 0.9;
            this.vel.z *= 0.9;
            this.vel.y *= 0.8;
        }else{
/*            this.vel.x *= Math.pow(0.8,60*Gdx.graphics.getDeltaTime());
            this.vel.z *= Math.pow(0.8,60*Gdx.graphics.getDeltaTime());*/
        }

        Vector3 v = this.vel.cpy().scl(Gdx.graphics.getDeltaTime());
        v.x *= 3;
        v.z *= 3;
        this.pos.add(v);
/*        if(isColliding()){
            this.pos.sub(v);
        }*/
        updateCam();
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
        if(this.vel.x > maxXvel){
            this.vel.x = maxXvel;
        }else if(this.vel.x < -maxXvel){
            this.vel.x = -maxXvel;
        }
        if(this.vel.y > maxYvel){
            this.vel.y = maxYvel;
        }else if(this.vel.y < -maxYvel){
            this.vel.y = -maxYvel;
        }
        if(this.vel.z > maxZvel){
            this.vel.z = maxZvel;
        }else if(this.vel.z < -maxZvel){
            this.vel.z = -maxZvel;
        }
        manageVelocities();
    }
    public void addVel(float x, float y, float z){
        this.vel.x += x;
        this.vel.y += y;
        this.vel.z += z;
        if(this.vel.x > maxXvel){
            this.vel.x = maxXvel;
        }else if(this.vel.x < -maxXvel){
            this.vel.x = -maxXvel;
        }
        if(this.vel.y > maxYvel){
            this.vel.y = maxYvel;
        }else if(this.vel.y < -maxYvel){
            this.vel.y = -maxYvel;
        }
        if(this.vel.z > maxZvel){
            this.vel.z = maxZvel;
        }else if(this.vel.z < -maxZvel){
            this.vel.z = -maxZvel;
        }
        manageVelocities();
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