package org.castelodelego.ludum27.gamemodel;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

/**
 * Cooking Robot
 * @author caranha
 *
 */
public class Bronks extends Walker{

	public static int ACTION_INGREDIENT = 0;
	public static int ACTION_OVEN = 1;
	
	Pizza currentPizza;
	ArrayList<Integer[]> orders;
	private ArrayList<Vector2> move_goals;
	
	
	public Bronks() {
		super(new Vector2());
		
		currentPizza = new Pizza();
		orders = new ArrayList<Integer[]>();
		move_goals = new ArrayList<Vector2>();
	}

	/**
	 * Reset the robot to its initial position
	 */
	public void reset()
	{
		move_goals.clear();
		currentPizza.clear();
		
	}
	
	
	public void addOrder(int ordertype, int orderindex)
	{
		
	}
	
}
