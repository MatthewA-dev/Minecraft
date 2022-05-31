package com.matthewadev.render;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import java.util.ArrayList;

public class BlockRenderer {
    private ArrayList<Block> blocks = new ArrayList<>();

    public void renderBlocks(PerspectiveCamera camera, ModelBatch batch){
        batch.begin(camera);
        for(Block b: blocks){
            b.render(camera, batch);
        }
        batch.end();
    }

    public void addBlock(int x, int y, int z){
        blocks.add(new Block(x,y,z));
    }
    public void dispose(){
        for (Block b: blocks){
            b.dispose();
        }
    }
}
