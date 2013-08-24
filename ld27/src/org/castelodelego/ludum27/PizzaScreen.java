package org.castelodelego.ludum27;

import org.castelodelego.ludum27.gamemodel.Bronks;
import org.castelodelego.ludum27.renderers.DebugRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

/**
 * This screen coordinates the input, updates and rendering of the main gameplay screen
 * @author caranha
 *
 */
public class PizzaScreen implements Screen, InputProcessor {

	DebugRenderer drender;
	Vector2 firstTouch;
	
	public PizzaScreen()
	{
		drender = new DebugRenderer();
		firstTouch = new Vector2();
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
		Gdx.input.setInputProcessor(this); // Can I call this for every screen?
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




	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.app.getGraphics().getHeight() - screenY;
		firstTouch.set(screenX,screenY);
		return true;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenY = Gdx.app.getGraphics().getHeight() - screenY;
		
		Vector2 newTouch = new Vector2(screenX, screenY);
		int target;
		
		// TODO: Test if we are dismissing the "Show Pizza" screen
		
		// Test if we are touching an ingredient
		target = Globals.gc.restaurant.getIngredientIndex(firstTouch);
		if (target != -1 && target == Globals.gc.restaurant.getIngredientIndex(newTouch))
		{
			Gdx.app.debug("Interface", "Touched Ingredient "+target);
			Globals.gc.sendCookCommand(Bronks.ACTION_INGREDIENT, target);
			return true;
		}
		
		// Test if we are touching an oven
		target = Globals.gc.restaurant.getOvenIndex(firstTouch);
		if (target != -1 && target == Globals.gc.restaurant.getOvenIndex(newTouch))
		{
			Gdx.app.debug("Interface", "Touched Oven "+target);
			Globals.gc.sendCookCommand(Bronks.ACTION_OVEN, target);
			return true;
		}
		
		
		
		
		// TODO: Test if we are touching a pizza array (tap/drag)
		// TODO: Test if we are touching a client
		
		
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screenY = Gdx.app.getGraphics().getHeight() - screenY;
		
		Gdx.app.log("Dragged", screenX+" "+screenY);
		return true;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
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


}
