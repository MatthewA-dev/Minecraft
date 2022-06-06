package com.matthewadev.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.matthewadev.game.Game;
import com.matthewadev.render.Block;
import com.matthewadev.render.Chunk;

import java.util.ArrayList;

public class PhysicsManager {
    private static btDefaultCollisionConfiguration collisionConfig;
    private static btCollisionDispatcher dispatcher;
    private static btDbvtBroadphase broadphase;
    private static btCollisionWorld world;
    //private static HashMap<Integer[],btCollisionObject> colls = new HashMap<>();
    private static ArrayList<ChunkCollisionObject> chunkColls = new ArrayList<>();

    public static void init(){
        // https://xoppa.github.io/blog/using-the-libgdx-3d-physics-bullet-wrapper-part1/ thank you
        collisionConfig = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfig);
        broadphase = new btDbvtBroadphase();
        world = new btCollisionWorld(dispatcher, broadphase, collisionConfig);
    }
    public static int chunkExists(int x, int z){
        for (int i = 0; i < chunkColls.size(); i++) {
            if(chunkColls.get(i).x == x && chunkColls.get(i).z == z){
                return i;
            }
        }
        return -1;
    }
    public static void removeChunkFromWorld(int x, int z){
        int chunkI = chunkExists(x,z);
        if(chunkI == -1){
            return;
        }
        world.removeCollisionObject(chunkColls.get(chunkI).object);
        chunkColls.remove(chunkI);
    }
    // for chunk models
    public static void addChunkToWorld(Model m, Matrix4 transform, int x, int z, ArrayList<Block> allBlocks){ // x and z for chunks
        int i = chunkExists(x,z);
        ChunkCollisionObject c;
        if(i != -1){
            world.removeCollisionObject(chunkColls.get(i).object);
            chunkColls.get(i).redefineCollisionObject(m, transform, allBlocks);
            c = chunkColls.get(i);
        }else{
            chunkColls.add(new ChunkCollisionObject(x,z,m,transform, allBlocks));
            c = chunkColls.get(chunkColls.size() - 1);
        }
        world.addCollisionObject(c.object);
    }

    public static void checkRayhit(float range){
        Ray ray = Game.camera.getPickRay(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1); // fancy division by 2
        Vector3 from = ray.origin.cpy();
        Vector3 to = ray.direction.cpy();

        to.scl(range);
        to.add(from);
        ClosestRayResultCallback c = new ClosestRayResultCallback(from,to);
        world.rayTest(from,to,c);
        if(c.hasHit()){
            Vector3 hitpoint = new Vector3();
            c.getHitPointWorld(hitpoint);
            System.out.println(hitpoint);
            Game.crenderer.removeBlock((int) hitpoint.x, (int) hitpoint.y, (int) hitpoint.z);
        }
    }

}
