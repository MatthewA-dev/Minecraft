package com.matthewadev.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.physics.Physics;

public class Player {
    private Vector3 pos;
    public float height = 1.8f;
    public float width = 0.8f;
    private Vector3 vel;
    public boolean isFlying = true;
    public boolean isOnGround = false;
    private float maxHorVel = 4f;
    private float maxYvel = 1000f;

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
        //manageVelocities();
        Vector3 v = vel.cpy().scl(Gdx.graphics.getDeltaTime());
        v.x *= 3;
        v.z *= 3;
        this.isOnGround = false;
        for (int addx = -1; addx < 2; addx += 2) {
            for (float addy = 0; addy <= 1; addy += 1) {
                for (int addz = -1; addz < 2; addz += 2) {
                    Vector3 origin = new Vector3(pos.x + (width / 2f) * addx, pos.y - (height) * addy, pos.z + (width / 2f) * addz);
                    //System.out.println(origin);
                    if (addy == 1) {
                        if (Physics.calcCols(origin, new Vector3(0f, -0.02f, 0f), 0.01f, false, false, 100) != null) {
                            if (Physics.calcCols(origin.cpy().add(v), new Vector3(0f, -0.02f, 0f), 0.01f, false, false, 100) != null) {
                                this.isOnGround = true;
                            }
                        }
                    }
                }
            }
        }
        dealWithColliding();
    }
    private void manageVelocities(){
        boolean shouldBeOnGround = false;
        Vector3 origin;
        Vector3 v = vel.cpy().scl(Gdx.graphics.getDeltaTime());
        float smallestcol = Float.POSITIVE_INFINITY;
        float toSetX = pos.x;
        float toSetY = pos.y;
        float toSetZ = pos.z;
        Vector3 toSetVel = v.cpy();
        v.x *= 3;
        v.z *= 3;
        boolean hasCol = false;
        for (int addx = -1; addx < 2; addx += 2) {
            for (float addy = 0; addy <= 1; addy += 1) {
                for (int addz = -1; addz < 2; addz += 2) {
                    origin = new Vector3(pos.x + (width / 2f) * addx, pos.y - (height) * addy, pos.z + (width / 2f) * addz);
                    //System.out.println(origin);
                    if (addy == 1) {
                        if (Physics.calcCols(origin, new Vector3(0f, -0.01f, 0f), 0.01f, false, false, 100) != null) {
                            if (Physics.calcCols(origin.cpy().add(v), new Vector3(0f, -0.01f, 0f), 0.01f, false, false, 100) != null) {
                                shouldBeOnGround = true;
                            }
                        }
                    }
                    if(hasCol){
                        continue;
                    }
                    Vector3[] collision = Physics.calcCols(origin, v, 0f, false, false, 100);
                    try {
                        /*if(origin.y < 1f) {
                            System.out.println(origin);
                        }*/
                        // managing collisions
/*                        System.out.println(pos + " " + origin + " " + collision[0]);
                        System.out.println(v);
                        System.out.println(collision[1]);*/
                        float d = Physics.distanceTo(origin, collision[0]);
                        if(d < smallestcol){
                            if (collision[1].x != 0f) {
                                //System.out.println("X");
                                if (toSetX != (collision[0].x) - ((width / 2f) * (addx))) {
                                    toSetX = (collision[0].x + 0.01f * collision[1].x) - ((width / 2f) * (addx));
                                }
                                toSetY = (collision[0].y) + height * addy;
                                toSetZ = collision[0].z - (width / 2f) * (addz);
                                vel.x = 0;
                                hasCol = true;
                            }
                            if (collision[1].y != 0f) {
                                //System.out.println("Y");
                                toSetX = collision[0].x - (width / 2f) * addx;
                                if (toSetY != (collision[0].y + (height) * addy)) {
                                    toSetY = (float) (collision[0].y + 0.01 * collision[1].y + (height) * addy);
                                }
                                toSetZ = collision[0].z - (width / 2f) * addz;
                                shouldBeOnGround = true;
                                vel.y = 0;
                                hasCol = true;
                            }
                            if (collision[1].z != 0f) {
                                //System.out.println("Z");
                                toSetX = (collision[0].x) - ((width / 2f) * (addx));
                                toSetY = (collision[0].y) + height * addy;
                                if (toSetZ != (collision[0].z) - ((width / 2f) * (addz))) {
                                    toSetZ = (collision[0].z + 0.01f * collision[1].z) - (width / 2f) * (addz);
                                }
                                vel.z = 0;
                                hasCol = true;
                            }
                            smallestcol = d;
                        }
                        //System.out.println(pos);
                    } catch (NullPointerException e) {
                    }
                }
            }
        }
        //Vector3 posp = pos.cpy();
        pos.x = toSetX;
        pos.y = toSetY;
        pos.z = toSetZ;
/*        if(!posp.equals(pos)){

            System.out.println(posp + " " + pos);
        }*/
        //vel.set(toSetVel);
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
            this.vel.x *= Math.pow(0.8,60*Gdx.graphics.getDeltaTime());
            this.vel.z *= Math.pow(0.8,60*Gdx.graphics.getDeltaTime());
        }

        Vector3 v = this.vel.cpy().scl(Gdx.graphics.getDeltaTime());
        v.x *= 3;
        v.z *= 3;
        boolean d = isColliding();
        //System.out.println("FIRST: " + isColliding());
        this.pos.add(v);
/*        if(isColliding()){
            this.pos.sub(v);
        }*/
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
        addVel(vel.x, vel.y, vel.z);
/*        this.vel.add(vel);
        if(this.vel.x * this.vel.x + this.vel.z * this.vel.z > maxHorVel * maxHorVel){
            Vector2 v = new Vector2(this.vel.x, this.vel.z);
            v.nor();
            v.scl(maxHorVel);
            this.vel.x = v.x;
            this.vel.z = v.y;
        }
        if(this.vel.y > maxYvel){
            this.vel.y = maxYvel;
        }else if(this.vel.y < -maxYvel) {
            this.vel.y = -maxYvel;
        }*/
    }
    public void addVel(float x, float y, float z){
        this.vel.x += x;
        this.vel.y += y;
        this.vel.z += z;
        if(this.vel.x * this.vel.x + this.vel.z * this.vel.z > maxHorVel * maxHorVel){
            Vector2 v = new Vector2(this.vel.x, this.vel.z);
            v.nor();
            v.scl(maxHorVel);
            this.vel.x = v.x;
            this.vel.z = v.y;
        }
        if(this.vel.y > maxYvel){
            this.vel.y = maxYvel;
        }else if(this.vel.y < -maxYvel){
            this.vel.y = -maxYvel;
        }
        Vector3 v = this.vel.cpy();
        v.scl(Gdx.graphics.getDeltaTime());
        v.x *= 3;
        v.z *= 3;
        for (int addx = -1; addx < 2; addx += 2) {
            for (float addy = 0; addy <= 1; addy += 0.5) {
                for (int addz = -1; addz < 2; addz += 2) {
                    Vector3 origin = new Vector3(pos.x + (width / 2f) * addx, pos.y - (height) * addy, pos.z + (width / 2f) * addz);
                    Vector3[] collision = Physics.calcCols(origin, v, 0f, false, false, 100);
                    try {
                        //System.out.println(v + " " + origin + " " + collision[0] + " " + collision[1]);
                        if (collision[1].x != 0f) {
                            this.vel.x = 0;
                            v.x = 0;
                        }
                        if (collision[1].y != 0f) {
                            pos.x = collision[0].x - (width / 2f) * addx;
                            if (pos.y != (collision[0].y + (height) * addy)) {
                                pos.y = (float) (collision[0].y + 0.01 * collision[1].y + (height) * addy);
                            }
                            pos.z = collision[0].z - (width / 2f) * addz;
                            isOnGround = true;
                            this.vel.y = 0;
                            v.y = 0;
                        }
                        if (collision[1].z != 0f) {
                            this.vel.z = 0;
                            v.z = 0;
                        }
                    } catch (NullPointerException e) {
                    }
                }
            }
        }
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
        vel.x = x;
        if(this.vel.x * this.vel.x + this.vel.z * this.vel.z > maxHorVel * maxHorVel){
            Vector2 v = new Vector2(this.vel.x, this.vel.z);
            v.nor();
            v.scl(maxHorVel);
            this.vel.x = v.x;
            this.vel.z = v.y;
        }
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
        vel.z = z;
        if(this.vel.x * this.vel.x + this.vel.z * this.vel.z > maxHorVel * maxHorVel){
            Vector2 v = new Vector2(this.vel.x, this.vel.z);
            v.nor();
            v.scl(maxHorVel);
            this.vel.x = v.x;
            this.vel.z = v.y;
        }
    }
    public Vector3 getVel(){
        return vel;
    }

}