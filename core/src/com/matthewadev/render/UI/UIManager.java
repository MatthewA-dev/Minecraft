package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.matthewadev.game.Game;
import com.matthewadev.render.Renderer2D;

import javax.swing.*;
import java.util.ArrayList;

public class UIManager {
    public static Screen currentScreen = Screen.MAIN_MENU;
    public static BitmapFont font;
    public static ArrayList<UIElement> mainMenuItems = new ArrayList<>();
    public static SpriteBatch batch;
    private static OrthographicCamera camera;

    public static void init(){
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        batch = new SpriteBatch();

        UIElement playBtn = new Button(100, 100, 50, 50, new Color(128 / 255f, 128 / 255f, 128 / 255f, 1f), new Runnable() {
            @Override
            public void run() {
                UIManager.currentScreen = Screen.GAME;
                Gdx.input.setCursorCatched(true);
            }
        });
        playBtn.text = "Play";
        mainMenuItems.add(playBtn);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 12;
        font = generator.generateFont(parameter);
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
        }
    }
}
