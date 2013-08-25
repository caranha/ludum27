package org.castelodelego.ludum27.gamemodel;

import java.util.ArrayList;

import org.castelodelego.ludum27.Globals;
import com.badlogic.gdx.math.Vector2;

/**
 * Cooking Robot
 * @author caranha
 *
 */
public class CookBot extends Walker{

	public final static int COOK_SPEED = 250;
	public final static int ACTION_INGREDIENT = 0;
	public final static int ACTION_OVEN = 1;
	
	public Pizza currentPizza;
	ArrayList<int[]> orders;
	int[] lastOrder;
	float cooldown;
	
	public CookBot() {
		super(new Vector2());
		
		currentPizza = new Pizza();
		orders = new ArrayList<int[]>();

		// Cooking Robot starts at the Oven
		lastOrder = new int[2];
		lastOrder[0] = ACTION_OVEN;
		lastOrder[1] = 0;		
		cooldown = 0;
	}

	/**
	 * Reset the robot to its pre-game values
	 */
	public void reset()
	{
		clear();
		speed = COOK_SPEED;
	}
	
	/**
	 * Clears the robot position, but does not remove power ups.
	 */
	public void clear() {
		resetGoals();
		currentPizza.clear();
		lastOrder[0] = ACTION_OVEN;
		lastOrder[1] = 0;
		setpos(Globals.gc.restaurant.getKitchenPos(lastOrder));		
		cooldown = 0;
	}

	
	
	public void addOrder(int ordertype, int orderindex)
	{
		int[] order = new int[2];
		order[0] = ordertype;
		order[1] = orderindex;
		orders.add(order);
		
		if (orders.size() == 1) // if there are no orders, we need to queue movement
			setGoals(Globals.gc.restaurant.getCookPath(lastOrder, order));
	}
	
	public void update(float dt)
	{
		if (cooldown > 0)
		{
			cooldown -= dt;
			return;
		}
			
		if (orders.isEmpty()) // no orders, do nothing
			return;
		
		if (!move(dt)) // if we have nowhere to move, it is time to execute the order
		{
			lastOrder = orders.get(0);
			orders.remove(0); // clear the current order;
			setpos(Globals.gc.restaurant.getKitchenPos(lastOrder));
			
			switch(lastOrder[0])
			{
			case ACTION_INGREDIENT:
				currentPizza.addIngredient(lastOrder[1]);
				break;
			case ACTION_OVEN:
				if (Globals.gc.restaurant.useOven(lastOrder[1],currentPizza)) 
					currentPizza.clear(); // true, can put the pizza in the Oven
				else
					orders.clear(); // failed to put pizza in oven; cancel all further orders					
			}
			
			if (!orders.isEmpty()) // if we still have orders queued, add movement goals
				setGoals(Globals.gc.restaurant.getCookPath(lastOrder, orders.get(0)));
		}
	}

	
}
