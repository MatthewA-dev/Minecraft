package com.matthewadev.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.matthewadev.game.Game;
import com.matthewadev.render.Block;
import com.matthewadev.render.Chunk;
public class RaycastingManager {
    public static void checkccccRayhit(float range){
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
