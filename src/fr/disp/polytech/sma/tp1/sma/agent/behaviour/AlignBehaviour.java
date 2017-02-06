package fr.disp.polytech.sma.tp1.sma.agent.behaviour;

import javax.vecmath.Vector2d;

@SuppressWarnings("restriction")
/**
 * Interface AlignBehaviour
 */
public interface AlignBehaviour<OUT extends BehaviourOutput> {
	/**
	 * Méthode run()
	 * @param orientation
	 * @param angularSpeed
	 * @param maxAngular
	 * @param target
	 * @return
	 */
	public OUT run(Vector2d orientation, double angularSpeed, double maxAngular, Vector2d target);
	
}