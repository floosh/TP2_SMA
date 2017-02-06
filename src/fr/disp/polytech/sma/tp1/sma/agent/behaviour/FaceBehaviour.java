package fr.disp.polytech.sma.tp1.sma.agent.behaviour;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

@SuppressWarnings("restriction")
/**
 * Interface FaceBehaviour
 */
public interface FaceBehaviour<OUT extends BehaviourOutput> {
	/**
	 * Méthode run()
	 * @param position
	 * @param orientation
	 * @param angularSpeed
	 * @param maxAngular
	 * @param target
	 * @return
	 */
	public OUT run(Point2d position, Vector2d orientation, double angularSpeed, double maxAngular, Point2d target);
	
}