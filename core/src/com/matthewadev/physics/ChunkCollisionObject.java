package com.matthewadev.physics;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;

public class ChunkCollisionObject {
    public btCollisionObject object;
    public int x;
    public int z;
    public ChunkCollisionObject(int x, int z, Model m, Matrix4 transform){
        this.x = x;
        this.z = z;
        redefineCollisionObject(m, transform);
    }
    public void redefineCollisionObject(Model m, Matrix4 transform){

        object = new btCollisionObject();
        object.setCollisionShape(Bullet.obtainStaticNodeShape(m.nodes));
        //object.setWorldTransform(transform);
    }
}
