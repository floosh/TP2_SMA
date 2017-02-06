package fr.disp.polytech.sma.tp1.sma.environment;

import javax.vecmath.Vector2d;

import org.arakhne.tinyMAS.core.AgentIdentifier;

import fr.disp.polytech.sma.tp1.sma.environment.objet.EnvironmentObject;
import fr.disp.polytech.sma.tp1.sma.environment.objet.PerceptionType;
/**
 * Classe AnimatViewPerception
 */
public class AnimatViewPerception extends AnimatPerception{

	PerceptionType type;
	private EnvironmentObject obj = null;
	private AgentIdentifier agId=null;

	
	/**
	 * Constructeur avec paramètres
	 * @param percepts
	 * @param t
	 * @param o
	 * @param ag
	 */
	public AnimatViewPerception(Vector2d percepts,PerceptionType t,EnvironmentObject o,AgentIdentifier ag) {
		super(percepts,t);
		this.agId=ag;
		this.obj=o;
		this.type=t;
	}



	/**
	 * Renvoi le type de perception
	 * @return
	 */
	public PerceptionType getType() {
		return type;
	}
	
	/**
	 * Set le type de perception
	 * @param type
	 */
	public void setType(PerceptionType type) {
		this.type = type;
	}

	/**
	 * Renvoi l'Environment Object
	 * @return
	 */
	public EnvironmentObject getObj() {
		return obj;
	}

	/**
	 * Renvoi l'id de l'agent
	 * @return
	 */
	public AgentIdentifier getAgId() {
		return agId;
	}


}
