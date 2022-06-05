package com.matthewadev.render;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.matthewadev.game.Game;
import com.matthewadev.game.WorldGenerator;

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
    public void generateChunk(int x, int z){
        for (int i = 0; i < chunks.size(); i ++){
            if(chunks.get(i).x == x && chunks.get(i).z == z){
                chunks.set(i,WorldGenerator.generateChunk(x,z));
                return;
            }
        }
        chunks.add(WorldGenerator.generateChunk(x,z));
        System.out.println(chunks);
    }

    public void addBlock(Block block){
        int chunkx = block.getX() / 16;
        int chunkz = block.getZ() / 16;
        for (Chunk chunk : chunks){
            if(chunk.x == chunkx && chunk.z == chunkz){
                chunk.addBlock(block);
                return;
            }
        }
        // otherwise new chunk
        Chunk c = new Chunk(chunkx,chunkz);
        c.addBlock(block);
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
    // check if any chunks are too far away from the player and removes them
    // also checks if any chunks aren't loaded that should be loaded
    public void handleChunkDistances() {
/*        int chunksRemoved = 0;
        for (int i = 0; i < chunks.size() - chunksRemoved; i++) {
            if(Math.abs(chunks.get(i - chunksRemoved).x - Game.player.getPos().x / 16) > 4){
                if(Math.abs(chunks.get(i - chunksRemoved).z - Game.player.getPos().z / 16) > 4){
                    chunks.remove(i - chunksRemoved);
                    chunksRemoved += 1;
                }
            }
        }*/
        for(int x = -2; x < 2; x++){
            for(int z = -2; z < 2; z++){
                boolean exists = false;
                for (Chunk c: chunks) {
                    if(c.x == x + (int) Math.floor(Game.player.getPos().x / 16)){
                        if(c.z == z + (int) Math.floor(Game.player.getPos().z / 16)){
                            exists = true;
                            break;
                        }
                    }
                }
                if(!exists) {
                    //System.out.println((x + Game.player.getPos().x / 16) + " " + (z + Game.player.getPos().z / 16));
                    chunks.add(WorldGenerator.generateChunk(x + (int) Math.floor(Game.player.getPos().x / 16), z + (int) Math.floor(Game.player.getPos().z / 16)));
                }
            }
        }
/*        for (int i = 0; i < chunks.size() - chunksRemoved; i++) {
            if(Math.abs(chunks.get(i - chunksRemoved).x  - Game.player.getPos().x / 16) > 4){ // unload anything above 250 blocks
                if(Math.abs(chunks.get(i - chunksRemoved).z - Game.player.getPos().z / 16) > 4){ // unload anything above 250 blocks
                    chunks.remove(i - chunksRemoved);
                    chunksRemoved += 1;
                }
            }
        }*/
    }
}
