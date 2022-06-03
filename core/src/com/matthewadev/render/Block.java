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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Block {
    private int x;
    private int y;
    private int z;
    private BlockType type;
    private ArrayList<Model> models = new ArrayList<>(); // faces of cube
    private ArrayList<ModelInstance> instances = new ArrayList<>();
    // private type type;

    public Block(int x, int y, int z, BlockType blockType){
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = blockType;

        HashMap<BlockSide, Texture> textures = TextureManager.textures.get(type);

        ModelBuilder modelBuilder = new ModelBuilder();

        Material topMat = new Material(TextureAttribute.createDiffuse(textures.get(BlockSide.TOP))); // We will always have a top texture
        Material sideMat;
        Material botMat;
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
        // create the six instances
        // keeping the models so that they can be properly destroyed
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
        instances.get(5).transform.rotate(1f,0f,0f, 90f);
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
    public void render(ModelBatch batch, Environment env){
        for(ModelInstance i: instances) {
            batch.render(i, env);
        }
    }
    // closing
    public void dispose(){
        for (Model m : models) {
            m.dispose();
        }
    }
}
