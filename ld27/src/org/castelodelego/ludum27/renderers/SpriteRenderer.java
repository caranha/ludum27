package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.Client;
import org.castelodelego.ludum27.gamemodel.Client.ClientState;
import org.castelodelego.ludum27.gamemodel.Pizza;
import org.castelodelego.ludum27.gamemodel.PizzaPlace;

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
	Sprite aboutbackground;
	Sprite helpbackground;
	Sprite gameOverbackground;
	Sprite amazingtable;
	Sprite ovenStatic;
	
	Animation bronks_anim;
	Animation beet_anim;
	Animation wait_timer;
	Animation ovenAnimate;
	
	Animation mainbackground;
	
	Animation4W client[];
	
	float timer;
	int switcher;
	
	public SpriteRenderer()
	{
		
	}
	
	/**
	 * Pre-allocate some of the textures to variables. Return true if everything was allocated
	 */
	public boolean init(TextureAtlas pack, float dt)
	{
		timer = 0;
		switcher = 0;
		
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
		aboutbackground = pack.createSprite("static/about");
		helpbackground = pack.createSprite("static/help");
		gameOverbackground = pack.createSprite("static/gameover");
		
		amazingtable = pack.createSprite("static/table");
		ovenStatic = pack.createSprite("static/oven");
		
		bronks_anim = Globals.animman.get("animations/BronksDOWN");
		beet_anim = Globals.animman.get("animations/BeetDOWN");
		wait_timer = Globals.animman.get("animations/timer");
		ovenAnimate = Globals.animman.get("animations/oven");
		
		mainbackground = Globals.animman.get("animations/main");
				
		client = new Animation4W[3];
		client[0] = new Animation4W("animations/ClientA");
		client[1] = new Animation4W("animations/ClientB");
		client[2] = new Animation4W("animations/ClientC");
		
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
		
		if (Globals.dice.nextFloat() > 0.03)
			Globals.batch.draw(mainbackground.getKeyFrame(0), 0,0);
		else
			Globals.batch.draw(mainbackground.getKeyFrame(0.7f), 0,0);
		
		Globals.debugtext.setColor(Color.BLACK);
		Globals.batch.draw(bronks_anim.getKeyFrame(timer), 80, 260);
		Globals.batch.draw(beet_anim.getKeyFrame(timer), 360, 360);
		
		Globals.debugtext.draw(Globals.batch, Globals.maxscore+"", 330, 60);		
		Globals.batch.end();
		
	}
	
	public void renderAboutScreen(float delta) {
		Globals.batch.begin();
		//Rendering background
		aboutbackground.draw(Globals.batch);
		Globals.batch.end();
	}
	

	public void renderHelpScreen(float delta) {
		Globals.batch.begin();
		//Rendering background
		helpbackground.draw(Globals.batch);
		Globals.batch.end();		
	}
	
	public void renderGameOverScreen(float delta) {
		Globals.batch.begin();
		//Rendering background
		gameOverbackground.draw(Globals.batch);
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
		
		renderOven();
		
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
		
		// Rendering Robots
		Globals.batch.draw(bronks_anim.getKeyFrame(Globals.gc.cook.anim_timer),Globals.gc.cook.getpos().x, Globals.gc.cook.getpos().y);
		Globals.batch.draw(beet_anim.getKeyFrame(Globals.gc.server.anim_timer),Globals.gc.server.getpos().x, Globals.gc.server.getpos().y);

		
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
			
			Globals.batch.draw(wait_timer.getKeyFrame(11f - c.waiting_time), c.getpos().x, c.getpos().y+60);
		}
	}
	
	public void renderPizzaBalloon(Pizza p, Vector2 pos)
	{
		pizzaballoon.setPosition(pos.x, pos.y);
		pizzaballoon.draw(Globals.batch);
		renderPizza(p, (new Vector2(7,6)).add(pos), 1);
	}
	
	public void renderOven()
	{

		if (Globals.gc.restaurant.ovenList.get(0).hasPizza())
			Globals.batch.draw(ovenAnimate.getKeyFrame(timer), Globals.gc.restaurant.ovenList.get(0).pos.x,Globals.gc.restaurant.ovenList.get(0).pos.y);
		else
		{
			Globals.batch.draw(ovenStatic, Globals.gc.restaurant.ovenList.get(0).pos.x,Globals.gc.restaurant.ovenList.get(0).pos.y);
			if (Globals.gc.cook.currentPizza.totalIngredients() > 0)
			{
				renderPizza(Globals.gc.cook.currentPizza, Globals.gc.restaurant.ovenList.get(0).pos.cpy().add(60,10), 2);
			}
		}
	}

	


	
}
