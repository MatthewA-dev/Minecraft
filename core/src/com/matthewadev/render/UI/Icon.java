package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matthewadev.render.BlockType;

public class Icon extends UIElement{
    public Texture texture;
    public BlockType b;
    public Icon(int x0, int y0, int width, int height, Color c) {
        super(x0, y0, width, height, c);
    }
    public Icon(int x0, int y0, int width, int height, Texture texture, BlockType b){
        super(x0, y0, width, height, new Color(0,0,0,0));
        this.texture = texture;
        this.b = b;
    }
    public void render(SpriteBatch batch){
        batch.draw(texture, x0, Gdx.graphics.getHeight() - y0, width, height);
    }
    public boolean isInside(int x, int y){
        if(x >= x0 && x < x + width && y <= y0 && y > y0 - height){
            return true;
        }
        return false;
    }
}
