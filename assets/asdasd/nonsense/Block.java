package com.matthewadev.render;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Null;
import com.matthewadev.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import static com.matthewadev.game.Game.modelBuilder;

public class Block {
    private final int x;
    private final int y;
    private final int z;
    private final BlockType type;
    public Model model; // faces of cube
    public ModelInstance instance;
    public Material topMat = null;
    public Material sideMat = null;
    public Material botMat = null;
    public boolean isVisible = false;
    // private type type;

    public Block(int x, int y, int z, BlockType blockType){
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = blockType;

        HashMap<BlockSide, Texture> textures = TextureManager.textures.get(type);


        topMat = new Material(TextureAttribute.createDiffuse(textures.get(BlockSide.TOP))); // We will always have a top texture
        if(textures.get(BlockSide.SIDE) != null){
            sideMat = new Material(TextureAttribute.createDiffuse(textures.get(BlockSide.SIDE)));
        }else{
            sideMat = new Material(TextureAttribute.createDiffuse(textures.get(BlockSide.TOP)));
        }
        if(textures.get(BlockSide.BOTTOM) != null) {
            botMat = new Material(TextureAttribute.createDiffuse(textures.get(BlockSide.BOTTOM)));
        }else{
            botMat = new Material(TextureAttribute.createDiffuse(textures.get(BlockSide.TOP)));
        }
        //ModelBuilder modelBuilder = new ModelBuilder();
        // create the six instances
        // keeping the models so that they can be properly destroyed
/*
        models.add(modelBuilder.createBox(1f, 1f, 0.001f,
                topMat,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
        models.add(modelBuilder.createBox(1f, 1f, 0.001f,
                sideMat,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
        models.add(modelBuilder.createBox(1f, 1f, 0.001f,
                botMat,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
        instances.add(new ModelInstance(models.get(0))); // top
        instances.add(new ModelInstance(models.get(1))); // sides
        instances.add(new ModelInstance(models.get(1)));
        instances.add(new ModelInstance(models.get(1)));
        instances.add(new ModelInstance(models.get(1)));
        instances.add(new ModelInstance(models.get(2))); // bottom
*/
/*
        // setting the faces to be oriented correct
        instances.get(0).transform.setTranslation(x,y + 0.5f,z + 0.5f);
        instances.get(0).transform.rotate(1f,0f,0f, 90f);

        instances.get(1).transform.setTranslation(x + 0.5f,y,z + 0.5f);
        instances.get(1).transform.rotate(0f,1f,0f, 90f);

        instances.get(2).transform.setTranslation(x - 0.5f,y,z + 0.5f);
        instances.get(2).transform.rotate(0f,1f,0f, 90f);

        instances.get(3).transform.setTranslation(x,y,z + 1f);
        //instances.get(3).transform.rotate(0f,1f,0f, 270f);

        instances.get(4).transform.setTranslation(x,y,z);
        //instances.get(4).transform.rotate(0f,1f,0f, 90f);

        instances.get(5).transform.setTranslation(x,y - 0.5f,z + 0.5f);
        instances.get(5).transform.rotate(1f,0f,0f, 90f);*/
    }
    public void update(){
        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        boolean shouldBeVisible = false;
        modelBuilder.begin();
        if (Game.crenderer.getBlock(getX(),getY() + 1,getZ()) == null) {
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, topMat)
                    .rect(0f, 1f, 0f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f, 0f);
            shouldBeVisible = true;
        } if (Game.crenderer.getBlock(getX(),getY(),getZ() - 1) == null){
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, sideMat)
                    //.rect(0f, 0f, 0f, 1f, 0f, 0f, 1f, 1f, 0f, 0f, 1f, 0f, 0f, 0f, -1f);
                    .rect(1f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 0f, 0f, -1f);
            shouldBeVisible = true;
        } if (Game.crenderer.getBlock(getX() ,getY(),getZ() + 1) == null){
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, sideMat)
                    .rect(0f, 0f, 1f, 1f, 0f, 1f, 1f, 1f, 1f, 0f, 1f, 1f, 0f, 0f, -1f);
            //.rect(0f, 0f, 1f,// 1f, 0f, 1f, ///0.5f, 1f, 1f,//// 0f, 1f, 1f,  0f, 0f, -1f);
            shouldBeVisible = true;
        } if (Game.crenderer.getBlock(getX() - 1,getY(),getZ()) == null) {
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, sideMat)
                    .rect(0f, 0f, 0f, 0f, 0f, 1f, 0f, 1f, 1f, 0f, 1f, 0f, -1f, 0f, 0f);
            shouldBeVisible = true;
        } if (Game.crenderer.getBlock(getX() + 1,getY(),getZ()) == null){
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, sideMat)
                    .rect(1f, 0f, 1f,1f, 0f, 0f, 1f, 1f, 0f, 1f, 1f, 1f,  1f, 0f, 0f);
            shouldBeVisible = true;
        } if(Game.crenderer.getBlock(getX(),getY() - 1,getZ()) == null){
            modelBuilder.part("box", GL20.GL_TRIANGLES, attr, botMat)
                    .rect(0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f , 0f, 1f, 0f, 1f, 0f, -1f, 0f);
            shouldBeVisible = true;
        }
        isVisible = shouldBeVisible;
        model = modelBuilder.end();
        instance = new ModelInstance(model);
        instance.transform.setTranslation(getX(),getY(),getZ());
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    // Only call this method when batch is started!!!
/*    public void render(PerspectiveCamera camera, ModelBatch batch, Environment env){
        batch.render(instance, env);
    }*/
    public BlockType getType(){
        return type;
    }
/*    public void render(ModelBatch batch, Environment env){
        for(ModelInstance i: instances) {
            batch.render(i, env);
        }
    }*/
    public Vector3 toChunkCoords(){
        return convertToChunkCoords(x,y,z);
    }
    public int toChunkNum(){
        return convertChunkNum(x,y,z);
    }
    // closing
/*    public void dispose(){
        for (Model m : models) {
            m.dispose();
        }
    }*/
    public static Vector3 convertToChunkCoords(int x, int y, int z){
        int cx = x % 16;
        int cz = z % 16;
        if(cx < 0){
            cx = 16 - Math.abs(x) % 16;
        }
        if(cz < 0){
            cz = 16 - Math.abs(z) % 16;
        }
        return new Vector3(cx,y,cz);
    }
    public static int convertChunkNum(int x, int y, int z){
        Vector3 loc = convertToChunkCoords(x,y,z);
        return (int) (loc.y * Math.pow(16,2) + loc.x * 16 + loc.z);
    }
}
