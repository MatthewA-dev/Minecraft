package com.matthewadev.render.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UIBigFont extends UIElement{
    public UIBigFont(int x0, int y0, int width, int height, Color c) {
        super(x0, y0, width, height, c);
    }
    public void render(SpriteBatch batch){
        batch.end();
        shape.setProjectionMatrix(batch.getProjectionMatrix());
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(c);
        shape.rect(x0, Gdx.graphics.getHeight() - y0, width, height);
        shape.end();
        batch.begin();
        if (!text.equals("")) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(UIManager.font, text);
            UIManager.logoFont.draw(batch, text, x0 + (width / 2) - (glyphLayout.width / 2), Gdx.graphics.getHeight() - y0 + (height / 2) - (glyphLayout.height / 2));
        }
    }
}
