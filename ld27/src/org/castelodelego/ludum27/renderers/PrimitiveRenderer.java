package org.castelodelego.ludum27.renderers;

import org.castelodelego.ludum27.Globals;
import org.castelodelego.ludum27.gamemodel.PizzaPlace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class PrimitiveRenderer {

	ShapeRenderer linedrawer;
	Vector2 traypos;
	Vector2 tablepos;
	
	public PrimitiveRenderer()
	{
		linedrawer = new ShapeRenderer();
		linedrawer.setProjectionMatrix(Globals.cam.combined);
	}

	
	public void reset()
	{
		traypos = null;
		tablepos = null;
	}
	
	
	public void renderPizzaScreen()
	{
		
		// Drawing liney-things
		linedrawer.begin(ShapeType.Line);
		Gdx.gl.glLineWidth(4); // This call is valid only for this "begin/end" cycle? // FIXME: understand how long this setting works.

		drawTrayOutline();
		drawTableOutline();
		linedrawer.end();
		
		// End drawing liney-things
	}
	
	
	
	public void setTrayPos(Vector2 pos)
	{
		traypos = pos;
	}
	
	public void setTablepos(Vector2 pos)
	{
		tablepos = pos;
	}
	
	
	public void drawTrayOutline()
	{
		if (traypos != null)
		{
			linedrawer.setColor(Color.RED);			
			linedrawer.rect(traypos.x, traypos.y, PizzaPlace.TRAY_SIZE[0], PizzaPlace.TRAY_SIZE[1]);
		}
	}
	
	public void drawTableOutline()
	{
		if (tablepos != null)
		{
			linedrawer.setColor(Color.RED);			
			linedrawer.rect(tablepos.x, tablepos.y, PizzaPlace.TABLE_SIZE[0], PizzaPlace.TABLE_SIZE[1]);
		}
	}
	
}
