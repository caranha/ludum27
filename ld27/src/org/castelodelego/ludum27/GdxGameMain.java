package org.castelodelego.ludum27;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GdxGameMain extends Game {

	static int SCREEN_NUMBER = 2;
	static int SCREEN_SPLASH = 0;
	static int SCREEN_MAIN = 1;
	
	static Screen[] screenlist;

	public static AssetManager manager;
	public static AnimationManager animman;
	
	static int nextscreen;
	static boolean changescreen;
	
	// Debug text display
	BitmapFont debugtext;
	public static SpriteBatch batch;
	
	@Override
	public void create() {		

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		debugtext = new BitmapFont();
		batch = new SpriteBatch();
		
		// Creating global resource managers
		queueAssets();
		animman = new AnimationManager();
		
		screenlist = new Screen[SCREEN_NUMBER];
		screenlist[SCREEN_SPLASH] = new SplashScreen(SCREEN_SPLASH);
		screenlist[SCREEN_MAIN] = new MainScreen();
		setScreen(screenlist[SCREEN_SPLASH]);
		nextscreen = SCREEN_SPLASH;
		changescreen = false;

		
	}

	@Override
	public void dispose() {
		debugtext.dispose();
		super.dispose();
	}

	@Override
	/**
	 * Global Render call for "game". Things rendering here should be rendered in any screen.
	 */
	public void render() {		
		if (changescreen)
		{
			changescreen = false;
			setScreen(screenlist[nextscreen]);
		}
		
		super.render();
		
		
		// Rendering here renders above everything else
		// Good for rendering debug info
		// TODO: Test if we are in debug mode
		batch.begin();
		debugtext.setColor(Color.YELLOW);
		debugtext.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 5, 795);		
		batch.end();
		
	}

	
	/**
	 * Add all assets for loading here.
	 * 
	 */
	private void queueAssets()
	{
		manager = new AssetManager();
		
		// manager.load("images-packed/pack.atlas", TextureAtlas.class); // packed images
	}
	
	/**
	 * static method for changing screens
	 * @param index - internal number of the screen to switch to.
	 */
	static public void setScreen(int index)
	{
		changescreen = true;
		nextscreen = index;
	}
	
	/**
	 * Returns a screen for direct manipulation
	 * (still needs to be cast on the other side, and the other side needs to know what kind of screen it is)
	 * TODO: make a new screen interface with a "reset" function
	 * @param index
	 * @return
	 */
	static public Screen getScreen(int index)
	{
		return screenlist[index];
	}
	
	/**
	 * Gets the static spritebatch for this game
	 * @return
	 */
	public static SpriteBatch getBatch()
	{
		return batch;
	}
	
	/*
	 * The methods below are super-methods for Game. I can override 
	 * this methods as needed to create behaviors that are done in all screens.
	 */
//	@Override
//	public void resize(int width, int height) {
//	}
//
//	@Override
//	public void pause() {
//	}
//
//	@Override
//	public void resume() {
//	}
}
