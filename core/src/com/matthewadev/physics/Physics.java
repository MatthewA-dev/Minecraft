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
    function raycast(origin, direction, radius, callback) {
        // From "A Fast Voxel Traversal Algorithm for Ray Tracing"
        // by John Amanatides and Andrew Woo, 1987
        // <http://www.cse.yorku.ca/~amana/research/grid.pdf>
        // <http://citeseer.ist.psu.edu/viewdoc/summary?doi=10.1.1.42.3443>
        // Extensions to the described algorithm:
        //   • Imposed a distance limit.
        //   • The face passed through to reach the current cube is provided to
        //     the callback.

        // The foundation of this algorithm is a parameterized representation of
        // the provided ray,
        //                    origin + t * direction,
        // except that t is not actually stored; rather, at any given point in the
        // traversal, we keep track of the *greater* t values which we would have
        // if we took a step sufficient to cross a cube boundary along that axis
        // (i.e. change the integer part of the coordinate) in the variables
        // tMaxX, tMaxY, and tMaxZ.

        // Cube containing origin point.
        var x = Math.floor(origin[0]);
        var y = Math.floor(origin[1]);
        var z = Math.floor(origin[2]);
        // Break out direction vector.
        var dx = direction[0];
        var dy = direction[1];
        var dz = direction[2];
        // Direction to increment x,y,z when stepping.
        var stepX = signum(dx);
        var stepY = signum(dy);
        var stepZ = signum(dz);
        // See description above. The initial values depend on the fractional
        // part of the origin.
        var tMaxX = intbound(origin[0], dx);
        var tMaxY = intbound(origin[1], dy);
        var tMaxZ = intbound(origin[2], dz);
        // The change in t when taking a step (always positive).
        var tDeltaX = stepX/dx;
        var tDeltaY = stepY/dy;
        var tDeltaZ = stepZ/dz;
        // Buffer for reporting faces to the callback.
        var face = vec3.create();

        // Avoids an infinite loop.
        if (dx === 0 && dy === 0 && dz === 0)
            throw new RangeError("Raycast in zero direction!");

        // Rescale from units of 1 cube-edge to units of 'direction' so we can
        // compare with 't'.
        radius /= Math.sqrt(dx*dx+dy*dy+dz*dz);

        while (/* ray has not gone past bounds of world */
                (stepX > 0 ? x < wx : x >= 0) &&
                        (stepY > 0 ? y < wy : y >= 0) &&
                        (stepZ > 0 ? z < wz : z >= 0)) {

            // Invoke the callback, unless we are not *yet* within the bounds of the
            // world.
            if (!(x < 0 || y < 0 || z < 0 || x >= wx || y >= wy || z >= wz))
                if (callback(x, y, z, blocks[x*wy*wz + y*wz + z], face))
                    break;

            // tMaxX stores the t-value at which we cross a cube boundary along the
            // X axis, and similarly for Y and Z. Therefore, choosing the least tMax
            // chooses the closest cube boundary. Only the first case of the four
            // has been commented in detail.
            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    if (tMaxX > radius) break;
                    // Update which cube we are now in.
                    x += stepX;
                    // Adjust tMaxX to the next X-oriented boundary crossing.
                    tMaxX += tDeltaX;
                    // Record the normal vector of the cube face we entered.
                    face[0] = -stepX;
                    face[1] = 0;
                    face[2] = 0;
                } else {
                    if (tMaxZ > radius) break;
                    z += stepZ;
                    tMaxZ += tDeltaZ;
                    face[0] = 0;
                    face[1] = 0;
                    face[2] = -stepZ;
                }
            } else {
                if (tMaxY < tMaxZ) {
                    if (tMaxY > radius) break;
                    y += stepY;
                    tMaxY += tDeltaY;
                    face[0] = 0;
                    face[1] = -stepY;
                    face[2] = 0;
                } else {
                    // Identical to the second case, repeated for simplicity in
                    // the conditionals.
                    if (tMaxZ > radius) break;
                    z += stepZ;
                    tMaxZ += tDeltaZ;
                    face[0] = 0;
                    face[1] = 0;
                    face[2] = -stepZ;
                }
            }
        }
    }
    public static int intbound(int s, int ds){
        if (ds < 0) {
            return intbound(-s, -ds);
        } else {
            s = mod(s, 1);
            return (1-s)/ds;
        }
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
