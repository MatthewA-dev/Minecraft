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
    public static Block empty;

    public static void init(){
        PhysicsManager.init();
        Renderer2D.init();
        camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //camera.position.set(1f, 1f, 1f);
        camera.near = 0.01f;
        camera.far = 64f;
        camera.update();
        TextureManager.loadTextures();
        empty = new Block(0,0,0, BlockType.EMPTY);
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
        player = new Player(1,5,1);
        //crenderer.addBlock(new Block(0,0,1, BlockType.STONE));
        crenderer.removeBlock(1,0,0);
        crenderer.removeBlock(19,0,0);
        crenderer.removeBlock(-1,0,-1);
        for (int i = 0; i < 32; i++) {
            crenderer.addBlock(new Block(0,0,i,BlockType.CRAFTING_TABLE));
        }
        crenderer.addBlock(new Block(1,1,0,BlockType.STONE));
        crenderer.addBlock(new Block(1,2,0,BlockType.STONE));
        crenderer.handleChunkDistances();
    }
    public static void runFrame(){
        render();
        Renderer2D.render();
        inputHandler.handleInput();
        timeSince += Gdx.graphics.getDeltaTime();
        runTick();
    }
    public static void runTick(){
        if(timeSince < 1f / 20){
            return;
        }
        //crenderer.handleChunkDistances();
        timeSince = 0;
    }
    public static void render(){
        crenderer.render(batch,env);
    }
    public static void dispose(){
        crenderer.dispose();
    }
}
