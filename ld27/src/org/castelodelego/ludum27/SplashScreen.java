package org.castelodelego.ludum27;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class SplashScreen implements Screen {
	
	public int ID;

	// Variables for drawing the splash screen
	OrthographicCamera camera;
	ShapeRenderer lineDrawer;
	SpriteBatch batch;
	Texture splashimg;	
	float time;
	float fade;
	
	boolean loaddone;
	boolean loadAnimDone;
	
	public SplashScreen(int myid)
	{
		ID = myid;
		
		loaddone = false;
		loadAnimDone = false;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.SCREEN_W, Constants.SCREEN_H);
		
		lineDrawer = new ShapeRenderer();
		
		// Loads the splash image (Chooses between the horizontal one and the vertical one)
		if (Gdx.app.getGraphics().getHeight() > Gdx.app.getGraphics().getWidth())
			splashimg = new Texture(Gdx.files.internal("images/backgrounds/splash_vert.png")); 
		else
			splashimg = new Texture(Gdx.files.internal("images/backgrounds/splash_hor.png")); 
		fade = 0;
		
		batch = GdxGameMain.getBatch();
	}
	
	
	@Override
	public void render(float delta) {
		
		// Clear the screen
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Set loading progress variables
		loaddone = GdxGameMain.manager.update(); // true if all loading is finished	
		//	loadprogress = GdxGameMain.manager.getProgress(); // 0-1 loading progress, if I need a loading bar
		
		// load animations
		if (loaddone == true && loadAnimDone == false)
		{	
			loadAnimDone = true;
			//loadAnimDone = GdxGameMain.animman.loadAnimations(GdxGameMain.manager.get("images-packed/pack.atlas", TextureAtlas.class));
		}
		
		// splash screen fade crontrol
		time = time+delta;		
		if (time < 0.5)
		{
			fade = time*2;
		}
		if (time > 1.5 && loaddone && loadAnimDone)
		{
			fade = fade - delta*3;
		}
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(splashimg, 0,0);
		batch.end();
		
		
		// Drawing Fade
		if (fade < 1.0f)
		{
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			lineDrawer.setProjectionMatrix(camera.combined);
			lineDrawer.begin(ShapeType.Filled);
			lineDrawer.setColor(1f, 1f, 1f, 1-fade);
			lineDrawer.rect(0, 0, Constants.SCREEN_W, Constants.SCREEN_H);		
			lineDrawer.end();
		}
		// End Drawing Fade
		

		if ((fade <= 0) && (loaddone))
			GdxGameMain.setScreen(GdxGameMain.SCREEN_MAIN);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
		splashimg.dispose();
	}

}