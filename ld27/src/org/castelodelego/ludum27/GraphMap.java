package org.castelodelego.ludum27;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * This class represents a graph in the cartesian space.
 * It is used to find the shortest path in the graph.
 * 
 * @author caranha
 *
 */
public class GraphMap {

	private Vector2[] vertices;
	private int[][] neighbors;
	
	private boolean[] visited;
	private Array<Integer> visitqueue;
	
	public GraphMap(Vector2[] vert, int[][] neigh)
	{
		vertices = vert;
		neighbors = neigh;
	}
	
	/** 
	 * Returns the shortest path between the node closest to origin, and the node closest to dest. 
	 * The path is represented as a node sequence in the ArrayList. Returns null if no path was found.
	 * Returns an empty array if the origin and the destination are the same.
	 * 
	 * @param origin
	 * @param dest
	 * @return
	 */
	public ArrayList<Vector2> getPath(Vector2 origin, Vector2 dest)
	{
		// FIXME: Incomplete function
		ArrayList<Vector2> ret = null;
		
		return ret;
	}
	
	
}
