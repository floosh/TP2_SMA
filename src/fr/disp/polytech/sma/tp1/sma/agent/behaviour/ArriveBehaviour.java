package fr.disp.polytech.sma.tp1.sma.agent.behaviour;

import java.util.ArrayList;

import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;

import fr.disp.polytech.sma.tp1.sma.environment.AnimatViewPerception;

@SuppressWarnings("restriction")
/**
 * Interface ArriveBehaviour
 */
public interface ArriveBehaviour  <OUT extends BehaviourOutput>  {
	/**
	 * Méthode run
	 * @param position
	 * @param linearSpeed
	 * @param maxLinear
	 * @param target
	 * @param viewPercepts
	 * @return
	 */
	public OUT run(Point2d position, double linearSpeed, double maxLinear, Tuple2d target, ArrayList<AnimatViewPerception> viewPercepts);
	
}
