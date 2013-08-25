package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.Pizza;
import org.castelodelego.ludum27.gamemodel.PizzaPlace;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class SpriteRenderer {
	
	Sprite[] pizza;
	
	public SpriteRenderer()
	{
		
	}
	
	/**
	 * Pre-allocate some of the textures to variables. Return true if everything was allocated
	 */
	public boolean init(TextureAtlas pack, float dt)
	{
		pizza = new Sprite[9];
		
		pizza[0] = pack.createSprite("pizza/pizza");
		
		pizza[1] = pack.createSprite("pizza/cheese");
		pizza[2] = pack.createSprite("pizza/tomato");
		pizza[3] = pack.createSprite("pizza/onion");
		pizza[4] = pack.createSprite("pizza/sausage");
		pizza[5] = pack.createSprite("pizza/olive");
		pizza[6] = pack.createSprite("pizza/eggs");
		pizza[7] = pack.createSprite("pizza/screw");
		pizza[8] = pack.createSprite("pizza/star");
		
		
		
		return true;
	}
	

	public void renderGameScreen(float dt)
	{
		Globals.batch.begin();
		// TODO: Rendering background
		// TODO: Rendering Tools (tables, holders, etc)
		// TODO: Rendering sprites (Robots, humans)
		
		// TODO: Rendering pizzas (on tables, on trays)
		for (int i = 0; i < PizzaPlace.pizzaTraySize; i++) // pizzas on trays
		{
			if (Globals.gc.restaurant.trayHasPizza(i))
				renderPizza(Globals.gc.restaurant.getPizzaFromTray(i),Globals.gc.restaurant.getTrayPosition(i).add(new Vector2(10,10)),2);
		}
		
		
		// TODO: Rendering speech balloons
		// TODO: Rendering pizza display
		Globals.batch.end();
	}
	
	public void renderPizza(Pizza p, Vector2 pos, float zoom)
	{

		pizza[0].setBounds(pos.x, pos.y, pizza[0].getWidth()*zoom, pizza[0].getHeight()*zoom);
		pizza[0].draw(Globals.batch);
		pizza[0].setBounds(pos.x, pos.y, pizza[0].getWidth()/zoom, pizza[0].getHeight()/zoom); // TODO: maybe there is a smarter way to do this?
		
		for (int i = 0; i < p.ingredients.length; i++)
			if (p.ingredients[i] > 0)
			{
				pizza[i+1].setBounds(pos.x, pos.y, pizza[i+1].getWidth()*zoom, pizza[i+1].getHeight()*zoom);
				pizza[i+1].draw(Globals.batch);
				pizza[i+1].setBounds(pos.x, pos.y, pizza[i+1].getWidth()/zoom, pizza[i+1].getHeight()/zoom);
			}
		
	}
}
