/* 
 * $Id$
 * 
 * Copyright (C) 2004-2007 St&eacute;phane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */

package fr.disp.polytech.sma.tp1.sma.agent;

import java.util.ArrayList;

import javax.vecmath.Point2d;

import org.arakhne.tinyMAS.situatedEnvironment.agent.SituatedAgent;
import org.arakhne.tinyMAS.situatedEnvironment.environment.SituatedEnvironment;
import org.arakhne.tinyMAS.situatedEnvironment.environment.SituatedObject;

import fr.disp.polytech.sma.tp1.sma.environment.AnimatBody;
import fr.disp.polytech.sma.tp1.sma.environment.AnimatInfluence;
import fr.disp.polytech.sma.tp1.sma.environment.AnimatPerception;
import fr.disp.polytech.sma.tp1.sma.environment.objet.EnvironmentObject;
import java.util.Comparator;
 
@SuppressWarnings("restriction")
/**
 * Classe Animat
 */
public abstract class Animat extends SituatedAgent<AnimatBody, SituatedObject, AnimatPerception, AnimatInfluence> {
 
	protected ArrayList<EnvironmentObject> knownObjects = new ArrayList<EnvironmentObject>();
	 
	protected long date;
 

	 
	protected int altruismFactor;
	protected boolean panic=false;
	protected boolean leaving;
	protected boolean arriving;
	protected boolean evacuation =false;
	protected int panicLuck=8;
	
	/**
	 * Constructeur Animat
	 * @param formationPosition
	 */
	public Animat(  ) {
	
		 
	}

	@Override
	/**
	 * Fonction start absraite
	 */
	public abstract void start(); 

	@Override
	/**
	 * méthode creatDefaultBody
	 * @param
	 * @param
	 * @param
	 */
	public  AnimatBody createDefaultBody(SituatedEnvironment<? extends SituatedAgent<AnimatBody, SituatedObject, AnimatPerception, AnimatInfluence>, AnimatBody, SituatedObject, AnimatPerception, AnimatInfluence> in) {
		AnimatBody body = new AnimatBody(this,  
										 .6,	// max linear speed m/s
										.2,						// max linear acceleration (m/s)/s
										Math.PI,				// max angular speed r/s
										Math.PI);			// max angular acceleration (r/s)/s
		
		 
		
		return body;
	}
	
	/**
	 * Méthode qui récupère l'accélération linéaire maximale
	 * @param AnimatBody body
	 * @return double
	 */
	protected double getMaxLinear(AnimatBody body) {
		return body.getMaxLinearAcceleration();
	}
	
	/**
	 * Méthode qui récupère la vitesse angulaire maximale
	 * @param AnimatBody body
	 * @return double
	 */
	protected double getMaxAngular(AnimatBody body) {
		return body.getMaxAngularAcceleration();
	}
	
	/**
	 * Méthode renvoyant true si la distance du point 2d est < 20
	 * @param point2d
	 * @return
	 */
	protected boolean nearLocation(Point2d point2d) {
		return this.getAgentBody().getLocation().distance(point2d) < 15;
	}
	
	@Override
	/**
	 * Méthode abstraite de prise de décision
	 */
	protected abstract void doDecisionAndAction();
 
	 
 

}

class ObjectDistanceComparator implements Comparator<AnimatPerception>{

    Animat a;
    
    public ObjectDistanceComparator(Animat a) {
        this.a = a;
    }
    
    public int distance(double x1, double y1, double x2, double y2) {
        return (int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    @Override
    public int compare(AnimatPerception o1, AnimatPerception o2) {
        return distance(a.getAgentBody().getLocation().x, a.getAgentBody().getLocation().y, o1.getData().x, o1.getData().y)
                -distance(a.getAgentBody().getLocation().x, a.getAgentBody().getLocation().y, o2.getData().x, o2.getData().y);
    }
}