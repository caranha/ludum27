package org.castelodelego.ludum27.gamemodel;

import org.castelodelego.ludum27.DiffParam;
import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.Client.ClientState;

import com.badlogic.gdx.Gdx;
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
	
	DiffParam[] diff;
	
	public enum GameState { PREPARING, RUNNING, BREAK, GAMEOVER, PAUSED };
	
	
	int score; // score for this game session
	int totalServed;
	
	int difficulty; // used to calculate level progression parameters.
	public GameState gs;
	
	public PizzaPlace restaurant; // Restaurant Info
	public Array<Client> clientlist; // Client Info
	
	// State objects for robots;
	public CookBot cook;
	public DeliverBot server;
	
	
	/** 
	 * New game context should only need to be called once 
	 */
	public GameContext()
	{		
		restaurant = new PizzaPlace();
		clientlist = new Array<Client>();
		cook = new CookBot();
		server = new DeliverBot(new Vector2(300,600));
		
		diff = new DiffParam[5];
		diff[0] = new DiffParam(1, 1, 1, 1, 240, 6);
		diff[1] = new DiffParam(3, 1, 1, 1, 240, 12); // increase variation
		diff[2] = new DiffParam(3, 1, 1, 2, 200, 24); // 2 at a time
		diff[3] = new DiffParam(5, 1, 1, 2, 200, 48); // increase variation
		diff[4] = new DiffParam(5, 1, 2, 2, 150, 72); // increase variation, 2 pizzas at a time
		
	}
	
	/**
	 * Reset the Game context to its initial values
	 */
	public void reset()
	{
		score = 0;
		totalServed = 0;
		
		difficulty = 0;
		gs = GameState.PREPARING;
		
		restaurant.reset();
		clientlist.clear();
		cook.reset();
		server.reset();
	}
	
	/**
	 * Clears the game state for a new day (does not reset power ups)
	 */
	public void clear()
	{
		gs = GameState.PREPARING;
		restaurant.clear();
		clientlist.clear();
		cook.clear();
		server.clear();		
		Globals.srender.reset();
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
			cook.update(dt);
			server.update(dt);
			
			// test if we need more clients
			if (clientlist.size < diff[difficulty].clientMax && Globals.dice.nextFloat() < (1.0f/diff[difficulty].clientFreq))					
				clientlist.add(new Client(diff[difficulty].variety,diff[difficulty].quantity,diff[difficulty].pizzaN));
			
			// Run update on clients
			for(int i = 0; i < clientlist.size; i++)
			{
				if (clientlist.get(i).state == ClientState.IN_LINE)
					clientlist.get(i).enterRestaurant(restaurant.getRandomEmptyTable());
				else
				{
					if (clientlist.get(i).isGameOver())
					{
						Gdx.app.debug("GameUpdate", "Game Over! Client "+i+" is too hungry");
						gs = GameState.GAMEOVER;
					}
					if (clientlist.get(i).update(dt))
						clientlist.removeIndex(i);
				}
			}
			
			// Run update on the Kitchen
			restaurant.update(dt);
			
			// Receive input
			
			break;
		case PAUSED:
			break;
			
		case BREAK:
			// Shoptime. Wait for click to leave this state, 
			break;
		}
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
	
	public void sendCookCommand(int type, int index)
	{
		cook.addOrder(type, index);
	}
	
	public void sendServerCommands(int target, int table) {
		server.setOrder(target, table);
	}


	/** 
	 * Increases the total of pizzas served this game, and increase difficulty if necessary
	 * @param i
	 */
	public void addPizzaServed(int i) {
		totalServed +=1;
		
		if ((difficulty+1) < diff.length && totalServed > diff[difficulty].nextLvl) // test to increase difficulty
		{
			Gdx.app.log("GameContext", "Increased difficulty to "+(difficulty+1)+" after "+totalServed+" pizzas.");
			difficulty++;
		}
	}

	public void addScore(int i) {
		score += i;		
	}

	public void setState(GameState state)
	{
		gs = state;
	}
	
	public GameState getState()
	{
		return gs;
	}
	
}
