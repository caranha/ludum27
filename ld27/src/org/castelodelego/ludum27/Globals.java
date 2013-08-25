package org.castelodelego.ludum27;

import java.util.Random;

import org.castelodelego.ludum27.gamemodel.GameContext;
import org.castelodelego.ludum27.renderers.DebugRenderer;
import org.castelodelego.ludum27.renderers.SpriteRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Contains all the statically accessible global variables.
 * 
 * @author caranha
 *
 */
public class Globals {
		
	final static public int client_anims = 3;
	
	public static Preferences scoreloader;
	
	public static AssetManager manager;
	public static AnimationManager animman;
	public static SpriteBatch batch;
	public static GameContext gc;
	public static OrthographicCamera cam;
	public static Random dice;
	public static SpriteRenderer srender;
	public static DebugRenderer drender;
	
	public static BitmapFont debugtext;
	
	public static int maxscore; // maximum score so far in this session;
	
	public static Music currentSong;
	public static String songNames[] = { "music/autotracker1.ogg","music/autotracker2.ogg"};
	
	static void init()
	{
		debugtext = new BitmapFont();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Constants.SCREEN_W, Constants.SCREEN_H);
		
		batch = new SpriteBatch();
		animman = new AnimationManager();
		manager = new AssetManager();
		srender = new SpriteRenderer();
		drender = new DebugRenderer();
		
		gc = new GameContext();
		

		
		dice = new Random();
		
		scoreloader = Gdx.app.getPreferences("Scores");
		maxscore = loadMaxScore();
		
		currentSong = null;
	}
	
	/**
	 * Sets the maximum score, and save it if necessary;
	 * @param score
	 */
	public static void setMaxScore(int score)
	{
		if (score > maxscore)
		{
			maxscore = score;
			scoreloader.putInteger("maxscore", maxscore);
			scoreloader.flush();
		}
	}
	
	
	/** 
	 * Loads the maximum score from the user's preferences;
	 * @return
	 */
	public static int loadMaxScore()
	{
		return (scoreloader.getInteger("maxscore", 0));		
	}
	
	public static void updateMusic()
	{
		if (currentSong == null || (!currentSong.isPlaying()))
		{
			currentSong = manager.get(songNames[dice.nextInt(songNames.length)],Music.class);
			currentSong.play();
		}			
	}
		
}
