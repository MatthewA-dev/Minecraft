package com.matthewadev.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matthewadev.game.Game;

public class Renderer2D {
    private static OrthographicCamera camera;
    private static SpriteBatch spriteBatch;
    private static BitmapFont font;
    private static float crosshairoffsetx;
    private static float crosshairoffsety;
    public static void init(){
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "+");
        crosshairoffsetx = (Gdx.graphics.getWidth() / 2f) - glyphLayout.width / 2f;
        crosshairoffsety = (Gdx.graphics.getHeight() / 2f) - glyphLayout.height / 2f;
    }
    public static void render(){
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        font.draw(spriteBatch, "+", crosshairoffsetx, crosshairoffsety);
        font.draw(spriteBatch, "X: " + Game.player.getPos().x + "\nY: " + Game.player.getPos().y + "\nZ: " + Game.player.getPos().z, 0, Gdx.graphics.getHeight());
        spriteBatch.end();
    }
}
