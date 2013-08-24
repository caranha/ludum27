package org.castelodelego.ludum27.gamemodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/** 
 * This static class contains all the constants and placement values for the pizzeria
 * 
 * @author caranha
 *
 */

public class PizzaPlace {


	
	public Vector2 entrance; // location where the clients enter
	
	public Array<Vector2> tableLocation; // the location of all tables in the pizza place
	public Client[] tableOccupied; // whether the table is occupied or not by a client
	
	
	// TODO: COMPLETE KITCHEN DATA
	
	// KITCHEN: Information ingredients
	// KITCHEN: Information about ovens
	// KITCHEN: Information about the pizzas waiting	

	
	
	/**
	 * Initialize the location values for the PizzaPlace
	 */
	public PizzaPlace()
	{
		entrance = new Vector2(20,80);
		
		tableLocation = new Array<Vector2>();
		tableLocation.add(new Vector2(150,150));
		tableLocation.add(new Vector2(250,150));
		tableLocation.add(new Vector2(350,150));
		tableLocation.add(new Vector2(150,250));
		tableLocation.add(new Vector2(250,250));
		tableLocation.add(new Vector2(350,250));
		tableOccupied = new Client[tableLocation.size];
		
		reset();
	}
	
	/**
	 * Clears the restaurant to its pre-game state
	 */
	public void reset() {
		
		// Clear tables
		for (int i = 0; i < tableOccupied.length; i++)
			releaseTable(i);

		// TODO: Finish Restaurant Reset
		// Clear ovens
		// Clear pizza waiting place
		
	}

	/**
	 * Gets the index of a random, empty table. If no tables are empty, returns -1;
	 * @return
	 */
	public int getRandomEmptyTable()
	{
		Random dice = new Random();
		int range = 0;
		
		for (int i = 0; i < tableOccupied.length; i++)
		{
			if (tableOccupied[i] == null)
				range++;
		}
		
		if (range == 0)
			return -1; // found no empty tables
		
		range = dice.nextInt(range);
		
		for (int i = 0; i < tableOccupied.length; i++)
			if (tableOccupied[i] == null)
			{
				if (range == 0)
					return i;
				else 
					range--;
			}		
		Gdx.app.debug("Kichen.getRandomTable","Unreachable Code, no table returned");
		return -1;
	}

	/**
	 * Add this client to this table
	 * @param table The table where to sit the client
	 * @param Client The client to sit at
	 */
	public void sitAtTable(int table, Client client) {
		if (tableOccupied[table] != null)
		{
			Gdx.app.error("PizzaPlace", "Tried to occupy a table that is already occupied: "+table);
		}
		
		tableOccupied[table] = client;
	}

	/**
	 * Gets the path, for a client, from the front door to the table
	 * 
	 * TODO: improve this function and the next one
	 * 
	 * @param table
	 * @return
	 */
	public ArrayList<Vector2> getClientPathToTable(int table) {

		ArrayList<Vector2> ret = new ArrayList<Vector2>();
		ret.add(entrance.cpy());
		ret.add(tableLocation.get(table).cpy());		
		return ret;
	}
	
	/**
	 * Gets the path, for a client, from the table back to the front door
	 * @param tableGoal
	 * @return
	 */
	public ArrayList<Vector2> getClientPathFromTable(int table) {
		ArrayList<Vector2> ret = new ArrayList<Vector2>();
		ret.add(tableLocation.get(table).cpy());
		ret.add(entrance.cpy());		
		return ret;
	}

	public void releaseTable(int tableGoal) {
		tableOccupied[tableGoal] = null;
	}


	
}
