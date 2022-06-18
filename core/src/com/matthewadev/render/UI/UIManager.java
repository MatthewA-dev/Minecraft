package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.matthewadev.game.Game;
import com.matthewadev.render.*;

import javax.swing.*;
import java.util.ArrayList;

public class UIManager {
    public static Screen currentScreen = Screen.MAIN_MENU;
    public static BitmapFont font;
    public static BitmapFont logoFont;
    public static ArrayList<UIElement> mainMenuItems = new ArrayList<>();
    public static ArrayList<UIElement> pausedItems = new ArrayList<>();
    public static ArrayList<UIElement> controlItems = new ArrayList<>();
    public static ArrayList<UIElement> inventoryItems = new ArrayList<>();
    public static SpriteBatch batch;
    private static OrthographicCamera camera;

    public static void init(){
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        batch = new SpriteBatch();
        // MAIN MENU
        UIElement playBtn = new Button(360, 400, 360, 75, new Color(0,0,0, 1f), new Runnable() {
            @Override
            public void run() {
                UIManager.currentScreen = Screen.GAME;
                Gdx.input.setCursorCatched(true);
            }
        });
        UIElement logo = new UIBigFont(290, 100, 360, 50, new Color(0,0,0, 1f));

        playBtn.text = "Play";
        logo.text = "MINECRAFT";

        mainMenuItems.add(logo);
        mainMenuItems.add(playBtn);
        // PAUSED MENU
        UIElement returnButton = new Button(360, 400, 360, 75, new Color(0,0,0, 1f), new Runnable() {
            @Override
            public void run() {
                UIManager.currentScreen = Screen.GAME;
                Gdx.input.setCursorCatched(true);
            }
        });
        returnButton.text = "RETURN";
        UIElement controlsButton = new Button(360, 500, 360, 75, new Color(0,0,0, 1f), new Runnable() {
            @Override
            public void run() {
                UIManager.currentScreen = Screen.CONTROL_MENU;
            }
        });
        controlsButton.text = "CONTROLS";
        //
        UIElement paused = new UIBigFont(310, 100, 360, 50, new Color(0,0,0, 1f));
        paused.text = "PAUSED";
        UIElement menu = new Button(360, 600, 360, 75, new Color(0,0,0, 1f), new Runnable() {
            @Override
            public void run() {
                UIManager.currentScreen = Screen.MAIN_MENU;
            }
        });

        menu.text = "MAIN MENU";
        pausedItems.add(paused);
        pausedItems.add(controlsButton);
        pausedItems.add(returnButton);
        pausedItems.add(menu);
        // CONTROLS MENU
        UIElement returnPausedButton = new Button(360, 500, 360, 75, new Color(0,0,0, 1f), new Runnable() {
            @Override
            public void run() {
                UIManager.currentScreen = Screen.PAUSED;
            }
        });
        returnPausedButton.text = "RETURN";
        UIElement howToPlay = new UIElement(0, 400, 1080, 500,new Color(0,0,0, 1f));
        howToPlay.text = "Movement: W, A, S, D\n Looking around: Mouse\n Inventory: E\n Return to origin: R\n Toggle Fly: H";

        UIElement controls = new UIBigFont(300, 100, 360, 50, new Color(0,0,0, 1f));
        controls.text = "CONTROLS";

        controlItems.add(howToPlay);
        controlItems.add(controls);
        controlItems.add(returnPausedButton);
        // INVENTORY
        int currentX = 0;
        int currentY = 0;
        int offsetX = 125;
        int offsetY = 50;
        for (BlockType b: BlockType.values()) {
            if(b == BlockType.EMPTY){
                continue;
            }
            Texture t;
            t = TextureManager.textures.get(b).get(BlockSide.SIDE);
            if(t == null){
                t = TextureManager.textures.get(b).get(BlockSide.TOP);
            }
            Icon i = new Icon(offsetX + 64 * currentX,Gdx.graphics.getHeight() - 64 * currentY - offsetY, 64, 64, t, b);
            if(currentX > 11){
                currentY += 1;
                currentX = 0;
            }else{
                currentX += 1;
            }
            inventoryItems.add(i);
        }
        UIElement inventoryLabel = new UIElement(0,100,1080,100,new Color(0,0,0,0));
        inventoryLabel.text = "Selected Block: " + Game.player.selectedBlock.name().toLowerCase().replace("_", " ");
        inventoryItems.add(inventoryLabel);


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        FreeTypeFontGenerator.FreeTypeFontParameter bigparm = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bigparm.size = 48;
        logoFont = generator.generateFont(bigparm);
        generator.dispose();
    }
    public static void render(){
        if(currentScreen == Screen.MAIN_MENU) {
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (UIElement e: mainMenuItems) {
                e.render(batch);
            }
            batch.end();
        }else if(currentScreen == Screen.GAME){
            Game.render();
            Renderer2D.render();
            Game.inputHandler.handleInput();
            Game.timeSince += Gdx.graphics.getDeltaTime();
            Game.runTick();
        }else if(currentScreen == Screen.PAUSED){
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (UIElement e: pausedItems) {
                e.render(batch);
            }
            batch.end();
        }else if(currentScreen == Screen.CONTROL_MENU){
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (UIElement e: controlItems) {
                e.render(batch);
            }
            batch.end();
        }else if(currentScreen == Screen.INVENTORY){
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            for (UIElement e: inventoryItems) {
                e.render(batch);
            }
            batch.end();
        }
    }
}
