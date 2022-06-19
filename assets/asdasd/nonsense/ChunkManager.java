package com.matthewadev.render;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.matthewadev.game.Game;
import com.matthewadev.game.WorldGenerator;
import com.matthewadev.physics.Physics;

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
        int chunkx = Chunk.getChunkCoord(block.getX());
        int chunkz = Chunk.getChunkCoord(block.getZ());
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
    public Block getBlock(float x, float y, float z){
        return getBlock(Physics.floorCorrectly(x), Physics.floorCorrectly(y), Physics.floorCorrectly(z));
    }
    public Block getBlock(int x, int y, int z){
        int chunkx = Chunk.getChunkCoord(x);
        int chunkz = Chunk.getChunkCoord(z);
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
            if(Math.abs(chunks.get(i).x - Chunk.getChunkCoord(Physics.floorCorrectly(Game.player.getX()))) > 2 ||
                    Math.abs(chunks.get(i).z - Chunk.getChunkCoord(Physics.floorCorrectly(Game.player.getZ()))) > 2){
                removeIndexs.add(0,i);
            }
        }
        for (Integer r: removeIndexs) {
            chunks.get(r).dispose();
            chunks.remove((int) r);
        }
    }
    public void updateBlockAt(int x, int y, int z){
        int chunkx = Chunk.getChunkCoord(x);
        int chunkz = Chunk.getChunkCoord(z);
        for (Chunk chunk : chunks){
            if(chunk.x == chunkx && chunk.z == chunkz){
                chunk.updateBlockAt(x, y, z);
                break;
            }
        }
    }
    public void updateBlocksAroundAt(int x, int y, int z){
        for(int xx = -1; xx < 2; xx += 1){
            for(int yy = -1; yy < 2; yy+= 1){
                for(int zz = -1; zz < 2; zz += 1){
                    try {
                        updateBlockAt(x + xx, y + yy, z + zz);
                    }catch(NullPointerException e){}
                }
            }
        }

    }
    public void generateSeenChunks(){
        for(int x = -2; x <= 2; x++){
            for(int z = -2; z <= 2; z++){
                boolean exists = false;
                for (Chunk c: chunks) {
                    if(c.x == x + Chunk.getChunkCoord(Physics.floorCorrectly(Game.player.getX()))){
                        if(c.z == z + Chunk.getChunkCoord(Physics.floorCorrectly(Game.player.getZ()))){
                            exists = true;
                            break;
                        }
                    }
                }
                if(!exists) {
                    chunks.add(WorldGenerator.generateChunk(x + Chunk.getChunkCoord(Physics.floorCorrectly(Game.player.getX())),
                            z + Chunk.getChunkCoord(Physics.floorCorrectly(Game.player.getZ()))));
                }
            }
        }
        for (int i = 0; i < chunks.size(); i++) {
            chunks.get(i).recalculateMesh();
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
