package com.matthewadev.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.matthewadev.game.Game;
import com.matthewadev.render.Chunk;

import java.util.ArrayList;
import java.util.HashMap;

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
    public static void addChunkToWorld(Model m, Matrix4 transform, int x, int z){ // x and z for chunks
        int i = chunkExists(x,z);
        ChunkCollisionObject c;
        if(i != -1){
            world.removeCollisionObject(chunkColls.get(i).object);
            chunkColls.get(i).redefineCollisionObject(m, transform);
            c = chunkColls.get(i);
        }else{
            chunkColls.add(new ChunkCollisionObject(x,z,m,transform));
            c = chunkColls.get(chunkColls.size() - 1);
        }
        world.addCollisionObject(c.object);
    }

    public static void checkRayhit(float range){
        Ray ray = Game.camera.getPickRay(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1); // fancy division by 2
        Vector3 from = ray.origin;
        Vector3 to = ray.direction.cpy();

        to.scl(range);
        to.add(from);

        for(Chunk c: Game.crenderer.getChunks()){
            ModelInstance i = c.getInstance();
            Vector3 pos = new Vector3();
            i.transform.getTranslation(pos);
            Vector3 intersection = new Vector3();
            if(Intersector.intersectRayBounds(ray,c.getBounds(),intersection)){
                Game.crenderer.removeBlock((int) intersection.x, (int) intersection.y, (int) intersection.z);
            }
        }
    }

}
