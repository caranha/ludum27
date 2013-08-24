package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.GraphMap;
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
	
	public void RenderGraphMap (GraphMap m)
	{
		
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
			linedrawer.rect(p.tableLocation.get(i).x, p.tableLocation.get(i).y, 60, 60);
		}

		// Render Ingredient tables
		// Render Oven
		// Render Pizza tray (with pizzas!)
		
		linedrawer.end();
		
		

	}
	
	public void RenderSmallPizza(int[] pizza, Vector2 position)
	{
		
	}
	
}