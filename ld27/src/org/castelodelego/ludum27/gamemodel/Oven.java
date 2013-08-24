package org.castelodelego.ludum27.gamemodel;

import com.badlogic.gdx.math.Vector2;

public class Oven {

	public Vector2 pos;
	Pizza p;
	
	String sprite;
	
	float maxTimer;
	float timer;
	
	public Oven(Vector2 p)
	{
		pos = new Vector2(p);
		maxTimer = 2; // makes pizzas in 2 seconds (TODO: needs balancing)
	}
	
	/**
	 * Reduces the timer of the oven. Returns true if a pizza is done.
	 * @param dt
	 * @return
	 */
	public boolean update(float dt)
	{
		if (hasPizza())
		{
			timer -= dt;
			return (timer < 0);
		}
		else
			return false;
	}
	
	/** 
	 * Removes the pizza from the oven, and resets its timer. Used to send pizzas to the nex step in their journey
	 * @return a pointer to the pizza that was in the oven
	 */
	public Pizza clear()
	{
		Pizza ret = p;
		p = null;
		timer = maxTimer;
		return ret;
	}
	
	public boolean hasPizza()
	{
		return (p != null);
	}
	
	public void putPizza(Pizza pp)
	{
		p = new Pizza();
		p.copy(pp);
		timer = maxTimer;
	}
	
}
