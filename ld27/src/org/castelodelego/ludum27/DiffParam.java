package org.castelodelego.ludum27;

/**
 * Attribute class holding the difficulty parameters
 * @author caranha
 *
 */
public class DiffParam {
	
	public int variety;
	public int quantity;
	public int pizzaN;
	
	public int clientMax;
	public int clientFreq;
	
	public int nextLvl;
	
	public DiffParam(int var, int quant, int pizzaN, int clientMax, int clientFreq, int next)
	{
		variety = var;
		quantity = quant;
		this.pizzaN = pizzaN;
		this.clientMax = clientMax;
		this.clientFreq = clientFreq;
		nextLvl = next;
	}

}
