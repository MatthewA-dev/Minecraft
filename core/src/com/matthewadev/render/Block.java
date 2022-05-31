package com.matthewadev.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class Block {
    private int x;
    private int y;
    private int z;

    private Model model;
    private ModelInstance instance;
    // private type type;

    public Block(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(1f, 1f, 1f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        Vector3 pos = new Vector3(new float[]{(float) x, (float) y, (float) z});
        instance.transform.setTranslation(pos);
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
    public void render(PerspectiveCamera camera, ModelBatch batch){
        batch.render(instance);
    }
    // closing
    public void dispose(){
        model.dispose();
    }
}
