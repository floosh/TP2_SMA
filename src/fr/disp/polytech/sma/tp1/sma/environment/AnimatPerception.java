package fr.disp.polytech.sma.tp1.sma.environment;

import java.util.ArrayList;
import java.util.List;
import javax.vecmath.Vector2d;
import org.arakhne.tinyMAS.situatedEnvironment.perception.Perception;

import fr.disp.polytech.sma.tp1.sma.environment.objet.PerceptionType;
import java.util.Comparator;

/**
 * Classe AnimatPerception
 */
public class AnimatPerception extends Perception<Vector2d> {
	
	PerceptionType type;
	
	/**
	 * constructeur avec paramètres
	 * @param percepts
	 */
	public AnimatPerception(Vector2d percepts, PerceptionType type) {
		super(percepts);
		this.type=type;
	}
	
	 
	
	/**
	 * extrait les perceptions visuelles d'une liste de perceptions
	 * @param pList
	 * @return
	 */
	public static ArrayList<AnimatViewPerception> extractViewPerception(List<AnimatPerception> pList){
		ArrayList<AnimatViewPerception> result = new ArrayList<AnimatViewPerception>();
		if(pList!=null)
		{
			for(AnimatPerception p : pList)
			{
				if(p instanceof AnimatViewPerception)
				{
					result.add((AnimatViewPerception) p );
				}
			}
		}
		
		return result;
	}



	public PerceptionType getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
}

 
