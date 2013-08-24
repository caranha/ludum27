package org.castelodelego.ludum27.gamemodel;

import org.castelodelego.ludum27.Globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Client extends Walker{

	enum ClientState { IN_LINE, GO_SEAT, WAIT_FOOD, EATING, GO_LEAVE };
	
	public ClientState state;
	private int tableGoal;
	
	
	private Pizza[] order;
	private boolean[] satisfied;
	private float waiting_time = 10; // THEME! 10 SECONDS! WOW!
	private float eating_time = 3;
	
	public Client(int variety, int quantity, int pizzaN) {
		super(new Vector2(0,0));

		int npizza = Globals.dice.nextInt(pizzaN)+1; // generates 1 to pizzaN pizzas;
				
		order = new Pizza[npizza];
		satisfied = new boolean[npizza];
		state = ClientState.IN_LINE;

		tableGoal = -1;
		speed = 40;
		
		for(int i = 0; i < npizza; i++)
		{
			order[i] = new Pizza(variety, quantity);
			satisfied[i] = false;
		}		
		
		this.sprite_tag = ""; // TODO: Select random client sprite;
	}
	
	/**
	 * updates the client. Returns true if the client can be removed
	 * @return
	 */
	public boolean update(float dt)
	{

				
		boolean ret = false;
		
		switch(state)
		{
		case IN_LINE:
			// waits patiently in line until the game context calls me.
			break;
			
		case GO_SEAT: // The client is already inside the restaurant, but not yet seated at the table;
			
			if (!move(dt)) // Table was reached
			{
				state = ClientState.WAIT_FOOD;
				setpos(Globals.gc.restaurant.tableLocation.get(tableGoal));
			}			
			break;
			
		case WAIT_FOOD:
			// TODO: Waiting for the food
			// If Timer runs out, kill the players
			// Else go to Eating
			state = ClientState.EATING;
			break;
			
		case EATING:
			// just wait in the eating animation until we can leave
			eating_time -= dt;
			if (eating_time < 0)
			{
				state = ClientState.GO_LEAVE; // eating is done, change to the last state
				setGoals(Globals.gc.restaurant.getClientPathFromTable(tableGoal)); // get the moving goals
				Globals.gc.restaurant.releaseTable(tableGoal); // clear the table
			}
			break;
		
		case GO_LEAVE:
			if (!move(dt))
			{
				ret = true;
			}			
			break;
	
		}		
		
		return ret;
	}
	
	/**
	 * The client enters the restaurant when a table is set for him. 
	 * Make sure the table is empty before calling this function. 
	 * @param table
	 */
	public void enterRestaurant(int table)
	{		
		if (table == -1)
			return; // invalid table given

		Gdx.app.debug("Table Selected", table+"");

		
		state = ClientState.GO_SEAT;
		tableGoal = table;
		setpos(Globals.gc.restaurant.entrance);
		Globals.gc.restaurant.sitAtTable(table,this);


		setGoals(Globals.gc.restaurant.getClientPathToTable(table));
	}

	/**
	 * Returns true if the hunger timer is over
	 * @return
	 */
	public boolean isGameOver() {		
		return (waiting_time < 0);
	}
	

}
