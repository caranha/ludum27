package org.castelodelego.ludum27;

import org.castelodelego.ludum27.renderers.DebugRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * This screen coordinates the input, updates and rendering of the main gameplay screen
 * @author caranha
 *
 */
public class PizzaScreen implements Screen {

	DebugRenderer drender;
	
	public PizzaScreen()
	{
		drender = new DebugRenderer();
	}
	
	
	@Override
	public void render(float delta) {
		// Clear Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		// process input
		
		
		// update game state
		Globals.gc.update(delta);
	
		// render everything
		drender.render(Globals.gc);
		
		// test if we need to leave this place
	
	
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Globals.gc.run();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
