package com.matthewadev.render;

import java.util.ArrayList;

public class ChunkManager {
    private ArrayList<Chunk> chunks = new ArrayList<>();
    public void render(){
        for (Chunk c: chunks){
            c.render();
        }
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
