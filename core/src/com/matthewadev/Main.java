package com.matthewadev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.ScreenUtils;
import com.matthewadev.game.Game;

public class Main extends ApplicationAdapter {
	@Override
	public void create () {
		//Bullet.init(true);
		Game.init();
	}

	@Override
	public void render () {
		// clear
		ScreenUtils.clear(0, 0, 0, 1);
		// opengl things
		Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Game.runFrame();
	}
	
	@Override
	public void dispose () {
		Game.dispose();
	}
}
