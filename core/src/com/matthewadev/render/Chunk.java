package com.matthewadev.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Random;

public class Chunk {
    public final int x;
    public final int z;
    private Block[][][] blocks = new Block[128][16][16];
    private ArrayList<Block> allBlocks = new ArrayList<>();

    public Chunk(int x, int y, int z){
        this.x = x;
        this.z = z;
    }
    public void recalculateMesh(){
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        for(Block b : allBlocks) {
            modelBuilder.part("face", GL20.GL_TRIANGLES, attr, // AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
                            b.topMat).rect((float) b.getX(),(float)  b.getY(), (float)  b.getZ(),(float) b.getX(),(float)  b.getY(),(float)  b.getZ(),(float)  b.getX() + 1,(float)  b.getY(),(float)  b.getZ() + 1, (float)  b.getX() + 1,(float)  b.getY(),(float)  b.getZ() + 1,0f,1f,0f);

        }
    }
    public void addBlock(Block block, int x, int y, int z){
        blocks[y][x][z] = block;
        allBlocks.add(blocks[y][x][z]);
    }
    public Block getBlock(int x, int y, int z){
        return blocks[y][x][z];
    }
    public void dispose(){
        for(Block[][] blocks1 : blocks){
            for(Block[] blocks2 : blocks1){
                for (Block b : blocks2){
                    b.dispose();
                }
            }
        }
    }
    public void render(ModelBatch b, Environment env){

    }
}
