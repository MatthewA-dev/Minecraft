package com.matthewadev.input;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.matthewadev.game.Game;
import com.matthewadev.physics.Physics;
import com.matthewadev.render.BlockType;

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
            speed = 1.2f;
            speedy = 5f;
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
            Game.player.reduceVelocity();
        }else{
            speed = 6f;
            speedy = 6f;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                Game.player.addPos(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                dir.x = -dir.x;
                dir.z = -dir.z;
                Game.player.addPos(dir.scl(speed * Gdx.graphics.getDeltaTime()));
                //Game.player.setPos(Game.player.getPos().cpy().sub(Game.camera.direction.cpy().scl(mult * 2f * Gdx.graphics.getDeltaTime())));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                dir = Game.camera.up.cpy().crs(Game.camera.direction);
                dir.y = 0;
                dir = dir.nor();
                Game.player.addPos(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                dir = Game.camera.up.cpy().crs(Game.camera.direction);
                dir.y = 0;
                dir = dir.nor();
                dir.x = -dir.x;
                dir.z = -dir.z;
                Game.player.addPos(dir.scl(speed * Gdx.graphics.getDeltaTime()));
            }

            Game.player.addVel(0,-1f * Gdx.graphics.getDeltaTime(),0);
        }
        Game.player.updatePlayerPos();
    }

    @Override
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.R:
                Game.player.setPos(1f,1f,1f);
                break;
            case Input.Keys.H:
                Game.player.isFlying = !Game.player.isFlying;
                break;
            case Input.Keys.SPACE:
                Game.player.addVel(0f,0.1f,0f);
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
        if(button == 0){
            Physics.destroyBlockWhereLooking();
        } else if (button == 1) {
            Physics.addBlockWhereLooking(BlockType.CRAFTING_TABLE);
        }
        Game.camera.update();
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
