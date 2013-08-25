package org.castelodelego.ludum27;

import java.util.Random;

import org.castelodelego.ludum27.gamemodel.GameContext;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Contains all the statically accessible global variables.
 * 
 * @author caranha
 *
 */
public class Globals {
		
	public static AssetManager manager;
	public static AnimationManager animman;
	public static SpriteBatch batch;
	public static GameContext gc;
	public static OrthographicCamera cam;
	public static Random dice;
	
	public static int maxscore; // maximum score so far in this session;
	
	
	static void init()
	{
		batch = new SpriteBatch();
		animman = new AnimationManager();
		manager = new AssetManager();
		
		gc = new GameContext();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Constants.SCREEN_W, Constants.SCREEN_H);
		
		dice = new Random();
		
		maxscore = loadMaxScore();
	}
	
	/**
	 * Sets the maximum score, and save it if necessary;
	 * @param score
	 */
	public void setMaxScore(int score)
	{
		
	}
	
	
	/** 
	 * Loads the maximum score from the user's preferences;
	 * @return
	 */
	public static int loadMaxScore()
	{
		return 0;
	}

}
