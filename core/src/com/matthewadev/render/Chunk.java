package com.matthewadev.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import java.util.ArrayList;
import java.util.Random;
public class Chunk {
    public final int x;
    public final int z;
    private Block[][][] blocks = new Block[128][16][16];
    private ArrayList<Block> allBlocks = new ArrayList<>();
    private Model chunkModel;
    private ModelInstance instance;

    public Chunk(int x, int z){
        this.x = x;
        this.z = z;
        recalculateMesh();
    }
    // https://stackoverflow.com/q/38457356/14969155
    public void recalculateMesh(){
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        for(Block b : allBlocks) {
            MeshPartBuilder meshPartBuilder = modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.topMat);
/*
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.topMat)
                        .rect(-0.5f + b.getX(), -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0f, 0f, -1f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(-0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0f, 0f, 1f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0f, -1f, 0f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0f, 1f, 0f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                        .rect(-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -1f, 0f, 0f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.botMat)
                        .rect(0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 1f, 0f, 0f);
*/
            // yeah yeah copy pasted code plagiarism, but like look at how big this is man, i aint writing all this
            // plus, i get how it works, im just a bum who likes easy solutions
            // and i had to manually add the offset of the block, cut me some slack okay? im trying.
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.topMat)
                    .rect(-0.5f + b.getX(), -0.5f + b.getY(), -0.5f + b.getZ(), -0.5f + b.getX(), 0.5f + b.getY(), -0.5f + b.getZ(), 0.5f + b.getX(), 0.5f + b.getY(), -0.5f + b.getZ(), 0.5f + b.getX(), -0.5f + b.getY(), -0.5f + b.getZ(), 0f, 0f, -1f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                    .rect(-0.5f + b.getX(), 0.5f + b.getY(), 0.5f + b.getZ(), -0.5f + b.getX(), -0.5f + b.getY(), 0.5f + b.getZ(), 0.5f + b.getX(), -0.5f + b.getY(), 0.5f + b.getZ(), 0.5f + b.getX(), 0.5f + b.getY(), 0.5f + b.getZ(), 0f, 0f, 1f);
            modelBuilder.part("box", GL20.GL_TRIANGLES,  attr, b.sideMat)
                    .rect(-0.5f + b.getX(), -0.5f + b.getY(), 0.5f + b.getZ(), -0.5f + b.getX(), -0.5f + b.getY(), -0.5f + b.getZ(), 0.5f + b.getX(), -0.5f + b.getY(), -0.5f + b.getZ(), 0.5f + b.getX(), -0.5f + b.getY(), 0.5f + b.getZ(), 0f, -1f, 0f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                    .rect(-0.5f + b.getX(), 0.5f + b.getY(), -0.5f + b.getZ(), -0.5f + b.getX(), 0.5f + b.getY(), 0.5f + b.getZ(), 0.5f + b.getX(), 0.5f + b.getY(), 0.5f + b.getZ(), 0.5f + b.getX(), 0.5f + b.getY(), -0.5f + b.getZ(), 0f, 1f, 0f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.sideMat)
                    .rect(-0.5f + b.getX(), -0.5f + b.getY(), 0.5f + b.getZ(), -0.5f + b.getX(), 0.5f + b.getY(), 0.5f + b.getZ(), -0.5f + b.getX(), 0.5f + b.getY(), -0.5f + b.getZ(), -0.5f + b.getX(), -0.5f + b.getY(), -0.5f + b.getZ(), -1f, 0f, 0f);
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, b.botMat)
                    .rect(0.5f + b.getX(), -0.5f + b.getY(), -0.5f + b.getZ(), 0.5f + b.getX(), 0.5f + b.getY(), -0.5f + b.getZ(), 0.5f + b.getX(), 0.5f + b.getY(), 0.5f + b.getZ(), 0.5f + b.getX(), -0.5f + b.getY(), 0.5f + b.getZ(), 1f, 0f, 0f);
        }
        chunkModel = modelBuilder.end();
        instance = new ModelInstance(chunkModel);

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
    public void addBlock(Block block, int x, int y, int z){
        blocks[y][x][z] = block;
        allBlocks.add(blocks[y][x][z]);
        recalculateMesh();
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
        b.render(instance, env);
    }
}
