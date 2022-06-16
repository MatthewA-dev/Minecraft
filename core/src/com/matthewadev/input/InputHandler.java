package com.matthewadev.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;
import com.matthewadev.physics.Physics;
import com.matthewadev.render.BlockType;
import com.matthewadev.render.UI.Screen;
import com.matthewadev.render.UI.UIManager;

// Continuous input handler (e.g. moving the player)
public class InputHandler implements InputProcessor {
    private Vector3 tmp = new Vector3(); // used for camera rotation
    public float degreesPerPixel = 0.15f;
    // for continuous input
    public void handleInput(){
        Vector3 dir = Game.camera.direction.cpy();
        dir.y = 0;
        dir = dir.nor();
        // forward and backward movement
        float speed;
        float speedy;
        if(Game.player.isFlying) {
            speed = 25f;
            speedy = 1000f;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                dir.x = -dir.x;
                dir.z = -dir.z;
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
                //Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.direction.cpy().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                dir = Game.camera.up.cpy().crs(Game.camera.direction);
                dir.y = 0;
                dir = dir.nor();
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                dir = Game.camera.up.cpy().crs(Game.camera.direction);
                dir.y = 0;
                dir = dir.nor();
                dir.x = -dir.x;
                dir.z = -dir.z;
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                Game.player.addVel(Game.camera.up.cpy().scl(speedy * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                Game.player.addVel(Game.camera.up.cpy().scl(-speedy * Gdx.graphics.getDeltaTime()));
            }
        }else{
            speed = 20f;
            speedy = 6f;
            Vector3 velback = Game.player.getVel().cpy();
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                dir.x = -dir.x;
                dir.z = -dir.z;
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
                //Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.direction.cpy().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                dir = Game.camera.up.cpy().crs(Game.camera.direction);
                dir.y = 0;
                dir = dir.nor();
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                dir = Game.camera.up.cpy().crs(Game.camera.direction);
                dir.y = 0;
                dir = dir.nor();
                dir.x = -dir.x;
                dir.z = -dir.z;
                Game.player.addVel(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
        }

        Game.player.updatePlayerPos();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.R:
                Game.player.setPos(0f,2.8f,0.5f);
                break;
            case Input.Keys.H:
                Game.player.isFlying = !Game.player.isFlying;
                break;
            case Input.Keys.SPACE:
                if(Game.player.isOnGround) {
                    Game.player.addVel(0f, 5f, 0f);
                }
                break;
            case Input.Keys.Y:
                Game.camera.direction.set(1f,0f,0f);
                break;
            case Input.Keys.U:
                Game.camera.direction.set(-1f,0f,0f);
                break;
            case Input.Keys.I:
                Game.camera.direction.set(0f,0f,1f);
                break;
            case Input.Keys.O:
                Game.camera.direction.set(0f,0f,-1f);
                break;
            case Input.Keys.P:
                Game.camera.direction.set(.5f,0f,.5f);
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
        if(UIManager.currentScreen == Screen.GAME) {
            if (button == 0) {
                Physics.destroyBlockWhereLooking();
            } else if (button == 1) {
                Physics.addBlockWhereLooking(BlockType.CRAFTING_TABLE);
            }
            Game.camera.update();
        }else if(UIManager.currentScreen == Screen.MAIN_MENU){
            if(UIManager.b.isInside(screenX, screenY)){
                UIManager.currentScreen = Screen.GAME;
                Gdx.input.setCursorCatched(true);
            }
        }
        //Physics.getClosestIntersection(Game.camera.direction, Game.player.getPos().cpy());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { // Adapted from the libgdx FirstPersonCamera controller code
        if(UIManager.currentScreen == Screen.GAME) {
            float angleX = (Gdx.graphics.getWidth() / 2 - screenX) * degreesPerPixel;
            float angleY = (Gdx.graphics.getHeight() / 2 - screenY) * degreesPerPixel;
            Game.camera.direction.rotate(Game.camera.up, angleX);
            tmp.set(Game.camera.direction).crs(Game.camera.up).nor();
            Game.camera.direction.rotate(tmp, angleY);
            Game.camera.update();
            Gdx.input.setCursorPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        }
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
