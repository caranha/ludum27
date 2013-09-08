package org.castelodelego.ludum27;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GdxGameMain extends Game {

	static int SCREEN_NUMBER = 6;
	
	static int SCREEN_SPLASH = 0;
	static int SCREEN_MAIN = 1;
	static int SCREEN_PLAY = 2;
	static int SCREEN_GAMEOVER = 3;
	static int SCREEN_ABOUT = 4;
	static int SCREEN_HELP = 5;
	
	static Screen[] screenlist;

	
	
	static int nextscreen;
	static boolean changescreen;
	
	// Debug text display
	
	
	
	@Override
	public void create() {		

		Gdx.app.setLogLevel(Application.LOG_NONE);
		
		

		Globals.init();
		
		// Creating global resource managers
		queueAssets();
		
		
		screenlist = new Screen[SCREEN_NUMBER];
		
		screenlist[SCREEN_SPLASH] = new SplashScreen(SCREEN_SPLASH);
		screenlist[SCREEN_MAIN] = new MainScreen();
		screenlist[SCREEN_PLAY] = new PizzaScreen();
		screenlist[SCREEN_GAMEOVER] = new GameOverScreen();
		screenlist[SCREEN_ABOUT] = new AboutScreen();
		screenlist[SCREEN_HELP] = new HelpScreen();
		
		
		setScreen(screenlist[SCREEN_SPLASH]);
		nextscreen = SCREEN_SPLASH;
		changescreen = false;

		
	}

	@Override
	public void dispose() {
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
			Gdx.app.debug("Change Screen", nextscreen+"");
		}
		
		super.render();
		
		if (this.getScreen()!=screenlist[SCREEN_SPLASH])
			Globals.updateMusic();
		
		// Rendering here renders above everything else
		// Good for rendering debug info
		
		// Uncomment for FPS
		//		Globals.batch.begin();
		//		Globals.debugtext.setColor(Color.YELLOW);
		//		Globals.debugtext.draw(Globals.batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), 5, 795);		
		//		Globals.batch.end();
		
	}

	
	/**
	 * Add all assets for loading here.
	 * 
	 */
	private void queueAssets()
	{
		Globals.manager.load("images-packed/pack.atlas", TextureAtlas.class); // packed images
		
		Globals.manager.load("music/autotracker1.ogg", Music.class);
		Globals.manager.load("music/autotracker2.ogg", Music.class);
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
