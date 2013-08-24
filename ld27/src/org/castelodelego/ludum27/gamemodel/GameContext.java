package org.castelodelego.ludum27.gamemodel;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.Client.ClientState;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/**
 * Holds all the information about the current game state.
 * Needs to be initialized every time a new game is started.
 * Holds the "update()" function for the game state
 * 
 * @author caranha
 *
 */
public class GameContext {
	
	/* Difficulty parameters:
	 * Max simultaneous clients
	 * Max different ingredients
	 * Max repeated ingredients
	 * Max pizzas
	 * Client Cooldown divisor // TODO: Balance this. Formula: Chance of a client appearing: 1/n
	 * Pizzas for next difficulty
	 */
	
	// TODO: choose a better way to define client frequency
	static final int[][] diffparams = {
		{1, 3, 1, 1, 240, 20} // Difficulty 0: 
	};
	enum GameState { PREPARING, RUNNING, BREAK, GAMEOVER };
	
	
	int score; // score for this game session
	int difficulty; // used to calculate level progression parameters.
	GameState gs;
	
	public PizzaPlace restaurant; // Restaurant Info
	public Array<Client> clientlist; // Client Info
	
	// State objects for robots;
	Bronks cook;
	Beet server;
	
	public GameContext()
	{		
		restaurant = new PizzaPlace();
		clientlist = new Array<Client>();
		cook = new Bronks(new Vector2(300,650));
		server = new Beet(new Vector2(300,600));
	}
	
	/**
	 * Reset the Game context to its initial values
	 */
	public void reset()
	{
		score = 0;
		difficulty = 0;
		gs = GameState.PREPARING;
		
		restaurant.reset();
		clientlist.clear();
	}
	
	public void run()
	{
		gs = GameState.RUNNING;
	}
	
	/**
	 * Updates the game state
	 * @param dt
	 */
	public void update(float dt)
	{
		switch(gs)
		{
		case PREPARING:
			break;
		case GAMEOVER:
			break;
		case RUNNING:

			// Run update on robots
			
			// test if we need more clients
			if (Globals.dice.nextFloat() < (1.0f/diffparams[difficulty][4])) // Mean Time To Happen for clients			
				clientlist.add(new Client(diffparams[difficulty][1], diffparams[difficulty][2], diffparams[difficulty][3]));
			
			// Run update on clients
			for(int i = 0; i < clientlist.size; i++)
			{
				if (clientlist.get(i).state == ClientState.IN_LINE)
					clientlist.get(i).enterRestaurant(restaurant.getRandomEmptyTable());
				else
				{
					if (clientlist.get(i).isGameOver())
						gs = GameState.GAMEOVER;
					if (clientlist.get(i).update(dt))
						clientlist.removeIndex(i);
				}
			}
			
			
			
			// Receive input
			
			break;
			
		case BREAK:
			// Shoptime. Wait for click to leave this state, 
			break;
		}
	}
	
	/**
	 * Returns true if the game is over
	 * @return
	 */
	public GameState getState()
	{
		return gs;
	}

	/**
	 * Used for debug rendering
	 * @return
	 */
	public Walker[] getAllMovers() {
		Walker[] ret = new Walker[clientlist.size+2];
		ret[0] = cook;
		ret[1] = server;
		for (int i = 0; i < clientlist.size; i++)
			ret[i+2] = clientlist.get(i);
		return ret;
	}
	
	
}
