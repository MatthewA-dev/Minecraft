package com.matthewadev.physics;

import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;

import java.util.ArrayList;

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
    // https://www.flipcode.com/archives/Raytracing_Topics_Techniques-Part_4_Spatial_Subdivisions.shtml
    // https://web.archive.org/web/20180713020237/http://www.cse.chalmers.se/edu/year/2010/course/TDA361/grid.pdf
    public static void getClosestIntersection(Vector3 direction, Vector3 pos){
        float mag = 3.0f;
        float stepx = 1f;
        float stepy = 1f;
        float stepz = 1f;
        Vector3 cell = new Vector3();
        if(direction.x < 0){
            stepx *= -1;
        }
        if(direction.y < 0){
            stepy *= -1;
        }
        if(direction.z < 0){
            stepz *= -1;
        }

        float tMaxX;
        float tMaxY;
        float tMaxZ;

        if(direction.x != 0){
            float rxr = 1.0f / direction.x;
        }
        else tMaxX = 1000000;
    }
}
