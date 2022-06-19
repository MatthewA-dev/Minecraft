package com.matthewadev.render;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.matthewadev.game.Game;
import com.matthewadev.game.WorldGenerator;
import com.matthewadev.physics.Physics;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    public void loadWorld(){
        File worldfile = new File("world.json");
        if(!worldfile.exists()){
            return;
        }
        JSONParser jsonParser = new JSONParser();
        try{
            FileReader reader = new FileReader("world.json");
            JSONObject worlddata = (JSONObject) jsonParser.parse(reader);
            float playerx = Float.parseFloat(worlddata.get("playerX").toString());
            float playery = Float.parseFloat(worlddata.get("playerY").toString());
            float playerz = Float.parseFloat(worlddata.get("playerZ").toString());
            Game.player.setPos(playerx, playery, playerz);
            Game.player.selectedBlock = BlockType.valueOf(worlddata.get("selected").toString());
            Game.player.isFlying = Boolean.parseBoolean(worlddata.get("isflying").toString());
            for (int i = chunks.size() - 1; i >=0 ; i--) {
                chunks.get(i).dispose();
                chunks.remove(i);
            }
            JSONArray chunkslist = (JSONArray) worlddata.get("chunks");
            for(Object chunkobj: chunkslist){
                if(chunkobj instanceof JSONObject){
                    JSONObject chunkjsonobj = (JSONObject) chunkobj;
                    Chunk c = new Chunk(Integer.parseInt(chunkjsonobj.get("x").toString()), Integer.parseInt(chunkjsonobj.get("z").toString()));
                    JSONArray blocks = (JSONArray) chunkjsonobj.get("blocks");
                    for(Object blockobject : blocks){
                        JSONObject blockjson = (JSONObject) blockobject;
                        c.addBlockWithoutCalculation(new Block(Integer.parseInt(blockjson.get("x").toString()),
                                                    Integer.parseInt(blockjson.get("y").toString()),
                                                    Integer.parseInt(blockjson.get("z").toString()),
                                                    BlockType.valueOf(blockjson.get("type").toString())));
                    }
                    c.recalculateMesh();
                    chunks.add(c);
                    Game.player.updateCam();
                }
            }
        }catch(IOException | ParseException e){
            System.out.println("Couldn't save world");
        }
    }

    public void saveWorld(){
        JSONObject world = new JSONObject();
        world.put("playerX", Game.player.getX());
        world.put("playerY", Game.player.getY());
        world.put("playerZ", Game.player.getZ());
        world.put("isflying", Game.player.isFlying);
        world.put("selected", Game.player.selectedBlock.toString());
        JSONArray chunks = new JSONArray();
        for(Chunk chunk: this.chunks){
            JSONObject chunkobj = new JSONObject();
            chunkobj.put("x", chunk.x);
            chunkobj.put("z", chunk.z);
            JSONArray blocks = new JSONArray();
            for(Block block: chunk.allBlocks){
                JSONObject blockobj = new JSONObject();
                blockobj.put("x", block.getX());
                blockobj.put("y", block.getY());
                blockobj.put("z", block.getZ());
                blockobj.put("type", block.getType().toString());
                blocks.add(blockobj);
            }
            chunkobj.put("blocks", blocks);
            chunks.add(chunkobj);
        }
        world.put("chunks",chunks);
        try{
            File file = new File("world.json");
            file.createNewFile();
            FileWriter fw = new FileWriter("world.json");
            fw.write(world.toJSONString());
            fw.flush();
            fw.close();
        }catch (IOException e){
            System.out.println("World couldn't be saved" + e);

        }
    }
}
