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
        for (int i = 0; i < chunks.size(); i++){
            if(chunks.get(i).x == x && chunks.get(i).z == z){
                chunks.set(i,WorldGenerator.generateChunk(x,z));
                return;
            }
        }
        chunks.add(WorldGenerator.generateChunk(x,z));
    }

    public void addBlock(Block block){

        int chunkx = block.getX() / 16;
        int chunkz = block.getZ() / 16;
        for (Chunk chunk : chunks) {
            if (chunk.x == chunkx && chunk.z == chunkz) {
                chunk.addBlock(block);
                return;
            }
        }
        // otherwise new chunk
        Chunk c = new Chunk(chunkx, chunkz);
        c.addBlock(block);
        chunks.add(c);
    }
    public void removeBlock(Block block){
        removeBlock(block.getX(),block.getY(),block.getZ());
    }
    public void removeBlock(int x, int y, int z){
        int chunkx = Chunk.getChunkCoord(x);
        int chunkz = Chunk.getChunkCoord(z);
        for (Chunk chunk : chunks) {
            if (chunk.x == chunkx && chunk.z == chunkz) {
                chunk.removeBlock(x,y,z);
                return;
            }
        }
    }

    public Block getBlock(int x, int y, int z){
        int chunkx = x / 16;
        int chunkz = z / 16;
        for (Chunk chunk : chunks){
            if(chunk.x == chunkx && chunk.z == chunkz){
                return chunk.getBlock(x, y, z);
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
    public void unloadUnseenChunks(){
        ArrayList<Integer> removeIndexs = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            if(Math.abs(chunks.get(i).x - (int)(Game.player.getPos().x / 16)) > 2 ||
                    Math.abs(chunks.get(i).z - (int)(Game.player.getPos().z / 16)) > 2){
                removeIndexs.add(0,i);
            }
        }
        for (Integer r: removeIndexs) {
            chunks.get(r).dispose();
            chunks.remove((int) r);
        }
    }
    public void generateSeenChunks(){
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                boolean exists = false;
                for (Chunk c: chunks) {
                    if(c.x == x + (int) (Game.player.getPos().x / 16)){
                        if(c.z == z + (int) (Game.player.getPos().z / 16)){
                            exists = true;
                            break;
                        }
                    }
                }
                if(!exists) {
                    chunks.add(WorldGenerator.generateChunk(x + (int) (Game.player.getPos().x / 16), z + (int) (Game.player.getPos().z / 16)));
                }
            }
        }
    }
    public void handleChunkDistances() {
        unloadUnseenChunks();
        generateSeenChunks();
    }
    public Chunk getChunkAt(int x, int z){
        for (Chunk c: chunks) {
            if(c.x == x && c.z == z){
                return c;
            }
        }
        return null;
    }
    public ArrayList<Chunk> getChunks(){
        return chunks;
    }
}
