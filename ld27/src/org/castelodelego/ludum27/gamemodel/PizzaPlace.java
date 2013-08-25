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
	public static final int[] OVEN_SIZE = { 160, 120 };
	public static final int[] TRAY_SIZE = { 60,60 };
	
	public Vector2 entrance; // location where the clients enter
	
	public Array<Vector2> tableLocation; // the location of all tables in the pizza place
	public Client[] tableOccupied; // whether the table is occupied or not by a client
	
	public Array<Vector2> ingredientPosition; // position of the ingredient tables;
	public Array<Oven> ovenList;

	public Pizza[] pizzaTray;
	public Vector2 pizzaTrayPos; // position of the Pizza Tray (bottom left);
	public static final int pizzaTraySize = 5;
	
	// KITCHEN: Information about the pizzas waiting	

	
	
	/**
	 * Initialize the location values for the PizzaPlace
	 */
	public PizzaPlace()
	{
		entrance = new Vector2(40,60);
		
		// TABLE LOCATIONS
		
		tableLocation = new Array<Vector2>();
		tableLocation.add(new Vector2(170,170));
		tableLocation.add(new Vector2(270,170));
		tableLocation.add(new Vector2(370,170));
		
		tableLocation.add(new Vector2(170,300));
		tableLocation.add(new Vector2(270,300));
		tableLocation.add(new Vector2(370,300));
		tableOccupied = new Client[tableLocation.size];
		
		// INGREDIENT LOCATIONS
		ingredientPosition = new Array<Vector2>();

		ingredientPosition.add(new Vector2(20,720));
		ingredientPosition.add(new Vector2(20,660));
		ingredientPosition.add(new Vector2(20,600));
		ingredientPosition.add(new Vector2(20,540));
		
		ingredientPosition.add(new Vector2(400,720));
		ingredientPosition.add(new Vector2(400,660));		
		ingredientPosition.add(new Vector2(400,600));
		ingredientPosition.add(new Vector2(400,540));

		// OVEN LOCATIONS
		ovenList = new Array<Oven>();
		
		pizzaTray = new Pizza[pizzaTraySize]; // it starts all null;
		pizzaTrayPos = new Vector2(90,460);
		
		reset();
	}
	
	/**
	 * Re-start the restaurant to its pre-game state (CLEARS POWER UPS)
	 */
	public void reset() {
		
		clear();
		ovenList.clear();
		ovenList.add(new Oven(new Vector2(160,660)));
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
		
		// Clear pizza tray
		for (int i = 0; i < pizzaTraySize; i++)
			pizzaTray[i] = null;
		
		
	}
	
	public void update(float dt)
	{
		for (int i = 0; i < ovenList.size; i++)
		{
			if (ovenList.get(i).update(dt)) // Pizza is done, try to put it on the tray
			{
				int place = findTraySpace();
				if (place != -1)
					pizzaTray[place] = ovenList.get(i).clear();
			}
		}
	}

	/**
	 * Looks for an empty space on the tray. If found, return its index. If not found, return -1;
	 * @return
	 */
	public int findTraySpace()
	{
		for (int i = 0; i < pizzaTraySize; i++)
			if (!(trayHasPizza(i)))
				return i;
		return -1;
	}
	
	/**
	 * Returns true if this tray index has a pizza in it.
	 */
	public boolean trayHasPizza(int target) {
		if (target < pizzaTraySize && pizzaTray[target] != null)
			return true;
		
		return false;
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
	 * Tests if a given table is occupied
	 * @param table
	 * @return
	 */
	public boolean isTableOccupied(int table)
	{
		return (tableOccupied[table] != null);
	}
	
	/**
	 * Gets the path, for a client, from the front door to the table
	 * 
	 * @param table
	 * @return
	 */
	
	
	public ArrayList<Vector2> getClientPathToTable(int table) {
		

		ArrayList<Vector2> ret = new ArrayList<Vector2>();
		
		ret.add(entrance.cpy().add(10,-40));
		
		if (table > 2) // tables 3, 4, 5
			ret.add(new Vector2(entrance.x+10,370));
		else
			ret.add(new Vector2(entrance.x+10,240));

		ret.add(tableLocation.get(table).cpy().add(10, 60));
		ret.add(tableLocation.get(table).cpy().add(10, 20));		
		return ret;
	}
	
	/**
	 * Gets the path, for a client, from the table back to the front door
	 * @param tableGoal
	 * @return
	 */
	public ArrayList<Vector2> getClientPathFromTable(int table) {
		ArrayList<Vector2> ret = new ArrayList<Vector2>();
		
		ret.add(tableLocation.get(table).cpy().add(10, 60));		
		if (table > 2) // tables 3, 4, 5
			ret.add(new Vector2(entrance.x+40,370));
		else
			ret.add(new Vector2(entrance.x+40,240));		
				
		ret.add(entrance.cpy().add(40,-100));
		
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
		
		if ((prevOrder[0] == nextOrder[0]) && prevOrder[1] < 4 && nextOrder[1] < 4) 
		{
			ret.add(getKitchenPos(nextOrder).cpy().add(65, 0)); // Both on the Left side, go straight to Ingredient
			return ret;
		}
		
		if ((prevOrder[0] == nextOrder[0]) && prevOrder[1] > 3 && nextOrder[1] > 3) 
		{
			ret.add(getKitchenPos(nextOrder).cpy().add(-45, 0)); // Both on the Left side, go straight to Ingredient
			return ret;
		}
		
		// Crossing: Add origin mid-point
		if ((prevOrder[0] == CookBot.ACTION_INGREDIENT) && prevOrder[1] < 4 )
			ret.add(getIngredientPosition(3).cpy().add(85,0));
		
		if ((prevOrder[0] == CookBot.ACTION_INGREDIENT) && prevOrder[1] > 3 )
			ret.add(getIngredientPosition(7).cpy().add(-65,0));
		
		// Add End Points
		
		if ((nextOrder[0] == CookBot.ACTION_OVEN))
		{
			ret.add(getOvenPosition(0).cpy().add(60,-70));
			return ret;
		}
		
		// Ingredient Orders
		if ((nextOrder[1]) < 4)
		{
			ret.add(getIngredientPosition(3).cpy().add(85,0));
			ret.add(getKitchenPos(nextOrder).cpy().add(65, 0));
		}
		else
		{
			ret.add(getIngredientPosition(7).cpy().add(-65,0));
			ret.add(getKitchenPos(nextOrder).cpy().add(-45, 0));
		}
		
		return ret;
	}

	public Collection<? extends Vector2> getServerPath(int[] prevAction,
			int[] nextAction) {
		ArrayList<Vector2> ret = new ArrayList<Vector2>();		
		
		// First deal with the cases where change lane is not necessary
		if (prevAction[0] == nextAction[0] && prevAction[0] == DeliverBot.ACTION_GRAB)
		{
			ret.add(getTrayPosition(nextAction[1]).cpy().add(10, -50));
			return ret;
		}
		
		if (prevAction[0] == nextAction[0] && ((prevAction[1] < 3 && nextAction[1] < 3) || (prevAction[1] > 2 && nextAction[1] > 2)))
		{
			ret.add(getTablePosition(nextAction[1]).cpy().add(10, -50));
			return ret;
		}
		
		// Origin Checkpoint
		if (prevAction[0] == DeliverBot.ACTION_GRAB)
			ret.add(getTrayPosition(0).cpy().add(-10, -70));
		else
		{
			int table = (prevAction[1] < 3?0:3);
			ret.add(getTablePosition(table).cpy().add(-80, -70));
		}

		// Destination Checkpoint
		if (nextAction[0] == DeliverBot.ACTION_GRAB)
			ret.add(getTrayPosition(0).cpy().add(-10, -70));
		else
		{
			int table = (nextAction[1] < 3?0:3);
			ret.add(getTablePosition(table).cpy().add(-80, -70));
		}
		
		// Destination
		ret.add(getKitchenPos(nextAction).cpy().add(10,-50));
		
//		ret.add(getKitchenPos(prevAction));
//		ret.add(getKitchenPos(nextAction));
		return ret;
	}

	
	
	/** 
	 * Get the kitchen position that is defined by this 	
	 * @param robotOrder: first value is an order from a Cook or Server robot, defines the type. Second value is an index;
	 * @return A vector with the valid kitchen position for the robot to stand, or null.
	 */
	public Vector2 getKitchenPos(int[] robotOrder) {
		switch(robotOrder[0])
		{
		case CookBot.ACTION_INGREDIENT:
			return getIngredientPosition(robotOrder[1]);

		case CookBot.ACTION_OVEN:
			return getOvenPosition(robotOrder[1]);
			
		case DeliverBot.ACTION_GRAB:
			return getTrayPosition(robotOrder[1]);
			
		case DeliverBot.ACTION_SERVE:
			return getTablePosition(robotOrder[1]);
			
		default:
			Gdx.app.error("getKitchenPos", "Invalid robot Order ID: "+robotOrder[0]+","+robotOrder[1]);
			return null;
		}
	}


	/** 
	 * Get the Ingredient/Oven/Tray/Table position, based on their indexes
	 * @param i the index of the Ingredient/Oven/Tray/Table
	 * @return null if the index is invalid;
	 */
	
	public Vector2 getIngredientPosition(int i)
	{
		if (i < ingredientPosition.size)
			return ingredientPosition.get(i);
		
		Gdx.app.debug("getIngredientPosition", "Invalid Ingredient Index :"+i);
		return null;
	}
	
	public Vector2 getOvenPosition(int i)
	{
		if (i < ovenList.size)
			return ovenList.get(i).pos;
		
		Gdx.app.debug("getOvenPosition", "Invalid Oven Index :"+i);
		return null;
	}
	
	public Vector2 getTrayPosition(int i) {
		if (i < pizzaTraySize)
			return (new Vector2(pizzaTrayPos.x+(i*TRAY_SIZE[0]),pizzaTrayPos.y));
		
		Gdx.app.debug("getTrayPosition", "Invalid Tray Index :"+i);
		return null;
	}

	public Vector2 getTablePosition(int i) {
		if (i < tableLocation.size)
			return (tableLocation.get(i));
		
		Gdx.app.debug("getTablePosition", "Invalid Table Index :"+i);
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
	
	/**
	 * Returns an index to the tray that is touched in this position, or -1 if no oven is touched in this position.
	 * @param firstTouch
	 * @return
	 */
	public int getTrayIndex(Vector2 firstTouch) {
		for (int i = 0; i < pizzaTraySize; i++)
		{
			Rectangle rect = new Rectangle(pizzaTrayPos.x+(TRAY_SIZE[0]*i),pizzaTrayPos.y,TRAY_SIZE[0],TRAY_SIZE[1]);
			if (rect.contains(firstTouch))
				return i;
		}
		return -1;
	}

	/** 
	 * Returns an index to the table that is touched in this position, or -1 if no table is touched in this position
	 * @param newTouch
	 * @return
	 */
	public int getTableIndex(Vector2 touch) {
		for (int i = 0; i < tableLocation.size; i++)
		{
			Rectangle rect = new Rectangle(tableLocation.get(i).x,tableLocation.get(i).y, TABLE_SIZE[0], TABLE_SIZE[1]);
			if (rect.contains(touch))
				return i;
		}
		return -1;
	}


	/**
	 * Removes a Pizza from the tray and returns it.
	 * @param i
	 * @return
	 */
	public Pizza removePizzaFromTray(int i) {
		if (i < pizzaTraySize)
		{
			Pizza ret = pizzaTray[i];
			pizzaTray[i] = null;
			return ret;
		}
		
		Gdx.app.error("Restaurant.RemoveFromTray","Invalid index "+i);
		return null;
	}
	
	/**
	 * Gets a pizza from the tray without removing it.
	 * @param i
	 * @return
	 */
	public Pizza getPizzaFromTray(int i) {
		if (i < pizzaTraySize)
			return pizzaTray[i];
		
		Gdx.app.error("Restaurant.RemoveFromTray","Invalid index "+i);
		return null;
	}
	
	
	

	public Client getClientAtTable(int i) {
		if (i > tableOccupied.length)
		{
			Gdx.app.error("getClientAtTable","invalid index "+i);
			return null;
		}
		return tableOccupied[i];
	}






	
}
