package com.matthewadev.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.VertexBufferObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.matthewadev.game.Game;
import sun.security.provider.certpath.Vertex;

import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.Comparator;

public class Chunk {
    public final int x;
    public final int z;
    //private Block[][][] blocks = new Block[128][16][16];
    public ArrayList<Block> allBlocks = new ArrayList<>();
    private Model chunkModel;
    private ModelInstance instance;

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        recalculateMesh();
    }

    // https://stackoverflow.com/q/38457356/14969155
    public void recalculateMesh() {
        if(chunkModel != null){
            chunkModel.dispose();
        }
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        for (Block b : allBlocks) {
            Vector3 chunkCoords = b.toChunkCoords();
            int bchunkx = (int) chunkCoords.x;
            int bchunky = (int) chunkCoords.y;
            int bchunkz = (int) chunkCoords.z;
            // render the face if block isn't present and if its transparent
            if (getBlock(b.getX(),b.getY() + 1,b.getZ()) == null || BlockType.isTransparent(getBlock(b.getX(),b.getY() + 1,b.getZ()).getType())) {
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.topMat)
                        .rect(0f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f + bchunkx, 1f + bchunky, 1f + bchunkz, 1f + bchunkx, 1f + bchunky, 1f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f, 1f, 0f);
            } if (getBlock(b.getX(),b.getY(),b.getZ() - 1) == null || BlockType.isTransparent(getBlock(b.getX(),b.getY(),b.getZ() - 1).getType())){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        //.rect(0f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f, 0f, -1f);
                        .rect(1f + bchunkx, 0f + bchunky, 0f + bchunkz, 0f + bchunkx, 0f + bchunky, 0f + bchunkz, 0f + bchunkx, 1f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f, 0f, -1f);
            } if (getBlock(b.getX(),b.getY(),b.getZ() + 1) == null || BlockType.isTransparent(getBlock(b.getX(),b.getY(),b.getZ() + 1).getType())){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(0f + bchunkx, 0f + bchunky, 1f + bchunkz, 1f + bchunkx, 0f + bchunky, 1f + bchunkz, 1f + bchunkx, 1f + bchunky, 1f + bchunkz, 0f + bchunkx, 1f + bchunky, 1f + bchunkz, 0f, 0f, -1f);
                //.rect(0f, 0f, 1f,// 1f, 0f, 1f, ///0.5f, 1f, 1f,//// 0f, 1f, 1f,  0f, 0f, -1f);
            } if (getBlock(b.getX() - 1,b.getY(),b.getZ()) == null || BlockType.isTransparent(getBlock(b.getX() - 1,b.getY(),b.getZ()).getType())) {
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(0f + bchunkx, 0f + bchunky, 0f + bchunkz, 0f + bchunkx, 0f + bchunky, 1f + bchunkz, 0f + bchunkx, 1f + bchunky, 1f + bchunkz, 0f + bchunkx, 1f + bchunky, 0f + bchunkz, -1f, 0f, 0f);
            } if (getBlock(b.getX() + 1,b.getY(),b.getZ()) == null || BlockType.isTransparent(getBlock(b.getX() + 1,b.getY(),b.getZ()).getType())){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(1f + bchunkx, 0f + bchunky, 1f + bchunkz,1f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 1f + bchunkz,  1f, 0f, 0f);
            } if(getBlock(b.getX(),b.getY() - 1,b.getZ()) == null || BlockType.isTransparent(getBlock(b.getX(),b.getY() - 1,b.getZ()).getType())){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.botMat)
                        .rect(0f + bchunkx, 0f + bchunky, 1f + bchunkz, 0f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 0f + bchunky, 1f + bchunkz, 0f, -1f, 0f);
            }
        }
        chunkModel = modelBuilder.end();
        instance = new ModelInstance(chunkModel);
        instance.transform.translate(new Vector3(this.x * 16f,0f, this.z * 16f));

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
            return null;
        }else if(!(z >= this.z * 16 && z <= (this.z + 1) * 16 - 1)){
            return null;
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
                allBlocks.remove(mid);
                recalculateMesh();
                return;
            }
            else if (item > item2)
                low = mid + 1;
            else
                high = mid - 1;
        }
    }
    public void dispose(){
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
        //b.render(instance);
        b.render(instance, env);
    }
    public Model getModel(){
        return chunkModel;
    }
    public ModelInstance getInstance(){
        return instance;
    }
    public static int getChunkCoord(int c){
        if(c < 0){
            return (c + 1) / 16 - 1;
        }else{
            return c / 16;
        }
    }
}
