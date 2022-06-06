package com.matthewadev.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.matthewadev.physics.PhysicsManager;

import java.util.ArrayList;

public class Chunk {
    public final int x;
    public final int z;
    private Block[][][] blocks = new Block[128][16][16];
    private ArrayList<Block> allBlocks = new ArrayList<>();
    private Model chunkModel;
    private ModelInstance instance;
    private final Block empty = new Block(0,0,0, BlockType.EMPTY);
    private BoundingBox bounds = new BoundingBox();

    public Chunk(int x, int z) {
        this.x = x;
        this.z = z;
        recalculateMesh();
    }

    // https://stackoverflow.com/q/38457356/14969155
    public void recalculateMesh() {
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        for (Block b : allBlocks) {

            // yeah yeah copy pasted code plagiarism, but like look at how big this is man, i aint writing all this
            // plus, i get how it works, im just a bum who likes easy solutions
            // and i had to manually add the offset of the block, cut me some slack okay? im trying.
            Vector3 chunkCoords = b.toChunkCoords();
            int bchunkx = (int) chunkCoords.x;
            int bchunky = (int) chunkCoords.y;
            int bchunkz = (int) chunkCoords.z;
            if (getBlock(b.getX(),b.getY() + 1,b.getZ()) == null) {
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.topMat)
                        .rect(0f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f + bchunkx, 1f + bchunky, 1f + bchunkz, 1f + bchunkx, 1f + bchunky, 1f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f, 1f, 0f);
            } if (getBlock(b.getX(),b.getY(),b.getZ() - 1) == null){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        //.rect(0f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f, 0f, -1f);
                        .rect(1f + bchunkx, 0f + bchunky, 0f + bchunkz, 0f + bchunkx, 0f + bchunky, 0f + bchunkz, 0f + bchunkx, 1f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 0f, 0f, -1f);
            } if (getBlock(b.getX(),b.getY(),b.getZ() + 1) == null){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(0f + bchunkx, 0f + bchunky, 1f + bchunkz, 1f + bchunkx, 0f + bchunky, 1f + bchunkz, 1f + bchunkx, 1f + bchunky, 1f + bchunkz, 0f + bchunkx, 1f + bchunky, 1f + bchunkz, 0f, 0f, -1f);
                //.rect(0f, 0f, 1f,// 1f, 0f, 1f, ///0.5f, 1f, 1f,//// 0f, 1f, 1f,  0f, 0f, -1f);
            } if (getBlock(b.getX() - 1,b.getY(),b.getZ()) == null) {
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(0f + bchunkx, 0f + bchunky, 0f + bchunkz, 0f + bchunkx, 0f + bchunky, 1f + bchunkz, 0f + bchunkx, 1f + bchunky, 1f + bchunkz, 0f + bchunkx, 1f + bchunky, 0f + bchunkz, -1f, 0f, 0f);
            } if (getBlock(b.getX() + 1,b.getY(),b.getZ()) == null){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(1f + bchunkx, 0f + bchunky, 1f + bchunkz,1f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 0f + bchunkz, 1f + bchunkx, 1f + bchunky, 1f + bchunkz,  1f, 0f, 0f);
            } if(getBlock(b.getX(),b.getY() - 1,b.getZ()) == null){
                modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.botMat)
                        .rect(0f + bchunkx, 0f + bchunky, 1f + bchunkz, 0f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 0f + bchunky, 0f + bchunkz, 1f + bchunkx, 0f + bchunky, 1f + bchunkz, 0f, -1f, 0f);
            }
}
        chunkModel = modelBuilder.end();
        instance = new ModelInstance(chunkModel);
        instance.transform.translate(new Vector3(this.x * 16f,0f, this.z * 16f));
        chunkModel.calculateBoundingBox(bounds);

        PhysicsManager.addChunkToWorld(chunkModel,instance.transform,x,z, allBlocks);
    }
    public void addBlockWithoutCalculation(Block block){
        if(block != null) {
            Vector3 ccoord = block.toChunkCoords();
            blocks[(int) ccoord.y][(int) ccoord.x][(int) ccoord.z] = block;
            addBlockBinSearch(block);
            //allBlocks.add(block);
        }
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
        Vector3 chunkCoords = Block.convertToChunkCoords(x,y,z);
        blocks[(int) chunkCoords.y][(int) chunkCoords.x][(int) chunkCoords.z] = null;
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
    public Block getBlock(int x, int y, int z){ // world coords
        try {
            if(y >= 128 || y < 0 || !(x >= this.x * 16 && x <= (this.x + 1) * 16 - 1) || !(z >= this.z * 16 && z <= (this.z + 1) * 16 - 1)){ // out of bounds
                return null;
            }
            Vector3 v = Block.convertToChunkCoords(x,y,z);
            return blocks[(int) v.y][(int) v.x][(int) v.z];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public Block getBlockChunkCoords(int x, int y, int z){ // chunks coords
        return blocks[y][x][z];
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
        b.render(instance, env);
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
        return (int) Math.floor(c / 16f);
    }
}
