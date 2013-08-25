package org.castelodelego.ludum27.gamemodel;

import java.util.ArrayList;

import org.castelodelego.ludum27.Globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class DeliverBot extends Walker {

	public final static int DELIVER_SPEED = 300;
	public static int MAX_PIZZA = 3; 
	public static final int ACTION_GRAB = 2; // This is bigger than 0 so that I can use only one "Get Kitchen Pos";
	public static final int ACTION_SERVE = 3;
	
	float cooldown;
	
	ArrayList<Pizza> heldPizza; // Pizzas to be delivered (in the same order that they will be delivered)
	
	ArrayList<int[]> orders; // List of orders from the interface;
	ArrayList<int[]> actions; // List of internal actions generated from the orders

	int[] lastAction;
	
	public DeliverBot(Vector2 pos) {
		super(new Vector2());
		
		heldPizza = new ArrayList<Pizza>();
		orders = new ArrayList<int[]>();
		actions = new ArrayList<int[]>();
		
		lastAction = new int[2];
	}
	
	
	/**
	 * Set this robot to the state it is in the beginning of a game (no power ups)
	 */
	public void reset()
	{
		clear();
		speed = DELIVER_SPEED;
		MAX_PIZZA = 3;
	}
	
	/**
	 * Set this robot to the state it is when the shop is empty (does not change power ups)
	 */
	public void clear()
	{
		resetGoals();
		cooldown = 0;
		lastAction[0] = ACTION_GRAB;
		lastAction[1] = 0;
		
		heldPizza.clear();
		orders.clear();
		actions.clear();

		setpos(Globals.gc.restaurant.getTrayPosition(0).cpy().add(10,-50));
	}

	
	public void update (float dt)
	{
		if (cooldown > 0)
		{
			cooldown -= dt;
			return;
		}
			
		if (!move(dt)) // I don't have any move goals
		{
			if (actions.isEmpty()) // I have no more actions to do -- get more actions from orders and queue the first
			{
				if (orders.isEmpty()) // I have no more orders, go back to the origin
				{
					if (!(lastAction[0] == ACTION_GRAB && lastAction[1] == 0))
					{
						int[] order = {ACTION_GRAB,0};					
						setGoals(Globals.gc.restaurant.getServerPath(lastAction, order));
						lastAction = order;
					}
					return;
				}
				// Add actions from the order list (Grab, Grab, Grab, Serve, Serve, Serve)
				int acts = (MAX_PIZZA < orders.size()? MAX_PIZZA:orders.size()); // I will add as many actions as the limit, or as the number of orders queued
				
				for (int i = 0; i < acts; i++) // queueing pizza grabs;
				{
					int[] action = { ACTION_GRAB, orders.get(i)[0]};
					actions.add(action);
				}
				for (int i = 0; i < acts; i++) // queueing pizza serves;
				{
					int[] action = { ACTION_SERVE, orders.get(i)[1]};
					actions.add(action);
				}
				
				for (int i = 0; i < acts; i++) // dequeueing actions
					orders.remove(0);
								
				// setGoals to the first action
				setGoals(Globals.gc.restaurant.getServerPath(lastAction, actions.get(0)));				
			}
			else // I am currently doing an action: Perform that action
			{
				lastAction = actions.remove(0);
				Gdx.app.debug("Server do Action", "Action: "+lastAction[0]+" "+lastAction[1]);
				
				switch(lastAction[0])
				{
				case ACTION_GRAB:
					heldPizza.add(Globals.gc.restaurant.removePizzaFromTray(lastAction[1]));
					break;
				case ACTION_SERVE:
					Pizza p = heldPizza.remove(0);
					if (lastAction[1] == -1) // throwing pizza in the trash
						break;
					
					Client c = Globals.gc.restaurant.getClientAtTable(lastAction[1]);
					if (!c.givePizza(p)) // gives pizza to the client, returns false if the pizza was wrong
					{
						cooldown = 0.5f; 
						Gdx.app.log("Beet", "I'm sorry, I got the wrong pizza!");// TODO: Add "I'm sorry" Balloon, Balance I'm sorry timer
					}
					break;
				default:
					Gdx.app.error("Server Update","Invalid Action queued "+lastAction[0]);
				}
				
				if (!actions.isEmpty())
					setGoals(Globals.gc.restaurant.getServerPath(lastAction, actions.get(0)));
			}
		}
	}
	
	/**
	 * Adds a new order to the serving robot (Assumes the new order is valid);
	 * 
	 * @param pizza
	 * @param client
	 */
	public void setOrder(int pizzaID, int clientID)
	{
		int[] order = new int[2];
		order[0] = pizzaID;
		order[1] = clientID;
		orders.add(order);
	}
	
	
}
