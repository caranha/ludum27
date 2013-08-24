package org.castelodelego.ludum27.gamemodel;

import java.util.ArrayList;
import java.util.Collection;

import com.badlogic.gdx.math.Vector2;

public class Walker {

	private Vector2 pos;
	Vector2 dir;
	float speed = 20;
	float size = 20;
	String sprite_tag;
	
	private ArrayList<Vector2> move_goals;
	
	
	public Walker(Vector2 pos)
	{
		this.pos = pos.cpy();
		this.dir = new Vector2();
		move_goals = new ArrayList<Vector2>();
	}
	
	public void resetGoals()
	{
		move_goals.clear();
	}
	
	public void setGoals(Collection <? extends Vector2> goals)
	{
		move_goals.addAll(goals);
	}
	
	/**
	 * If we have a goal, move towards that goal. If not, return false.
	 */
	public boolean move(float dt)
	{
		// no goals, no move
		if (move_goals.isEmpty())
			return false;

		// move towards the goal
		dir.set(move_goals.get(0).cpy().sub(pos));
		pos.add((dir.nor()).scl(speed*dt));
		
		// check if we reached the goal
		if (pos.dst(move_goals.get(0).x,move_goals.get(0).y) <= speed*dt) // goal was reached
		{
			move_goals.remove(0);
		}
		
		return true; // still moving
	}
	
	public void setdir(Vector2 d)
	{
		dir.set(d);
	}
	
	public void setpos(Vector2 d)
	{
		pos.set(d);
	}
	
	public Vector2 getpos()
	{
		return pos;
	}
	
}
