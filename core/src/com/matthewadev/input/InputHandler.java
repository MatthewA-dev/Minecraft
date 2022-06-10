package com.matthewadev.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;
import com.matthewadev.physics.Physics;
import com.matthewadev.physics.PhysicsManager;
import com.matthewadev.physics.RaycastingManager;

// Continuous input handler (e.g. moving the player)
public class InputHandler implements InputProcessor {
    private Vector3 tmp = new Vector3(); // used for camera rotation
    public float degreesPerPixel = 0.3f;
    // for continuous input
    public void handleInput(){
        float mult = 1f;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)){
            mult = 3f;
        }
        // forward and backward movement
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            Game.player.setPos(Game.player.getPos().cpy().add(Game.camera.direction.cpy().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.direction.cpy().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            Game.player.setPos(Game.player.getPos().cpy().add(Game.camera.up.cpy().crs(Game.camera.direction).nor().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.up.cpy().crs(Game.camera.direction).nor().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
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
        Physics.raycastingGood(Game.player.getPos(), Game.camera.direction, 100f);
        //Physics.getClosestIntersection(Game.camera.direction, Game.player.getPos().cpy());
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
