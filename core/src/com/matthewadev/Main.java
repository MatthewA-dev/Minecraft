package com.matthewadev;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.ScreenUtils;
import com.matthewadev.render.BlockRenderer;

public class Main extends ApplicationAdapter {
	ModelBatch batch;
	PerspectiveCamera camera;
	BlockRenderer renderer;
	@Override
	public void create () {
		batch = new ModelBatch();
		camera = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(1f, 1f, 1f);
		camera.lookAt(0,0,0);
		camera.near = 1f;
		camera.far = 300f;
		camera.update();

		renderer = new BlockRenderer();
		renderer.addBlock(0,0,0);
		renderer.addBlock(0,0,1);
	}

	@Override
	public void render () {
		// clear
		ScreenUtils.clear(0, 0, 0, 1);
		// opengl things
		Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// rendering
		renderer.renderBlocks(camera, batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		renderer.dispose();
	}
}
