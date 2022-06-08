package com.matthewadev.physics;

public class Physics { // in relation to center of player
    float x1;
    float y1;
    float z1;
    float x2;
    float y2;
    float z2;

    public Physics(float x1, float y1, float z1,float x2, float y2, float z2 ){
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }
    public void calculatePossibleCollisions(float originx, float originy, float originz){
        float x1real = originx + x1;
        float y1real = originy + y1;
        float z1real = originz + z1;
        float x2real = originx + x2;
        float y2real = originy + y2;
        float z2real = originz + z2;

        int xblocksToCheck = (int) (x1real - x2real);
        int xbegin = (int)x1real;
        int yblocksToCheck = (int) (y1real - y2real);
        int ybegin = (int)y1real;
        int zblocksToCheck = (int) (z1real - z2real);
        int zbegin = (int)z1real;



    }
}
