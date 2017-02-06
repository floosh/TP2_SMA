package fr.disp.polytech.sma.tp1.sma.environment;

import javax.vecmath.Vector2d;

import org.arakhne.tinyMAS.core.AgentIdentifier;
import org.arakhne.tinyMAS.situatedEnvironment.influence.Influence;

/**
 * Classe AnimatInfluence
 */
public abstract class AnimatInfluence extends Influence {

	protected final Vector2d linear;
	protected final double angular;
	
	/**
	 * Constructeur avec paramètres
	 * @param emitter
	 * @param linearInfo
	 * @param angularInfo
	 */
	AnimatInfluence(AgentIdentifier emitter, Vector2d linearInfo, double angularInfo) {
		super(emitter);
		this.linear = linearInfo;
		this.angular = angularInfo;
	}
	
}