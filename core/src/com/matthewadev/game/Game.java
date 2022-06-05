package com.matthewadev.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.matthewadev.input.InputHandler;
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
        // setup rendering and camera
        //batch = new ModelBatch();
        camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(1f, 1f, 1f);
        camera.near = 0.01f;
        camera.far = 300f;
        camera.update();
        TextureManager.loadTextures();
        batch = new ModelBatch();

        crenderer = new ChunkManager();
        //crenderer.addBlock(new Block(0,0,0,BlockType.STONE));
        //renderer = new BlockRenderer();
        crenderer.addBlock(new Block(0,0,0, BlockType.STONE));
        crenderer.addBlock(new Block(1,0,1, BlockType.STONE));
        crenderer.addBlock(new Block(-1,0,-1, BlockType.GRASS_BLOCK));
        crenderer.addBlock(new Block(1,0,-1, BlockType.DIRT));
        crenderer.addBlock(new Block(-2,0,0, BlockType.DIRT));
        crenderer.generateChunk(1,1);
        crenderer.generateChunk(2,2);
 /*       crenderer.addBlock(new Block(-1,0,-1, BlockType.DIRT));
        for (int i = 0; i < 20; i++) {
            crenderer.addBlock(new Block(i,0,i, BlockType.DIRT));
        }*/

        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        Gdx.input.setCursorCatched(true);
        // lighting
        env = new Environment();
        env.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        env.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        // player and game logic
        player = new Player(1,1,1,camera);

    }
    public static void runFrame(){
        render();
        inputHandler.handleInput();
        timeSince += Gdx.graphics.getDeltaTime();
        runTick();
    }
    public static void runTick(){
        //System.out.println((int)Game.player.getPos().x / 16 + " " + (int)Game.player.getPos().z / 16);
        //System.out.println(Game.player.getPos().cpy().scl(1f/16));
        if(timeSince < 1f / 20){
            return;
        }
        //System.out.println((int)Game.player.getPos().x + " " + (int)Game.player.getPos().z);
        //crenderer.handleChunkDistances();
        timeSince = 0;
    }
    public static void render(){
        //renderer.renderBlocks(batch, env);
        crenderer.render(batch,env);
    }
    public static void dispose(){
        renderer.dispose();
    }
}
