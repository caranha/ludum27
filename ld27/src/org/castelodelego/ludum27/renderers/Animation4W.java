package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * This class holds 4 animations, one for each cardinal direction (UP, DOWN, LEFT, RIGHT) 
 * transparently, and returns a key frame based on the timing and the direction
 * 
 * @author caranha
 *
 */
public class Animation4W {

	
	
	enum Direction { UP, DOWN, LEFT, RIGHT};
	
	Animation up;
	Animation down;
	Animation left;
	Animation right;
	
	public Animation4W(String basename)
	{
		Gdx.app.debug("Animation4W", basename+"UP");
		up = Globals.animman.get(basename+"UP");
		down = Globals.animman.get(basename+"DOWN");
		left = Globals.animman.get(basename+"LEFT");
		right = Globals.animman.get(basename+"RIGHT");
		Gdx.app.debug("getKeyFrame:", ""+up);
	}
	
	/** 
	 * Returns a Texture Region based not only on the time, but also on a direction vector;
	 * @param dt
	 * @param dir
	 * @return
	 */
	public TextureRegion getKeyFrame(float dt, Vector2 dir)
	{
		Animation ret;
		
		if (Math.abs(dir.x) > Math.abs(dir.y))
		{ // left or right
			if (dir.x < 0)
				ret = left;
			else
				ret = right;
		}
		else
		{ // up or down
			if (dir.y > 0)
				ret = up;
			else
				ret = down;
		}
		
		return ret.getKeyFrame(dt);
	}
	
	/**
	 * Use this function if you want one specific direction without bothering 
	 * with direction vectors.
	 * @param dt
	 * @param dir
	 * @return
	 */
	public TextureRegion getKeyFrame(float dt, Direction dir)
	{
		switch(dir)
		{
		case UP:
			return up.getKeyFrame(dt);
		case DOWN: 
			return down.getKeyFrame(dt);
		case LEFT: 
			return left.getKeyFrame(dt);
		case RIGHT: 
			return right.getKeyFrame(dt);
		}
		
		return null;
	}
	
}
