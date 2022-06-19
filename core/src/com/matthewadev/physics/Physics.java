package com.matthewadev.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;
import com.matthewadev.game.Player;
import com.matthewadev.render.Block;
import com.matthewadev.render.BlockType;

import static java.lang.Math.abs;

public class Physics {
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
    public static float distanceTo(Vector3 p1, Vector3 p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));
    }
    // https://stackoverflow.com/a/55277311/14969155
    // https://www.gamedev.net/blogs/entry/2265248-voxel-traversal-algorithm-ray-casting/
    public static Vector3[] calcCols(Vector3 start, Vector3 direction, float mag, boolean scaleDirection, boolean doRounding, int scale){
        Vector3[] returns = new Vector3[2];
        if(scaleDirection) {
            direction = direction.cpy().scl(mag);
        }else{
            mag = (float) Math.sqrt(direction.x * direction.x + direction.y * direction.y + direction.z * direction.z);
        }
        int x1 = (int) ((direction.x + start.x) * scale), y1 = (int) ((direction.y + start.y) * scale), z1 = (int) ((direction.z + start.z) * scale);
        int x0 = (int) (start.x * scale), y0 = (int) (start.y * scale), z0 = (int) (start.z * scale);
        int dx = abs(x1 - x0);
        int dy = abs(y1 - y0);
        int dz = abs(z1 - z0);
        int stepX = x0 < x1 ? 1 : -1;
        int stepY = y0 < y1 ? 1 : -1;
        int stepZ = z0 < z1 ? 1 : -1;
        double tMaxX = mag * 0.5 / dx;
        double tMaxY = mag * 0.5 / dy;
        double tMaxZ = mag * 0.5 / dz;
        double tDeltaX = mag / dx;
        double tDeltaY = mag / dy;
        double tDeltaZ = mag / dz;
        Vector3 normal = new Vector3(0f,0f,0f);
        while (x0 != x1 || y0 != y1 || z0 != z1) {
            if(tMaxX < tMaxY) {
                if(tMaxZ < tMaxX) { // z smallest
                    normal.set(0f, 0, -stepZ);
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;
                } else { // x smallest
                    normal.set(-stepX, 0, 0);
                    x0 = x0 + stepX;
                    tMaxX = tMaxX + tDeltaX;
                }
            } else {
                if(tMaxY < tMaxZ) { // y smallest
                    normal.set(0f, -stepY, 0);
                    y0 = y0 + stepY;
                    tMaxY = tMaxY + tDeltaY;
                } else { // z smallest
                    normal.set(0f, 0, -stepZ);
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;
                }
            }
            //System.out.println(x0 + " " + y0 + " " + z0 + " | "  + floorCorrectly((float) (x0) / scale) + " " + floorCorrectly((float) y0 / scale) + " " + floorCorrectly((float) z0 / scale) + " | " + normal);
/*            System.out.println(tMaxX + " " + tMaxY + " " + tMaxZ + " | " + x0 + " " + y0 + " " + z0);
            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    normal.set(-stepX, 0, 0);
                    x0 = x0 + stepX;
                    tMaxX = tMaxX + tDeltaX;
                } else if (tMaxX > tMaxZ) {
                    normal.set(0f, 0, -stepZ);
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;
                } else {
                    normal.set(0, -stepY, 0);
                    x0 = x0 + stepX;
                    tMaxX = tMaxX + tDeltaX;
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;
                }
            } else if (tMaxX > tMaxY) {
                if (tMaxY < tMaxZ) {
                    normal.set(0f, -stepY, 0);
                    y0 = y0 + stepY;
                    tMaxY = tMaxY + tDeltaY;
                } else if (tMaxY > tMaxZ) {
                    normal.set(0, 0, -stepZ);
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;
                } else {
                    normal.set(-stepX, 0, 0);
                    y0 = y0 + stepY;
                    tMaxY = tMaxY + tDeltaY;
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;

                }
            } else {
                if (tMaxY < tMaxZ) {
                    normal.set(0f, -stepY, 0);
                    y0 = y0 + stepY;
                    tMaxY = tMaxY + tDeltaY;
                    x0 = x0 + stepX;
                    tMaxX = tMaxX + tDeltaX;
                } else if (tMaxY > tMaxZ) {
                    normal.set(0f, 0, -stepZ);
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;
                } else {
                    normal.set(-stepX, 0, 0);
                    x0 = x0 + stepX;
                    tMaxX = tMaxX + tDeltaX;
                    y0 = y0 + stepY;
                    tMaxY = tMaxY + tDeltaY;
                    z0 = z0 + stepZ;
                    tMaxZ = tMaxZ + tDeltaZ;

                }
            }*/
/*            System.out.println(floorCorrectly((float) x0 / scale) + " " +
                    floorCorrectly((float) y0 / scale) + " " +
                    floorCorrectly((float) z0 / scale));*/
            //System.out.println((x0/10) + " " + (y0/10) + " " + (z0/10)  + " " + Game.crenderer.getBlock((x0/10), (y0/10), z0/10));
            if (Game.crenderer.getBlock(floorCorrectly((float) x0 / scale),
                    floorCorrectly((float) y0 / scale),
                    floorCorrectly((float) z0 / scale)) != null) {
                if(doRounding) {
                    returns[0] = new Vector3(floorCorrectly((float) (x0) / scale),
                            floorCorrectly((float) (y0) / scale),
                            floorCorrectly((float) (z0) / scale));
                }else{
                    returns[0] = new Vector3(((float) x0/* + normal.x*/) / scale,
                            ((float) y0 /*+ normal.y*/) / scale,
                            ((float) z0 /*+ normal.z*/) / scale);
                }
                returns[1] = normal;
                if(normal.y == 0) {
                    //System.out.println(returns[0] + " " + start + " " + direction + " " + normal);
                }
                return returns;
            }
        }
        return null;
    }
    public static void destroyBlockWhereLooking(){
        try {
            Vector3 block = calcCols(Game.camera.position, Game.camera.direction, 5.5f, true, true, 10)[0];
            Game.crenderer.removeBlock((int) block.x, (int) block.y, (int) block.z);
        }catch(NullPointerException ignored){
        }
    }
    public static void addBlockWhereLooking(BlockType type){
        try {
            Vector3[] block = calcCols(Game.camera.position, Game.camera.direction, 5.5f, true, true, 10);
            Block b = new Block((int) (block[0].x + block[1].x), (int) (block[0].y + block[1].y), (int) (block[0].z + block[1].z), type);
            //System.out.println(b.getX() + " " + b.getY() + " " + b.getZ());
            // check if your inside block
            /*for (int addx = -1; addx < 2; addx += 2) {
                for(int addy = 0; addy < 2; addy += 1){
                    for (int addz = -1; addz < 2; addz += 2) {
*//*                        System.out.println(floorCorrectly(Game.player.getPos().x + (Game.player.width / 2f) * addx) + " " +
                                floorCorrectly(Game.player.getPos().y + (Game.player.height) * addy) + " " +
                                floorCorrectly(Game.player.getPos().z + (Game.player.width / 2f) * addz));*//*
                        if (floorCorrectly(Game.player.getPos().x + (Game.player.width / 2f) * addx) == block[0].x + block[1].x) {
                            if (floorCorrectly(Game.player.getPos().y - (Game.player.height) * addy) == block[0].y + block[1].y)  {
                                if(floorCorrectly(Game.player.getPos().z + (Game.player.width / 2f) * addz) == block[0].z + block[1].z) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }*/
            Game.crenderer.addBlock(b);
            Vector3 vel = Game.player.getVel().cpy().scl(Gdx.graphics.getDeltaTime());
            vel.x *= 3;
            vel.z *= 3;
            Vector3 pos = Game.player.getPos().cpy().add(vel);
            if(Game.player.isColliding(Game.player.getPos().x, Game.player.getPos().y, Game.player.getPos().z)){
                Game.crenderer.removeBlock(b);
            }else if(Game.player.isColliding(pos.x, pos.y, pos.z)){
                Game.crenderer.removeBlock(b);
            }
        }catch(NullPointerException ignored){}
    }
/*    public void calculatePossibleCollisions(float originx, float originy, float originz){
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
        Vector3 end = start.cpy().add(direction.cpy().scl(mag));

        int dx = (int) abs(end.x * 10 - start.x * 10);
        int dy = (int) abs(end.y * 10 - start.y * 10);
        int dz = (int) abs(end.z * 10 - start.z * 10);

        int ix = start.x < end.x ? 1 : -1;
        int iy = start.y < end.y ? 1 : -1;
        int iz = start.z < end.z ? 1 : -1;

        int x = (int) (start.x * 10);
        int y = (int) (start.y * 10);
        int z = (int) (start.z * 10);

        int endx = (int) (end.x * 10);
        int endy = (int) (end.y * 10);
        int endz = (int) (end.z * 10);

        if (dx >= dy && dx >= dz) {
            int p1 = 2 * dy - dx;
            int p2 = 2 * dz - dx;
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
                p1 += 2 * dy;
                p2 += 2 * dz;
                if(Game.crenderer.getBlock((x / 10), (y / 10), z / 10) != null) {
                    Game.crenderer.removeBlock((x / 10), (y / 10), z / 10);
                    return;
                }
            }

        } else if (dy >= dx && dy >= dz){
            int p1 = 2 * dx - dy;
            int p2 = 2 * dz - dy;
            while (y != endy) {
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
                if(Game.crenderer.getBlock((x / 10), (y / 10),  z / 10) != null){
                    Game.crenderer.removeBlock((x / 10), (y / 10), z / 10);
                    return;
                }
            }
        }
        else {
            int p1 = 2 * dy - dz;
            int p2 = 2 * dx - dz;
            while (z != endz) {
                p1 = 2 * dy - dz;
                p2 = 2 * dx - dz;
                while (z != endz) {
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
                    if (Game.crenderer.getBlock((x / 10), (y / 10), z / 10) != null) {
                        Game.crenderer.removeBlock((x / 10), (y / 10), z / 10);
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
*//*                    face[0] = -stepX;
                    face[1] = 0;
                    face[2] = 0;*//*
                } else {
                    //if (tMaxZ > radSquare) break;
                    z += stepZ;
                    tMaxZ += tDeltaZ;
*//*                    face[0] = 0;
                    face[1] = 0;
                    face[2] = -stepZ;*//*
                }
            } else {
                if (tMaxY < tMaxZ) {
                    //if (tMaxY > radSquare) break;
                    y += stepY;
                    tMaxY += tDeltaY;
*//*                    face[0] = 0;
                    face[1] = -stepY;
                    face[2] = 0;*//*
                } else {
                    // Identical to the second case, repeated for simplicity in
                    // the conditionals.
                    //if (tMaxZ > radSquare) break;
                    z += stepZ;
                    tMaxZ += tDeltaZ;
*//*                    face[0] = 0;
                    face[1] = 0;
                    face[2] = -stepZ;*//*
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
    }*/
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
    public static int signum(float num){
        if(num > 0){
            return 1;
        }else if(num < 0){
            return -1;
        }else{
            return 0;
        }
    }
    public static int mod(int value, int modulus){
        return (value % modulus + modulus) % modulus;
    }
}
