package com.matthewadev.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.matthewadev.input.InputHandler;
import com.matthewadev.physics.PhysicsManager;
import com.matthewadev.render.*;

public class Game {
    public static Player player;
    public static PerspectiveCamera camera;
    public static ModelBatch batch;
    public static BlockRenderer renderer;
    public static ChunkManager crenderer;
    public static InputHandler inputHandler;
    public static Environment env;
    public static float timeSince = 0f; // time since last tick

    public static void init(){
        PhysicsManager.init();
        camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(1f, 1f, 1f);
        camera.near = 0.01f;
        camera.far = 300f;
        camera.update();
        TextureManager.loadTextures();
        batch = new ModelBatch();

        crenderer = new ChunkManager();
        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        Gdx.input.setCursorCatched(true);
        // lighting
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        // player and game logic
        player = new Player(1,1,1,camera);
        //crenderer.addBlock(new Block(0,0,1, BlockType.STONE));
        crenderer.removeBlock(1,0,0);
        crenderer.removeBlock(19,0,0);
        crenderer.removeBlock(-1,0,-1);
        crenderer.addBlock(new Block(18,6,14,BlockType.CRAFTING_TABLE));


    }
    public static void runFrame(){
        render();
        inputHandler.handleInput();
        timeSince += Gdx.graphics.getDeltaTime();
        runTick();
    }
    public static void runTick(){
        if(timeSince < 1f / 20){
            return;
        }
        crenderer.handleChunkDistances();
        timeSince = 0;
    }
    public static void render(){
        crenderer.render(batch,env);
    }
    public static void dispose(){
        crenderer.dispose();
    }
}
