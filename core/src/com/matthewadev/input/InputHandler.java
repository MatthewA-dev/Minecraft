package com.matthewadev.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;

// Continuous input handler (e.g. moving the player)
public class InputHandler implements InputProcessor {
    // for continuous input
    public void handleInput(){
        //System.out.println(Game.camera.direction);
        //System.out.println(Game.camera.up);
        //System.out.println("Degrees up: " + (-(float)Math.atan2(Game.camera.up.x, Game.camera.up.y)*MathUtils.radiansToDegrees + 180));
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            //Game.camera.direction.x += 0.01;
            //Game.camera.rotate(new Matrix4());
            //Game.camera.rotate(1f, 0f, 1f, 0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            //Game.camera.rotate(-1f, 0f, 1f, 0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            //Game.camera.rotate(1f, 1f, 0f, 0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            //Game.camera.rotate(-1f, 1f, 0f, 0f);

        }
        Game.camera.update();
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
        //Game.camera.rotate(0.3f * diffX, 0f, 1f, 0f); // Left to right movement
        //Game.camera.rotate(0.3f * diffX, 0f, 1f, 0f);
        //Game.camera.rotate(0.3f * diffY, 1f, 0f, 0f);
        //Game.camera.update();
        //Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
