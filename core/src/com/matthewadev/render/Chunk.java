package com.matthewadev.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
public class Chunk {
    public final int x;
    public final int z;
    private Block[][][] blocks = new Block[128][16][16];
    private ArrayList<Block> allBlocks = new ArrayList<>();
    private Model chunkModel;
    private ModelInstance instance;
    private final Block empty = new Block(0,0,0, BlockType.EMPTY);

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
            //System.out.println(bchunkx + " " + bchunkz + " " + b.getType() + " " + b.toChunkNum() + " " + b.getX() + " " + b.getY() + " " + b.getZ());
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

/*        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        for(Block b : allBlocks) {
            modelBuilder.part("face", GL20.GL_TRIANGLES, attr, // AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
                            b.topMat).rect((float) b.getX(),(float)  b.getY(), (float)  b.getZ(),(float) b.getX(),(float)  b.getY(),(float)  b.getZ(),(float)  b.getX() + 1,(float)  b.getY(),(float)  b.getZ() + 1, (float)  b.getX() + 1,(float)  b.getY(),(float)  b.getZ() + 1,0f,1f,0f);

        }
        chunkModel = modelBuilder.end();
        instance = new ModelInstance(chunkModel);*/
    }
    public void addBlockWithoutCalculation(Block block){
        if(block != null) {
            blocks[block.getY()][Math.abs(block.getX()) % 16][Math.abs(block.getZ()) % 16] = block;
            addBlockBinSearch(block);
            //allBlocks.add(block);
        }
    }
    public void addBlock(Block block){
        addBlock(block);
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
    public void removeBlock(int x, int y, int z){
        blocks[y][x][z] = null;
        int low = 0;
        int high = allBlocks.size() - 1;
        int item = (int) (Math.pow(x, 3) + Math.pow(y, 2) + z);
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (item == allBlocks.get(mid).toChunkNum())
                allBlocks.remove(mid);
            else if (item > allBlocks.get(mid).toChunkNum())
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
            return blocks[y][Math.abs(x % 16)][Math.abs(z % 16)];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    public Block getBlockChunkCoords(int x, int y, int z){ // chunks coords
        return blocks[y][x][z];
    }
    public void dispose(){
        for(Block[][] blocks1 : blocks){
            for(Block[] blocks2 : blocks1){
                for (Block b : blocks2){
                    if(b != null){
                        b.dispose();
                    }
                }
            }
        }
    }
    public void render(ModelBatch b, Environment env){
        b.render(instance, env);
    }
}
