package org.castelodelego.ludum27.gamemodel;

import org.castelodelego.ludum27.Globals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Client extends Walker{

	public enum ClientState { IN_LINE, GO_SEAT, WAIT_FOOD, EATING, GO_LEAVE };

	static float[] speedClient = { 55, 70, 90};
	
	
	public ClientState state;
	private int tableGoal;
	
	
	public Pizza[] order;
	private boolean[] satisfied;
	public float waiting_time = 10; // THEME! 10 SECONDS! WOW!
	private float eating_time = 3;
	
	public Client(int variety, int quantity, int pizzaN) {
		super(new Vector2(0,0));

		int npizza = Globals.dice.nextInt(pizzaN)+1; // generates 1 to pizzaN pizzas;
				
		order = new Pizza[npizza];
		satisfied = new boolean[npizza];
		state = ClientState.IN_LINE;

		tableGoal = -1;

		
		for(int i = 0; i < npizza; i++)
		{
			order[i] = new Pizza(variety, quantity);
			satisfied[i] = false;
		}		
		
		animation = Globals.dice.nextInt(Globals.client_anims);
		speed = speedClient[animation];
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
			}			
			break;
			
		case WAIT_FOOD:
			for (int i = 0; i < satisfied.length; i++)
				if (!satisfied[i]) // at least one pizza is not here yet
				{
					waiting_time -= dt;
					return ret; // UGLY CODE - break only takes me away from the loop. Maybe there is a "double break" somewhere?
				}

			// all pizzas are here
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
		setpos(new Vector2(Globals.dice.nextInt(200),-60));
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

	/**
	 * Receives a pizza and tests if it is one of the pizzas the client is waiting for
	 * @param p a pizza
	 * @return true if this is a pizza the client is waiting for, false if the client is not waiting for this pizza
	 */
	public boolean givePizza(Pizza p) {
		for (int i = 0; i < order.length; i++)
		{
			if (satisfied[i]) // Already have this pizza
				continue;
			
			if (order[i].isEqual(p))
			{
				satisfied[i] = true;
				Gdx.app.log("Client", "Thanks for the correct pizza!");
				Globals.gc.addPizzaServed(1);
				
				// TODO: Add text balloon here
				Globals.gc.addScore(p.totalIngredients()*order.length);
				return true;
			}
			
		}

		Gdx.app.log("Client","You gave me a wrong pizza!");
		// TODO: add text balloon here
		return false;
	}

	/**
	 * Returns a string with all the pizzas that the client wants
	 * @return
	 */
	public String pizzaString() {
		String ret = "";
		for (int i = 0; i < order.length; i++)
		{
			if (!satisfied[i])
				ret = ret+order[i].infoText()+" -- ";
		}
		return ret;
	}
	
	public Array<Pizza> getDesires()
	{
		Array<Pizza> ret = new Array<Pizza>();
		for (int i = 0; i < satisfied.length; i++)
			if (!satisfied[i])
				ret.add(order[i]);
		return ret;
	}

}
