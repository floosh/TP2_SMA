package fr.disp.polytech.sma.tp1.sma.agent.behaviour;

import javax.vecmath.Vector2d;

@SuppressWarnings("restriction")
/**
 * Classe BehaviourOutput
 */
public abstract class BehaviourOutput {

	protected final Vector2d linear = new Vector2d();
	protected double angular = 0;
	/**
	 * Renvoi le vecteur 2d correspondant à l'acceleration linéaire
	 * @return
	 */
	public Vector2d getLinear() {
		return this.linear;
	}
	/**
	 * Renvoi  le double correspondant à la vitesse angulaire 
	 * @return
	 */
	public double getAngular() {
		return this.angular;
	}
	/**
	 * set l'acceleration linéaire BehaviourOutput passé en paramètre
	 * @param o
	 */
	public void setLinear(BehaviourOutput o) {
		this.linear.set(o.getLinear());
	}
	/**
	 * Renvoi la vitesse angulaire correspondant au behaviourOutput passé en paramètre
	 * @param o
	 */
	public void setAngular(BehaviourOutput o) {
		this.angular = o.getAngular();
	}
	/**
	 * Méthode abstraite isAccelerationBehaviourOutput
	 * @return
	 */
	public abstract boolean isAccelerationBehaviourOutput();

}