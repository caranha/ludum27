package org.castelodelego.ludum27.gamemodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


/** 
 * This static class contains all the constants and placement values for the pizzeria
 * 
 * @author caranha
 *
 */

public class PizzaPlace {

	public static final int[] INGREDIENT_SIZE = { 60,60 };
	public static final int[] TABLE_SIZE = { 60,60 };
	public static final int[] OVEN_SIZE = { 60,100 };
	
	public Vector2 entrance; // location where the clients enter
	
	public Array<Vector2> tableLocation; // the location of all tables in the pizza place
	public Client[] tableOccupied; // whether the table is occupied or not by a client
	
	public Array<Vector2> ingredientPosition; // position of the ingredient tables;
	public Array<Oven> ovenList;

	
	// TODO: COMPLETE KITCHEN DATA
	
	// KITCHEN: Information about the pizzas waiting	

	
	
	/**
	 * Initialize the location values for the PizzaPlace
	 */
	public PizzaPlace()
	{
		entrance = new Vector2(20,80);
		
		// TABLE LOCATIONS
		tableLocation = new Array<Vector2>();
		tableLocation.add(new Vector2(150,150));
		tableLocation.add(new Vector2(250,150));
		tableLocation.add(new Vector2(350,150));
		tableLocation.add(new Vector2(150,250));
		tableLocation.add(new Vector2(250,250));
		tableLocation.add(new Vector2(350,250));
		tableOccupied = new Client[tableLocation.size];
		
		// INGREDIENT LOCATIONS
		ingredientPosition = new Array<Vector2>();
		ingredientPosition.add(new Vector2(20,720));
		ingredientPosition.add(new Vector2(80,720));
		ingredientPosition.add(new Vector2(140,720));
		ingredientPosition.add(new Vector2(200,720));
		ingredientPosition.add(new Vector2(20,640));
		ingredientPosition.add(new Vector2(80,640));
		ingredientPosition.add(new Vector2(140,640));
		ingredientPosition.add(new Vector2(200,640));

		// OVEN LOCATIONS
		ovenList = new Array<Oven>();
		ovenList.add(new Oven(new Vector2(400,680)));
		
		reset();
	}
	
	/**
	 * Re-start the restaurant to its pre-game state (CLEARS POWER UPS)
	 */
	public void reset() {
		
		clear();
		ovenList.clear();
		ovenList.add(new Oven(new Vector2(400,680)));
	}
	
	/**
	 * Clears the restaurant to its pre-game state (does not clear power ups)
	 */
	public void clear() {
		
		// Clear tables
		for (int i = 0; i < tableOccupied.length; i++)
			releaseTable(i);
		
		// Clear ovens
		for (int i = 0; i < ovenList.size; i++)
			ovenList.get(i).clear();
		
		// TODO: Finish Restaurant Reset
		// Clear pizza waiting place
		
	}
	
	public void update(float dt)
	{
		for (int i = 0; i < ovenList.size; i++)
		{
			if (ovenList.get(i).update(dt)); // Pizza is done, try to put it on the tray
		}
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

	/**
	 * Tries to put a pizza in the oven. Returns false if fails.
	 * @param i -- oven number
	 * @param currentPizza -- pizza to be put in the oven. Values will be copied
	 * @return Returns false if oven number is invalid, or oven is full.
	 */
	public boolean useOven(int i, Pizza currentPizza) {
		if (i < ovenList.size && !ovenList.get(i).hasPizza())
		{
			ovenList.get(i).putPizza(currentPizza);
			return true;
		}
		else				
			return false;
	}

	public Collection<? extends Vector2> getCookPath(int[] prevOrder,
			int[] nextOrder) {
		ArrayList<Vector2> ret = new ArrayList<Vector2>();
		// TODO Improve the Route generation Algorithm
		
		ret.add(getKitchenPos(prevOrder));
		ret.add(getKitchenPos(nextOrder));
		
		return ret;
	}

	
	
	
	public Vector2 getKitchenPos(int[] lastOrder) {
		if (lastOrder[0] == Bronks.ACTION_INGREDIENT)
			return getIngredientPosition(lastOrder[1]);
		else
			return getOvenPosition(lastOrder[1]);
	}

	public Vector2 getIngredientPosition(int i)
	{
		if (i < ingredientPosition.size)
			return ingredientPosition.get(i);
		else
			return null;
	}
	
	public Vector2 getOvenPosition(int i)
	{
		if (i < ovenList.size)
			return ovenList.get(i).pos;
		else
			return null;
	}

	/**
	 * Returns an index to the ingredient that is touched in this position, or -1 if no ingredient is touched in this position.
	 * @param firstTouch
	 * @return
	 */
	public int getIngredientIndex(Vector2 firstTouch) {
		for (int i = 0; i < ingredientPosition.size; i++)
		{
			Rectangle rect = new Rectangle(ingredientPosition.get(i).x,ingredientPosition.get(i).y,INGREDIENT_SIZE[0],INGREDIENT_SIZE[1]);
			if (rect.contains(firstTouch))
				return i;
		}
		return -1;
	}

	/**
	 * Returns an index to the oven that is touched in this position, or -1 if no oven is touched in this position.
	 * @param firstTouch
	 * @return
	 */
	public int getOvenIndex(Vector2 firstTouch) {
		for (int i = 0; i < ovenList.size; i++)
		{
			Rectangle rect = new Rectangle(ovenList.get(i).pos.x,ovenList.get(i).pos.y,OVEN_SIZE[0],OVEN_SIZE[1]);
			if (rect.contains(firstTouch))
				return i;
		}
		return -1;
	}
	
}
