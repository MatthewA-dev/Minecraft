package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {
    private final ShapeRenderer shape;
    public int x0;
    public int y0;
    public int width;
    public int height;
    public Button (int x0, int y0, int width, int height){
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
        shape = new ShapeRenderer();
    }
    public boolean isInside(int x, int y){
        if(x >= x0 && x < x + width && y >= y0 && y < y0 + height){
            return true;
        }
        return false;
    }

    public void render(){
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(x0, Gdx.graphics.getHeight() - y0, width, height);
        shape.end();
    }
}
