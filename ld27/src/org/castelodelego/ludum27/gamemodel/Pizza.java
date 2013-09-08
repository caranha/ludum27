package org.castelodelego.ludum27.gamemodel;

import org.castelodelego.ludum27.Globals;

import com.badlogic.gdx.Gdx;

public class Pizza {

	public static final int SIZE = 8;
	
	public static final int MOZZARELLA = 0;
	public static final int TOMATO = 1;
	public static final int ONION = 2;
	public static final int SAUSAGE = 3;
	public static final int OLIVE = 4;
	public static final int EGG = 5;
	public static final int SCREW = 6; // change for something more interesting later;
	public static final int STAR = 7;
	
	
	public int[] ingredients;
	
	public Pizza()
	{
		ingredients = new int[SIZE];
	}
	
	/**
	 * Makes a random pizza with the requested number of DIFFERENT ingredients
	 * 
	 * FIXME: This tends to get loopy if the variety is too close to the total number of ingredients. Change pizza to use "SETS" instead?
	 */
	public Pizza(int variety)
	{
		ingredients = new int[SIZE];
		if (variety > 8)
		{
			Gdx.app.error("Invalid Code Parameter", "Tried to generate a random pizza with variety above possible. Creating an empty pizza instead.");
			return;
		}

		// picks random ingredients until the variety is exhausted.
		while (variety > 0)
		{
			int nextingredient = Globals.dice.nextInt(SIZE);
			if (ingredients[nextingredient] == 0)
			{
				ingredients[nextingredient]++;
				variety --;
			}
		}
	}
	
	public boolean isEqual(Pizza p)
	{
		for (int i = 0; i < SIZE; i++)
		{
			if (this.ingredients[i] != p.ingredients[i])
				return false;
		}
		return true;
	}
	
	public void addIngredient(int i)
	{
		if (i >= SIZE)
		{
			Gdx.app.error("Pizza.addIngredient", "Requested an igredient out of bonds: "+i);
			return;
		}
		ingredients[i]++;
	}
	
	/**
	 * @return the total number of ingredients used in this pizza
	 */
	public int totalIngredients()
	{
		int ret = 0;
		for (int i = 0; i < SIZE; i++)
			ret += ingredients[i];
		return ret;
	}
	
	/** 
	 * Clear all ingredients
	 */
	public void clear()
	{
		for (int i = 0; i < SIZE; i++)
			ingredients[i] = 0;
	}
	
	/**
	 * Copy a pizza into this one
	 * You wouldn't download a pizza
	 */
	public void copy(Pizza p)
	{
		for (int i = 0; i < SIZE; i++)
			ingredients[i] = p.ingredients[i];
	}

	/**
	 * @return a text string with infor about all the ingredients in this pizza
	 */
	public String infoText() {
		String ret = "";
		for (int i = 0; i < SIZE; i++)
			ret += "I"+i+":"+ingredients[i]+" ";
		return ret;
	}
}
