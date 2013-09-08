package org.castelodelego.ludum27;

/**
 * Attribute class holding the difficulty parameters
 * @author caranha
 *
 */
public class DiffParam {
	
	public int client_min; // Minimum number of clients. If the number of clients is less than this, generate a new one.
	public int client_max; // Maximum number of clients, if this number is reached, extra clients will not be generated.
	public float extra_client; // Time for extra clients above the minimum to show up
	public float chance_two; // Chance that a client will ask for a second topping in his pizza
	public float chance_three; // chance that a client will ask for a third topping - IF HE ALREADY HAS ASKED FOR A SECOND!
		
	public int nextLvl; // number of pizzas at which we switch to the next difficulty level
	
	public DiffParam(int min, int max, float extra, float c_two, float c_three, int level)
	{
		client_min = min;
		client_max = max;
		extra_client = extra;
		chance_two = c_two;
		chance_three = c_three;
		nextLvl = level;
	}

}
