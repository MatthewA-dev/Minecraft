package com.matthewadev.physics;

import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;
import com.matthewadev.render.Block;
import com.matthewadev.render.BlockType;

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
    // https://gamedev.stackexchange.com/a/49423
    public static void getClosestIntersection(Vector3 direction, Vector3 pos){
        float mag = 9.0f;
        int voxelX = (int) pos.x;
        int voxelY = (int) pos.y;
        int voxelZ = (int) pos.z;

        float stepx = 1f;
        float stepy = 1f;
        float stepz = 1f;
        Vector3 cell = new Vector3();
        if(direction.x < 0){
            stepx *= -1;
        }else if(direction.x == 0){
            stepx *= 0;
        }
        if(direction.y < 0){
            stepy *= -1;
        }else if(direction.y == 0){
            stepy *= 0;
        }
        if(direction.z < 0){
            stepz *= -1;
        }else if(direction.z == 0){
            stepz *= 0;
        }

        //float tMaxX = pos.

    }
    // https://www.geeksforgeeks.org/bresenhams-algorithm-for-3-d-line-drawing/
    public static void raycastingGood(Vector3 start, Vector3 direction, float mag){
        int d = 0;

        Vector3 end = start.cpy().add(direction.cpy().scl(mag));

        int dx = (int) Math.abs(end.x * 100 - start.x * 100);
        int dy = (int) Math.abs(end.y * 100 - start.y * 100);
        int dz = (int) Math.abs(end.z * 100 - start.z * 100);


        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point
        int dz2 = 2 * dz;

        int ix = start.x < end.x ? 1 : -1; // increment direction
        int iy = start.y < end.y ? 1 : -1;
        int iz = start.z < end.z ? 1 : -1;

        int x = (int) (start.x * 100);
        int y = (int) (start.y * 100);
        int z = (int) (start.z * 100);

        int endx = (int) (end.x * 100);
        int endy = (int) (end.y * 100);
        int endz = (int) (end.z * 100);

        if (dx >= dy && dx >= dz) {
            float p1 = 2 * dy - dx;
            float p2 = 2 * dz - dx;
            while (x != endx) {
                x += ix;
                if (p1 >= 0) {
                    y += iy;
                    p1 -= 2 * dx;
                }
                if (p2 >= 0) {
                    z += iz;
                    p2 -= 2 * dx;
                }
                if(Game.crenderer.getBlock((x / 100), (y / 100), z / 100) != null) {
                    Game.crenderer.removeBlock((x / 100), (y / 100), z / 100);
                    return;
                }
            }

        } else if (dy >= dx && dy >= dz){
            float p1 = 2 * dx - dy;
            float p2 = 2 * dz - dy;
            while (true) {
                y += iy;
                if (p1 >= 0) {
                    x += ix;
                    p1 -= 2 * dy;
                }
                if (p2 >= 0){
                    p2 -= 2 * dy;
                    z += iz;
                }

                p1 += 2 * dx;
                p2 += 2 * dz;
                if(Game.crenderer.getBlock((x / 100), (y / 100),  z / 100) != null){
                    Game.crenderer.removeBlock((x / 100), (y / 100), z / 100);
                    return;
                }
            }
        }
        else {
            float p1 = 2 * dy - dz;
            float p2 = 2 * dx - dz;
            while (true) {
                p1 = 2 * dy - dz
                p2 = 2 * dx - dz
                while (z1 != z2) {
                    z += iz;
                    if (p1 >= 0) {
                        y += iy;
                        p1 -= 2 * dz;
                    }
                    if (p2 >= 0) {
                        x += ix;
                        p2 -= 2 * dz;
                    }
                    p1 += 2 * dy;
                    p2 += 2 * dx;
                    if (Game.crenderer.getBlock((x / 100), (y / 100), z / 100) != null) {
                        Game.crenderer.removeBlock((x / 100), (y / 100), z / 100);
                        return;
                    }
                }
            }
        }
    }

    public static void raycast(Vector3 origin, Vector3 direction){
        float maximumSquared = 100f;
        float x = origin.x;
        float y = origin.y;
        float z = origin.z;

        float dx = direction.x;
        float dy = direction.y;
        float dz = direction.z;

        int stepX = signum(dx);
        int stepY = signum(dy);
        int stepZ = signum(dz);

        float tMaxX = intbound(origin.x, dx);
        float tMaxY = intbound(origin.y, dy);
        float tMaxZ = intbound(origin.z, dz);

        float tDeltaX = (float)stepX/dx;
        float tDeltaY = (float)stepY/dy;
        float tDeltaZ = (float)stepZ/dz;
        if (dx == 0 && dy == 0 && dz == 0){
            System.out.println("NO");
            return;
        }
        while (true) {
            // tMaxX stores the t-value at which we cross a cube boundary along the
            // X axis, and similarly for Y and Z. Therefore, choosing the least tMax
            // chooses the closest cube boundary. Only the first case of the four
            // has been commented in detail.
            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    //if (tMaxX > radSquare) break;
                    // Update which cube we are now in.
                    x += stepX;
                    // Adjust tMaxX to the next X-oriented boundary crossing.
                    tMaxX += tDeltaX;
                    // Record the normal vector of the cube face we entered.
/*                    face[0] = -stepX;
                    face[1] = 0;
                    face[2] = 0;*/
                } else {
                    //if (tMaxZ > radSquare) break;
                    z += stepZ;
                    tMaxZ += tDeltaZ;
/*                    face[0] = 0;
                    face[1] = 0;
                    face[2] = -stepZ;*/
                }
            } else {
                if (tMaxY < tMaxZ) {
                    //if (tMaxY > radSquare) break;
                    y += stepY;
                    tMaxY += tDeltaY;
/*                    face[0] = 0;
                    face[1] = -stepY;
                    face[2] = 0;*/
                } else {
                    // Identical to the second case, repeated for simplicity in
                    // the conditionals.
                    //if (tMaxZ > radSquare) break;
                    z += stepZ;
                    tMaxZ += tDeltaZ;
/*                    face[0] = 0;
                    face[1] = 0;
                    face[2] = -stepZ;*/
                }
            }
            if(tMaxX<tMaxY) {
                if(tMaxZ<tMaxX) {
                    if(tMaxZ > 100f) break;
                } else {
                    if(tMaxX > 100f) break;
                }
            } else {
                if(tMaxY<tMaxZ) {
                    if(tMaxY > 100f) break;
                } else {
                    if(tMaxZ > 100f) break;
                }
            }
            System.out.print(x + " " + y + " " + z + " ");
            System.out.print(floorCorrectly(x) + " " + floorCorrectly(y) + " " + floorCorrectly(z) + " ");
            System.out.println(Game.crenderer.getBlock(floorCorrectly(x),floorCorrectly(y),floorCorrectly(z)));
            if(Game.crenderer.getBlock(floorCorrectly(x),floorCorrectly(y),floorCorrectly(z)) != null){
                Game.crenderer.removeBlock(floorCorrectly(x),floorCorrectly(y),floorCorrectly(z));
                break;
            }
        }
        System.out.println("====================");
    }
    public static int floorCorrectly(float num){
        if(num < 0){
            return (int) (num - 1);
        }else{
            return (int) num;
        }
    }
    public static float intbound(float s, float ds){
        return 1/ds;
    }
    public static int signum(float x){
        if(x > 0){
            return 1;
        }else if(x < 0){
            return -1;
        }else{
            return 0;
        }
    }
    public static int mod(int value, int modulus){
        return (value % modulus + modulus) % modulus;
    }
}
