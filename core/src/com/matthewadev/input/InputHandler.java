package com.matthewadev.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.matthewadev.game.Game;

// Continuous input handler (e.g. moving the player)
public class InputHandler implements InputProcessor {
    // for continuous input
    public void handleInput(){

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        int diffX = Gdx.graphics.getWidth() / 2 - screenX;
        int diffY = Gdx.graphics.getHeight() / 2 - screenY;
        Game.camera.rotate(1f * diffX, 1f, 0f, 0f);
        Game.camera.rotate(1f * diffY, 0f, 1f, 0f);
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
