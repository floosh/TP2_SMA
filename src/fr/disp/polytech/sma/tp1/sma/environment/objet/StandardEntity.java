package fr.disp.polytech.sma.tp1.sma.environment.objet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import javax.vecmath.Point2d;

import fr.disp.polytech.sma.tp1.sma.environment.WorldModel;

/**
 * Classe Bombe
 */
public class StandardEntity extends EnvironmentObject {

	private Boolean isBordure = false;

	private final int radius = 200;
	public int getRadius() {
		return radius;
	}

	/**
	 * Constructeur
	 * @param time
	 */
	public StandardEntity(Point2d point2d, WorldModel model, boolean isBordure){
		super();
		this.isBordure=isBordure;
		this.model=model;
		this.setLocation(point2d);

	}


	@Override
	/**
	 * Set le type de perception à "BOMBE"
	 */
	public PerceptionType getPerceptionType() {
		if(this.isBordure)
			return PerceptionType.OBSTACLE;
		else
			return PerceptionType.OBSTACLE;
	}


}
