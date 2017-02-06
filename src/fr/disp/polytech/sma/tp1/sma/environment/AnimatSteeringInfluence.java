package fr.disp.polytech.sma.tp1.sma.environment;

import javax.vecmath.Vector2d;

import org.arakhne.tinyMAS.core.AgentIdentifier;
/**
 * Classe AnimatSteeringInfluence
 */
public class AnimatSteeringInfluence extends AnimatInfluence {
	/**
	 * Constructeur avec paramètres
	 * @param emitter
	 * @param linearAcceleration
	 * @param angularAcceleration
	 */
	AnimatSteeringInfluence(AgentIdentifier emitter, Vector2d linearAcceleration, double angularAcceleration) {
		super(emitter, linearAcceleration, angularAcceleration);
	}
	
	/**
	 * Renvoi l'acceleration linéaire
	 * @return
	 */
	public Vector2d getLinearAcceleration() {
		return this.linear;
	}
	
	/**
	 * Renvoi l'acceleration angulaire
	 * @return double
	 */
	public double getAngularAcceleration() {
		return this.angular;
	}

}