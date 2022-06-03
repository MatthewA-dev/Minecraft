package com.matthewadev.render;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.utils.Array;
import com.matthewadev.game.Game;

import java.util.ArrayList;
import java.util.Comparator;

public class BlockRenderer {
    private ArrayList<Block> blocks = new ArrayList<>();

    public void renderBlocks(ModelBatch batch, Environment env){
        batch.begin(Game.camera);
        for(Block b: blocks){
            b.render(batch, env);
        }
        //batch.getRenderableSorter().sort(Game.camera,batch.);
        batch.end();
    }

    public void addBlock(int x, int y, int z, BlockType b){
        blocks.add(new Block(x,y,z,b));
    }
    public void dispose(){
        for (Block b: blocks){
            b.dispose();
        }
    }
}

/*
class Sorter implements RenderableSorter {

    @Override
    public void sort(Camera camera, Array<Renderable> renderables) {
        renderables.sort(new InstanceComparator());

    }
}

class InstanceComparator implements Comparator<Renderable> {

    @Override
    public int compare(Renderable o1, Renderable o2) {
        double diffx1 = Math.pow(Game.camera.position.x - o1.worldTransform.getScaleX(),2);
        double diffy1 = Math.pow(Game.camera.position.y - o1.worldTransform.getScaleY(),2);
        double diffz1 = Math.pow(Game.camera.position.z - o1.worldTransform.getScaleZ(),2);


        double diffx2 = Math.pow(Game.camera.position.x - o2.worldTransform.getScaleX(),2);
        double diffy2 = Math.pow(Game.camera.position.y - o2.worldTransform.getScaleY(),2);
        double diffz2 = Math.pow(Game.camera.position.z - o2.worldTransform.getScaleZ(),2);

        double dis1 = diffx1 + diffy1 + diffz1;
        double dis2 = diffx2 + diffy2 + diffz2;

        if(dis1 < dis2){
            return 1;
        }else if(dis1 == dis2){
            return 0;
        }else{
            return -1;
        }
    }
}*/
