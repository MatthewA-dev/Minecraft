package com.matthewadev.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class Block {
    private int x;
    private int y;
    private int z;
    private BlockType type;
    //private ArrayList<Model> models = new ArrayList<>(); // faces of cube
    //private ArrayList<ModelInstance> instances = new ArrayList<>();
    public Material topMat = null;
    public Material sideMat = null;
    public Material botMat = null;
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
        // transparency
        if(BlockType.isTransparent(blockType)) {
            topMat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            sideMat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            botMat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
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
