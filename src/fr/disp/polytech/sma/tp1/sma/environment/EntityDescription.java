package fr.disp.polytech.sma.tp1.sma.environment;

import java.util.ArrayList;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.disp.polytech.sma.tp1.sma.environment.objet.PerceptionType;
/**
 * Class EntityDescription : contient les informations d'affichage d'un agent (utilisé par la GUI)
 */
public class EntityDescription implements Comparable<EntityDescription> {

	public final Point2d position = new Point2d();
	public final Vector2d orientation = new Vector2d();
	public final CircularFrustrum fustrum = new CircularFrustrum(new Point2d(), 100);
	public PerceptionType type = PerceptionType.OBSTACLE;
	public double ill = 0;
	public double hungry = 0;
	public double naturalNeeds = 0;
	public boolean isPanic = false;
	public final ArrayList<AnimatViewPerception> viewPerception ;
 	
	/**
	 * Constructeur d'une EntityDescription
	 * @param arrayListv
	 * @param a
	 */
	public EntityDescription(ArrayList<AnimatViewPerception> arrayListv) {
		viewPerception = arrayListv;
 	}
	
 
	/**
	 * Methode de comparaison avec une autre autre EntityDescription
	 */
	public int compareTo(EntityDescription o) {
		
		return o.hashCode() - hashCode();
	}

}
