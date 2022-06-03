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
    private Vector3 tmp = new Vector3(); // used for camera rotation
    public float degreesPerPixel = 0.3f;
    // for continuous input
    public void handleInput(){
        // forward and backward movement
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
/*            Game.player.getPos().cpy().add(Game.camera.direction.cpy().scl(0.1f));
            Vector3 pos = Game.player.getPos();
            Vector3 looking = Game.camera.direction;
            System.out.println(pos);
            Game.player.setPos(pos.x + looking.x * 0.1f, pos.y + looking.y * 0.1f, pos.z + looking.z * 0.1f);
  */        Game.player.setPos(Game.player.getPos().cpy().add(Game.camera.direction.cpy().scl(0.1f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.direction.cpy().scl(0.1f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Game.player.setPos(Game.player.getPos().cpy().add(Game.camera.up.cpy().crs(Game.camera.direction).scl(0.1f)));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.up.cpy().crs(Game.camera.direction).scl(0.1f)));
        }
        Game.camera.update();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.R:
                Game.player.setPos(1f,1f,1f);
                break;
        }
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
    public boolean mouseMoved(int screenX, int screenY) { // Adapted from the libgdx FirstPersonCamera controller code
        float angleX = (Gdx.graphics.getWidth() / 2 - screenX) * degreesPerPixel;
        float angleY = (Gdx.graphics.getHeight() / 2 - screenY)  * degreesPerPixel;
        Game.camera.direction.rotate(Game.camera.up, angleX);
        tmp.set(Game.camera.direction).crs(Game.camera.up).nor();
        Game.camera.direction.rotate(tmp, angleY);
        Game.camera.update();
        Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
