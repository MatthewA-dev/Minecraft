package com.matthewadev.render;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.matthewadev.game.Game;

import java.util.ArrayList;

public class ChunkManager {
    private ArrayList<Chunk> chunks = new ArrayList<>();
    public void render(ModelBatch batch, Environment env){
        batch.begin(Game.camera);
        for (Chunk c: chunks){
            c.render(batch,env);
        }
        batch.end();
    }

    public void addBlock(Block block){
        int chunkx = block.getX() / 16;
        int chunkz = block.getZ() / 16;
        for (Chunk chunk : chunks){
            if(chunk.x == chunkx && chunk.z == chunkz){
                chunk.addBlock(block, chunkx, block.getY(), chunkz);
                return;
            }
        }
        // otherwise new chunk
        Chunk c = new Chunk(chunkx,chunkz);
        c.addBlock(block, block.getX(), block.getY(), block.getZ());
        chunks.add(c);
    }

    public Block getBlock(int x, int y, int z){
        int chunkx = x / 16;
        int chunkz = z / 16;
        for (Chunk chunk : chunks){
            if(chunk.x == chunkx && chunk.z == chunkz){
                return chunk.getBlock(chunkx, y, chunkz);
            }
        }
        return null;
    }
    public void dispose(){
        for (Chunk c: chunks){
            c.dispose();
        }
    }
}
