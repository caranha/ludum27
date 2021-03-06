package org.castelodelego.ludum27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainScreen implements Screen, GestureListener {

	Rectangle[] touchAreas;
	
	public MainScreen()
	{
		touchAreas = new Rectangle[3];
		touchAreas[0] = new Rectangle(75, 410, 270, 60);
		touchAreas[1] = new Rectangle(170, 305, 270, 60);
		touchAreas[2] = new Rectangle(60, 200, 270, 60);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.6f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Globals.srender.renderMainScreen(delta);
		
		//Globals.drender.RenderRectangles(touchAreas);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new GestureDetector(this));
		Globals.srender.reset();
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

	public Vector2 unprojectCoordinates(float x, float y)
	{
		Vector3 rawtouch = new Vector3(x, y,0);
		Globals.cam.unproject(rawtouch); 
		
		Vector2 ret = new Vector2(rawtouch.x, rawtouch.y);
		return ret;
	}
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		Globals.gc.reset();
		
		Vector2 firstTouch = unprojectCoordinates(x, y);
		
		if (touchAreas[0].contains(firstTouch))	
		{
			GdxGameMain.setScreen(GdxGameMain.SCREEN_PLAY);
			Globals.gc.reset();
			return true;
		}
		
		if (touchAreas[1].contains(firstTouch))	
		{
			GdxGameMain.setScreen(GdxGameMain.SCREEN_HELP);
			return true;
		}
		
		if (touchAreas[2].contains(firstTouch))	
		{
			GdxGameMain.setScreen(GdxGameMain.SCREEN_ABOUT);
			return true;
		}
		
		
		return false;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

}
