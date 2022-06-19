package com.matthewadev.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.matthewadev.game.Game;
import com.matthewadev.physics.PhysicsManager;

import java.util.ArrayList;

import static com.matthewadev.game.Game.modelBuilder;

public class Chunk {
    public final int x;
    public final int z;
    //private Block[][][] blocks = new Block[128][16][16];
    private ArrayList<Block> allBlocks = new ArrayList<>();

    private ArrayList<Block> visibleBlocks = new ArrayList<>();
    private Model chunkModel;
    private ModelInstance instance;
    private BoundingBox bounds = new BoundingBox();

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        recalculateMesh();
    }

    // https://stackoverflow.com/q/38457356/14969155
    public void recalculateMesh() {
        for (int i = 0; i < allBlocks.size(); i++) {
            allBlocks.get(i).update();
        }
    }
    public void addBlockWithoutCalculation(Block block){
        if(block != null) {
            addBlockBinSearch(block);
            //allBlocks.add(block);
        }
    }

    public Block getBlock(int x, int y, int z){
        if(y >= 128 || y < 0){ // out of bounds
            return null;
        } else if (!(x >= this.x * 16 && x <= (this.x + 1) * 16 - 1)) {
            //System.out.println(Game.crenderer.getBlock(x,y,z) + " " + x + " " + y +  " " + z);
            return Game.crenderer.getBlock(x,y,z);
        } else if(!(z >= this.z * 16 && z <= (this.z + 1) * 16 - 1)){
            //System.out.println(Game.crenderer.getBlock(x,y,z) + " " + x + " " + y +  " " + z);
            return Game.crenderer.getBlock(x,y,z);
        }
        int low = 0;
        int high = allBlocks.size() - 1;
        int item = Block.convertChunkNum(x,y,z);
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int item2 = allBlocks.get(mid).toChunkNum();
            if (item == item2) {
                return allBlocks.get(mid);
            }
            else if (item > item2)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return null;
    }
    public void addBlock(Block block){
        addBlockWithoutCalculation(block);
        recalculateMesh();
    }
    // https://www.geeksforgeeks.org/binary-search/ lazy
    private void addBlockBinSearch(Block block){
        Game.crenderer.updateBlocksAroundAt(block.getX(), block.getY(), block.getZ());
        int low = 0;
        int high = allBlocks.size() - 1;
        int item = (int) block.toChunkNum();
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (item == allBlocks.get(mid).toChunkNum()) {
                allBlocks.set(mid, block);
                return;
            }
            else if (item > allBlocks.get(mid).toChunkNum()) {
                low = mid + 1;
            }
            else {
                high = mid - 1;
            }
        }
        try {
            allBlocks.add(low, block);
        }catch(IndexOutOfBoundsException e) {
            allBlocks.add(block);
        }
    }
    // chunk coords
    public void removeBlock(Block block){
        removeBlock(block.getX(), block.getY(),block.getZ());
    }
    public void removeBlock(int x, int y, int z){ // world coordinates
        int low = 0;
        int high = allBlocks.size() - 1;
        int item = Block.convertChunkNum(x,y,z);
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int item2 = allBlocks.get(mid).toChunkNum();
            if (item == item2) {
                Block b = allBlocks.get(mid);
                allBlocks.remove(mid);
                Game.crenderer.updateBlocksAroundAt(b.getX(), b.getY(), b.getZ());
                return;
            }
            else if (item > item2)
                low = mid + 1;
            else
                high = mid - 1;
        }
    }
    public void updateBlockAt(int x, int y, int z){
        int low = 0;
        int high = allBlocks.size() - 1;
        int item = Block.convertChunkNum(x,y,z);
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int item2 = allBlocks.get(mid).toChunkNum();
            if (item == item2) {
                System.out.println("asdasdasdasdawsdasdasdasdasasdasd");
                allBlocks.get(mid).update();
                return;
            }
            else if (item > item2)
                low = mid + 1;
            else
                high = mid - 1;
        }
    }
    public void dispose(){
        PhysicsManager.removeChunkFromWorld(x,z);
        chunkModel.dispose();
/*        for(Block[][] blocks1 : blocks){
            for(Block[] blocks2 : blocks1){
                for (Block b : blocks2){
                    if(b != null){
                        b.dispose();
                    }
                }
            }
        }*/
    }
    public void render(ModelBatch b, Environment env){
        for (Block block: allBlocks) {
            if(block.isVisible){
                b.render(block.instance, env);
            }
        }
        //b.render(instance, env);
    }
    public Model getModel(){
        return chunkModel;
    }
    public ModelInstance getInstance(){
        return instance;
    }
    public BoundingBox getBounds(){
        return bounds;
    }
    public static int getChunkCoord(int c){
        if(c < 0){
            return (c + 1) / 16 - 1;
        }else{
            return c / 16;
        }
    }
}
