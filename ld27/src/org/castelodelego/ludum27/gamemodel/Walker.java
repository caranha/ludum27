package org.castelodelego.ludum27.gamemodel;

import com.badlogic.gdx.math.Vector2;

public class Walker {

	private Vector2 pos;
	Vector2 dir;
	float speed = 20;
	float size = 20;
	String sprite_tag;
	
	public Walker(Vector2 pos)
	{
		this.pos = pos.cpy();
		this.dir = new Vector2();
	}
	
	public void move(float dt)
	{
		// normalize the direction, then multiplies it by the speed and the delta-time, and adds it to the position.
		pos.add((dir.nor()).scl(speed*dt));
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
