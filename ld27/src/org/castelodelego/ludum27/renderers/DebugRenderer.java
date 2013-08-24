package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.GameContext;
import org.castelodelego.ludum27.gamemodel.PizzaPlace;
import org.castelodelego.ludum27.gamemodel.Walker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

public class DebugRenderer {

	ShapeRenderer linedrawer;
	
	public DebugRenderer()
	{
		linedrawer = new ShapeRenderer();
		linedrawer.setProjectionMatrix(Globals.cam.combined);		
	}
	
	public void render(GameContext gc)
	{
		RenderRestaurant(gc.restaurant);
		RenderMover(gc.getAllMovers());
	}
		
	public void RenderMover (Walker m[])
	{
		linedrawer.begin(ShapeType.Line);
		linedrawer.setColor(Color.GREEN);
		for(int i = 0; i < m.length; i++)
			linedrawer.rect(m[i].getpos().x, m[i].getpos().y, 40, 40);
		linedrawer.end();
	}
	
	public void RenderRestaurant (PizzaPlace p)
	{

		linedrawer.begin(ShapeType.Line);

		// Render Entrance
		linedrawer.setColor(Color.YELLOW);
		linedrawer.rect(p.entrance.x, p.entrance.y, 80, 40);
		
		// Render Tables
		linedrawer.setColor(Color.BLUE);
		for (int i = 0; i < p.tableLocation.size; i++)
		{
			linedrawer.rect(p.tableLocation.get(i).x, p.tableLocation.get(i).y, PizzaPlace.TABLE_SIZE[0], PizzaPlace.TABLE_SIZE[1]);
		}

		// Render Ingredient tables
		linedrawer.setColor(Color.RED);
		for (int i = 0; i < p.ingredientPosition.size; i++)
		{
			linedrawer.rect(p.ingredientPosition.get(i).x,p. ingredientPosition.get(i).y, PizzaPlace.INGREDIENT_SIZE[0], PizzaPlace.INGREDIENT_SIZE[1]);
		}
		
		// Render Oven
		for (int i = 0; i < p.ovenList.size; i++)
		{
			if (p.ovenList.get(i).hasPizza())
				linedrawer.setColor(Color.WHITE);
			else
				linedrawer.setColor(Color.GRAY);
			linedrawer.rect(p.ovenList.get(i).pos.x, p.ovenList.get(i).pos.y, PizzaPlace.OVEN_SIZE[0], PizzaPlace.OVEN_SIZE[1]);
		}
		
		// Render Pizza tray (with pizzas!)
		for (int i = 0; i < PizzaPlace.pizzaTraySize; i++)
		{
			linedrawer.setColor(Color.ORANGE);
			linedrawer.rect(p.pizzaTrayPos.x+(PizzaPlace.TRAY_SIZE[0]*i), p.pizzaTrayPos.y, PizzaPlace.TRAY_SIZE[0], PizzaPlace.TRAY_SIZE[1]);
			if (p.pizzaTray[i] != null)
				linedrawer.circle(p.pizzaTrayPos.x+(PizzaPlace.TRAY_SIZE[0]*i)+PizzaPlace.TRAY_SIZE[0]/2, p.pizzaTrayPos.y+PizzaPlace.TRAY_SIZE[1]/2, 10);
		}
		
		
		
		linedrawer.end();
		
		

	}
	
	public void RenderSmallPizza(int[] pizza, Vector2 position)
	{
		
	}
	
}
