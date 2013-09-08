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
	
	// Extra Client timer
	float extra_timer; 
	
	
	/** 
	 * New game context should only need to be called once 
	 */
	public GameContext()
	{		
		extra_timer = 0;
		restaurant = new PizzaPlace();
		clientlist = new Array<Client>();
		cook = new CookBot();
		server = new DeliverBot(new Vector2(300,600));
		
		diff = new DiffParam[8];
		// Minimum Clients, Maximum Clients, Time to extra client, chance of second pizza flavor, chance of third piza flavor, level up score
		
		
		diff[0] = new DiffParam(1, 1, 0, 0, 0, 2);
		diff[1] = new DiffParam(1, 2, 7, 0, 0, 7); 
		diff[2] = new DiffParam(2, 2, 0, 0, 0, 15);
		diff[3] = new DiffParam(2, 3, 5, 0, 0, 25);
		diff[3] = new DiffParam(1, 2, 7, 1f, 0, 29);
		diff[4] = new DiffParam(2, 3, 10, 0.2f, 0, 40);
		diff[5] = new DiffParam(2, 3, 5, 0.2f, 0.1f, 55);
		diff[6] = new DiffParam(2, 3, 5, 0.4f, 0.4f, 70);
		diff[6] = new DiffParam(3, 4, 5, 0.2f, 0.0f, 90);
	}
	
	/**
	 * Reset the Game context to its initial values
	 */
	public void reset()
	{
		extra_timer = 0;
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

			
			//** TEST FOR MORE CLIENTS **//
			
			// calculating the current number of "dangerous" clients
			// TODO: I can probably roll this loop into the previous loop. 
			// But since the client list is so small, it probably won't matter.
			int nclients = 0;
			for (int i = 0; i < clientlist.size; i++)
				if (clientlist.get(i).state == ClientState.GO_SEAT || clientlist.get(i).state == ClientState.WAIT_FOOD)
					nclients++;
			
			// Guaranteeing the minimum number of clients
			if (nclients < diff[difficulty].client_min)
				{
					clientlist.add(new Client(diff[difficulty]));
					nclients++;
				}	
			
			// testing for extra clients
			if (nclients >= diff[difficulty].client_min && nclients < diff[difficulty].client_max)
			{
				extra_timer += dt;
				if (extra_timer + Globals.dice.nextFloat() > diff[difficulty].extra_client)
				{	
					clientlist.add(new Client(diff[difficulty]));
					extra_timer = 0;
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
		
		if ((difficulty+1) < diff.length && totalServed >= diff[difficulty].nextLvl) // test to increase difficulty
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
	
	public int getScore()
	{
		return score;
	}

	
}
