package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public void render(SpriteBatch batch){
        batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(new Color(1f,1f,1f,1f));
        shape.rect(x0 - 5, Gdx.graphics.getHeight() - y0 - 5, width + 10, height + 10);
        shape.setColor(c);
        shape.rect(x0, Gdx.graphics.getHeight() - y0, width, height);
        shape.end();
        batch.begin();
        if (!text.equals("")) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(UIManager.font, text);
            UIManager.font.draw(batch, text, x0 + (width / 2) - (glyphLayout.width / 2), Gdx.graphics.getHeight() - y0 + (height / 2) - (glyphLayout.height / 2));
        }
    }
}
