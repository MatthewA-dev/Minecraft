package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button extends UIElement{
    public Runnable func;
    public Button (int x0, int y0, int width, int height, Color c, Runnable func){
        super(x0,y0,width,height,c);
        this.func = func;
    }
    public boolean isInside(int x, int y){
        if(x >= x0 && x < x + width && y <= y0 && y > y0 - height){
            return true;
        }
        return false;
    }
}
