package com.matthewadev.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.matthewadev.input.InputHandler;
import com.matthewadev.render.BlockRenderer;

public class Game {
    public static Player player;
    public static PerspectiveCamera camera;
    public static ModelBatch batch;
    public static BlockRenderer renderer;
    public static InputHandler inputHandler;

    public static void init(){
        // setup rendering and camera
        batch = new ModelBatch();
        camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(1f, 1f, 1f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        renderer = new BlockRenderer();
        renderer.addBlock(0,0,0);
        renderer.addBlock(0,0,1);

        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);
        //Gdx.input.setCursorCatched(true);
    }
    public static void runFrame(){
        render();
        inputHandler.handleInput();
    }
    public static void render(){
        renderer.renderBlocks(camera, batch);
    }
    public static void dispose(){
        Game.dispose();
    }
}
