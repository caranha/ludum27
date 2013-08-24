package org.castelodelego.ludum27.gamemodel;

import org.castelodelego.ludum27.Globals;

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
	
	// FIXME: Create Pizza Class
	public Pizza()
	{
		ingredients = new int[SIZE];
	}
	
	public Pizza(int variety, int quantity)
	{
		ingredients = new int[SIZE];
		for (int i = 0; i < variety; i++)
		{
			ingredients[Globals.dice.nextInt(SIZE)]+= Globals.dice.nextInt(quantity);			
		}
		if (totalIngredients() == 0)
			ingredients[Globals.dice.nextInt(SIZE)]+= 1; // at least one ingredient
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
	
	
}
