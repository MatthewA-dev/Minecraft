package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.matthewadev.game.Game;
import com.matthewadev.render.Renderer2D;

public class UIManager {
    public static Screen currentScreen = Screen.MAIN_MENU;
    public static Button b = new Button(100,100,50,50);
    public static void render(){
        if(currentScreen == Screen.MAIN_MENU) {
            b.render();
        }else{
            Game.render();
            Renderer2D.render();
            Game.inputHandler.handleInput();
            Game.timeSince += Gdx.graphics.getDeltaTime();
            Game.runTick();
        }
    }
}
