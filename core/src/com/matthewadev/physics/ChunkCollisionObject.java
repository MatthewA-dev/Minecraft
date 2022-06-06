package com.matthewadev.physics;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.matthewadev.render.Block;

import java.util.ArrayList;

public class ChunkCollisionObject {
    public btCollisionObject object;
    public int x;
    public int z;
    public btCompoundShape shape;
    public ChunkCollisionObject(int x, int z, Model m, Matrix4 transform, ArrayList<Block> blocks){
        this.x = x;
        this.z = z;
        redefineCollisionObject(m, transform, blocks);
    }
    public void redefineCollisionObject(Model m, Matrix4 transform, ArrayList<Block> blocks){
        object = new btCollisionObject();
        shape = new btCompoundShape();
        for(Block b: blocks){
            btCollisionShape blockShape = new btBoxShape(b.toChunkCoords());
            shape.addChildShape(new Matrix4(new Vector3(b.getX(), b.getY(), b.getZ()), new Quaternion(0f,0f,0f,0f), new Vector3(1f,1f,1f)), blockShape);
        }

        //shape = Bullet.obtainStaticNodeShape(m.nodes);
        //object.setCollisionShape(Bullet.);
        object.setCollisionShape(shape); // THIS IS THE PROBLEM
        object.setWorldTransform(transform);
    }
}
