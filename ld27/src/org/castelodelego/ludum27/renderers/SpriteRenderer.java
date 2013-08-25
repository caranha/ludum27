package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.Client;
import org.castelodelego.ludum27.gamemodel.Client.ClientState;
import org.castelodelego.ludum27.gamemodel.Pizza;
import org.castelodelego.ludum27.gamemodel.PizzaPlace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpriteRenderer {
	
	Sprite[] pizza;
	Sprite pizzaballoon;
	Sprite storebackground;
	Sprite amazingtable;
	
	Animation bronks_anim;
	Animation beet_anim;
	Animation wait_timer;
	
	Animation4W client[];
	
	float timer;
	
	public SpriteRenderer()
	{
		
	}
	
	/**
	 * Pre-allocate some of the textures to variables. Return true if everything was allocated
	 */
	public boolean init(TextureAtlas pack, float dt)
	{
		timer = 0;
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
		
		pizzaballoon = pack.createSprite("balloons/pizzaballoon");
		
		storebackground = pack.createSprite("static/store");
		amazingtable = pack.createSprite("static/table");
		
		bronks_anim = Globals.animman.get("animations/BronksDOWN");
		beet_anim = Globals.animman.get("animations/BeetDOWN");
		wait_timer = Globals.animman.get("animations/timer");
				
		client = new Animation4W[1];
		client[0] = new Animation4W("animations/ClientA");
		
		return true;
	}
	
	public void reset()
	{
		timer = 0;
	}

	public void renderMainScreen(float dt)
	{
		timer += dt;
		
		Globals.batch.begin();
		Globals.debugtext.setColor(Color.YELLOW);
		Globals.debugtext.draw(Globals.batch, "Samurai Pizza", 100, 700);
		Globals.debugtext.draw(Globals.batch, "Your pizza in 10 seconds, or we commit Seppuku!",100,680);
		
		Globals.debugtext.draw(Globals.batch, "0- click on ingredients to add them to the pizza", 100, 640);
		Globals.debugtext.draw(Globals.batch, "1- click on the owen to finalize a pizza", 100, 620);
		Globals.debugtext.draw(Globals.batch, "2- drag a ready pizza to the client to deliver", 100, 600);
		Globals.debugtext.draw(Globals.batch, "3- Hope it is the right pizza!!!",100,580);
		
		Globals.debugtext.draw(Globals.batch, "Remember, don't let the clients wait more than 10 seconds!",100,500);
		
		Globals.batch.draw(bronks_anim.getKeyFrame(timer), 150, 400);
		Globals.batch.draw(beet_anim.getKeyFrame(timer), 300, 400);
		
		Globals.debugtext.draw(Globals.batch, "Click Anywhere to Begin!", 100, 300);		
		Globals.batch.end();
		
	}
	
	public void renderGameScreen(float dt)
	{
		timer += dt;
		Globals.batch.begin();
		//Rendering background
		storebackground.draw(Globals.batch);
		
		// Rendering Ingredient Table
		for (int i = 0; i < Globals.gc.restaurant.ingredientPosition.size; i++) // pizzas on trays
		{
			
			renderIngredient(i,(new Vector2(10,10)).add(Globals.gc.restaurant.getIngredientPosition(i)),2);
		}
		
		// TODO: Rendering Oven

		// Rendering Pizza Tray
		for (int i = 0; i < PizzaPlace.pizzaTraySize; i++) // pizzas on trays
		{
			if (Globals.gc.restaurant.trayHasPizza(i))
				renderPizza(Globals.gc.restaurant.getPizzaFromTray(i),Globals.gc.restaurant.getTrayPosition(i).add(new Vector2(10,10)),2);
		}
		
		// Rendering Clients
		for (int i = 0; i < Globals.gc.clientlist.size; i++)
		{
			renderClient(Globals.gc.clientlist.get(i),dt);
		}

		// Rendering Tables
		for (int i = 0; i < Globals.gc.restaurant.tableLocation.size; i++)
		{
			Globals.batch.draw(amazingtable, Globals.gc.restaurant.tableLocation.get(i).x, Globals.gc.restaurant.tableLocation.get(i).y);
			if ((Globals.gc.restaurant.getClientAtTable(i) != null) &&
				(Globals.gc.restaurant.getClientAtTable(i).state == ClientState.EATING))
			{ // drawing a pizza in the table
				renderPizza(Globals.gc.restaurant.getClientAtTable(i).order[0], 
						(new Vector2(22,25)).add(Globals.gc.restaurant.tableLocation.get(i)),0.9f);
			}
		}

		// TODO: Pizzas on tables

		
		// Rendering Robots
		Globals.batch.draw(bronks_anim.getKeyFrame(Globals.gc.cook.anim_timer),Globals.gc.cook.getpos().x, Globals.gc.cook.getpos().y);
		Globals.batch.draw(beet_anim.getKeyFrame(Globals.gc.server.anim_timer),Globals.gc.server.getpos().x, Globals.gc.server.getpos().y);

		
		// TODO: Pizzas on speech balloons
		
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
	
	public void renderIngredient(int i, Vector2 pos, float zoom)
	{
		pizza[i+1].setBounds(pos.x, pos.y, pizza[i+1].getWidth()*zoom, pizza[i+1].getHeight()*zoom);
		pizza[i+1].draw(Globals.batch);
		pizza[i+1].setBounds(pos.x, pos.y, pizza[i+1].getWidth()/zoom, pizza[i+1].getHeight()/zoom);
	}
	
	public void renderClient(Client c, float dt)
	{
		
		
		switch(c.state) // Main animation
		{
		case GO_SEAT: // Walking
		case GO_LEAVE:
			Globals.batch.draw(client[c.animation].getKeyFrame(c.anim_timer,c.getdir()),c.getpos().x, c.getpos().y);
			break;
		case IN_LINE: // Stopped
		case WAIT_FOOD:
		case EATING:
			Globals.batch.draw(client[c.animation].getKeyFrame(c.anim_timer,Animation4W.Direction.DOWN),c.getpos().x, c.getpos().y);
		}
		
		switch (c.state) // assistant animations
		{
		case WAIT_FOOD:
			Array<Pizza> tmp = c.getDesires();
			for (int i = 0; i < tmp.size; i++)
			{
				renderPizzaBalloon(tmp.get(i), (new Vector2(40,40)).add(c.getpos()));
			}
			
			Globals.batch.draw(wait_timer.getKeyFrame(10 - c.waiting_time), c.getpos().x, c.getpos().y+60);
		}
	}
	
	public void renderPizzaBalloon(Pizza p, Vector2 pos)
	{
		pizzaballoon.setPosition(pos.x, pos.y);
		pizzaballoon.draw(Globals.batch);
		renderPizza(p, (new Vector2(7,6)).add(pos), 1);
	}
}
