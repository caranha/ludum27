package org.castelodelego.ludum27;

import org.castelodelego.ludum27.gamemodel.CookBot;
import org.castelodelego.ludum27.renderers.DebugRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
		Gdx.gl.glClearColor(0.4f, 0.4f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		// process input
		
		
		// update game state
		Globals.gc.update(delta);
	
		// render everything
		Globals.srender.renderGameScreen(delta);
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
		Globals.srender.reset();
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


	public Vector2 unprojectCoordinates(int x, int y)
	{
		Vector3 rawtouch = new Vector3(x, y,0);
		Globals.cam.unproject(rawtouch); 
		
		Vector2 ret = new Vector2(rawtouch.x, rawtouch.y);
		return ret;
	}
	

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		Gdx.app.debug("raw touch", screenX + " " + screenY);
		
		firstTouch.set(unprojectCoordinates(screenX, screenY));
		
		Gdx.app.debug("unprojected Touch", firstTouch.x + " " + firstTouch.y);
		
		return true;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 newTouch = unprojectCoordinates(screenX, screenY);
		
		int target;
		
		// TODO: Test if we are dismissing the "Show Pizza" screen
		
		// Test if we are touching an ingredient
		target = Globals.gc.restaurant.getIngredientIndex(firstTouch);
		if (target != -1 && target == Globals.gc.restaurant.getIngredientIndex(newTouch))
		{
			Gdx.app.log("Interface", "Touched Ingredient "+target);
			Globals.gc.sendCookCommand(CookBot.ACTION_INGREDIENT, target);
			return true;
		}
		
		// Test if we are touching an oven
		target = Globals.gc.restaurant.getOvenIndex(firstTouch);
		if (target != -1 && target == Globals.gc.restaurant.getOvenIndex(newTouch))
		{
			Gdx.app.log("Interface", "Touched Oven "+target);
			Globals.gc.sendCookCommand(CookBot.ACTION_OVEN, target);
			return true;
		}
		
		// Test if we are touching a pizza array (tap/drag)
		target = Globals.gc.restaurant.getTrayIndex(firstTouch);
		if (target != -1 && Globals.gc.restaurant.trayHasPizza(target))
		{
			if (Globals.gc.restaurant.getTrayIndex(newTouch) == target) // Tapping the tray
			{
				// TODO: show pizza window
				Gdx.app.log("Interface","Pizza in Tray "+target+": "+Globals.gc.restaurant.getPizzaFromTray(target).infoText());
				return true;
			}
			
			int table = Globals.gc.restaurant.getTableIndex(newTouch);
			if (table != -1) // Dragging to a table
			{
				if (Globals.gc.restaurant.isTableOccupied(table))
				{
					Globals.gc.sendServerCommands(target,table);
					Gdx.app.log("Interface", "Server Orderd: "+target+","+table);
					return true;
				}
				// TODO: error message/sound: table is empty
			}
			
			// TODO: dragging to the trash
			
		}
		
		// Test if we are touching a client (info)
		target = Globals.gc.restaurant.getTableIndex(firstTouch);
		if (target != -1 && Globals.gc.restaurant.getTableIndex(newTouch)==target)
		{
			Gdx.app.log("Interface", "Table Touched: "+target);
			if (Globals.gc.restaurant.isTableOccupied(target))
			{
				Gdx.app.log("Interface","Client at table "+target+" wants: "+Globals.gc.restaurant.getClientAtTable(target).pizzaString());
			}
		}
		
		
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		screenY = Gdx.app.getGraphics().getHeight() - screenY;
		
		//Gdx.app.log("Dragged", screenX+" "+screenY);
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
