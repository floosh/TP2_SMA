package fr.disp.polytech.sma.tp1.sma.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.tinyMAS.core.AgentIdentifier;
import org.arakhne.tinyMAS.core.ConstantStepTimeManager;
import org.arakhne.tinyMAS.core.SimulationClock;
import org.arakhne.tinyMAS.situatedEnvironment.environment.AbstractSituatedEnvironment;
import org.arakhne.tinyMAS.situatedEnvironment.environment.SituatedObject;

import fr.disp.polytech.sma.tp1.data.box.AABoundingBox;
import fr.disp.polytech.sma.tp1.sma.agent.Animat;
import fr.disp.polytech.sma.tp1.sma.agent.StandardAgent;
import fr.disp.polytech.sma.tp1.sma.environment.objet.EnvironmentObject;
import fr.disp.polytech.sma.tp1.sma.environment.objet.PerceptionType;
import fr.disp.polytech.sma.tp1.sma.environment.objet.StandardEntity;


@SuppressWarnings("restriction")
/**
 * Classe WorldModel
 */
public class WorldModel
extends
AbstractSituatedEnvironment<Animat, AnimatBody, SituatedObject, AnimatPerception, AnimatInfluence> {

	public static final double BODY_RADIUS = 2;

	private ArrayList<AnimatBody> agents = new ArrayList<AnimatBody>();

	private ArrayList<EnvironmentObject> objects = new ArrayList<EnvironmentObject>();
	private final double width;
	private final double height;
	
	public AABoundingBox car;
	//private QuadTree allBodyTree;


	/**
	 * Constructeur avec paramètres
	 * @param width
	 * @param height
	 */
	public WorldModel(double width, double height) {
		super(new ConstantStepTimeManager(1, TimeUnit.SECONDS));
		this.width = width;
		this.height = height;
		//this.allBodyTree = new QuadTree(width, height);
	}



	/**
	 * Renvoi l'attribut Width
	 * @return
	 */
	public double getWidth() {
		return this.width;
	}

	/**
	 * Renvoi l'attribut Height
	 * @return
	 */
	public double getHeight() {
		return this.height;
	}

	@Override
	/**
	 * Ajoute un body passé en paramètre à l'arbre des Body (allBodyTree) avec une position aléatoire
	 */
	protected void onAgentBodyAdded(AnimatBody body) {
		double x, y;
		x = (new Random().nextInt((int)getWidth())) - getWidth() / 2;
                y = (new Random().nextInt((int)getHeight())) - getHeight() / 2;
		body.setPosition(x, y);
		this.agents.add(body);
		//this.allBodyTree.addBody(body);
	}

	@Override
	/**
	 * Supprime le body passé en paramètre de l'arbre des Body (allBodyTree)
	 */
	protected void onAgentBodyRemoved(AnimatBody body) {
 
	}

	/**
	 * Méthode decrivant la perception de chaque agent (auditive et visuelle)
	 */
	public AnimatPerception[] perceive(AgentIdentifier agent) {
		ArrayList<AnimatPerception> allPercepts = new ArrayList<AnimatPerception>();

		AnimatBody body = getAgentBody(agent);
		if (body!=null) {
			double x1 = body.getX();
			double y1 = body.getY();

			for(AnimatBody b1 : getAllAgentBodies()) {
				if (b1!=body) {
					double x2 = b1.getX();
					double y2 = b1.getY();
					double distance = new Vector2d(x2-x1,y2-y1).length();
					if(distance<body.getViewFustrum().radius){
						Vector2d v = new Vector2d(x2 ,y2);
						allPercepts.add(new AnimatPerception(v,b1.getType()));
					}
				}
			}
			
			for (EnvironmentObject o : getObjects()) {
				double x2 = o.getPosX();
				double y2 = o.getPosY();
				double distance = new Vector2d(x2-x1,y2-y1).length();
				if(o instanceof StandardEntity){
					if(distance<body.getViewFustrum().radius){
						Vector2d v = new Vector2d(x2 ,y2);
						allPercepts.add(new AnimatPerception(v,o.getPerceptionType()));
					}
				}
			}
		}

		AnimatPerception[] tab = new AnimatPerception[allPercepts.size()];
		allPercepts.toArray(tab);
		allPercepts.clear();
		return tab;

	}

	@Override
	/**
	 * Applique les influences passées en paramètre
	 */
	protected boolean applyInfluences(Collection<AnimatInfluence> influences) {
		SimulationClock clock = getSimulationClock();

		List<AnimatInfluence> influenceList = new ArrayList<AnimatInfluence>(
				influences);
		List<AnimatAction> actions = new ArrayList<AnimatAction>(influenceList
				.size());

		// Compute actions
		for (int index1 = 0; index1 < influenceList.size(); index1++) {
			AnimatInfluence inf1 = influenceList.get(index1);
			AnimatBody body1 = getAgentBody(inf1.getEmitter());
			if (body1 != null) {
				Vector2d move;
				double rotation;

				move = body1.computeSteeringMove(
						((AnimatSteeringInfluence) inf1)
						.getLinearAcceleration(), clock);
				rotation = body1.computeSteeringRotation(
						((AnimatSteeringInfluence) inf1)
						.getAngularAcceleration(), clock);

				 
				actions.add(new AnimatAction(body1, move, rotation));
			}
                        
                        if(body1.getType() == PerceptionType.WOLF) {
                            for(AnimatBody a : getAllAgentBodies()) {
                                if(a.getLocation().distance(body1.getLocation()) < 3 && a.getType() == PerceptionType.SHEEP) {
                                    a.freeze();
                                }
                            }
                        }
		}

		// Apply the actions
		for (AnimatAction action : actions) {
			AnimatBody body = action.getObjecttoMove();
			if (body != null) {
				body.move(action.getTranslation().x, action.getTranslation().y,
						clock.getSimulationStepDuration());
				body.rotate(action.getRotation(), clock
						.getSimulationStepDuration());


			}
			if(Double.isNaN(body.getLocation().x ) || Double.isNaN(body.getLocation().y ) )
				 body.setPosition(0, 0);
		}
		
		

		//System.out.println("World updating @ "+clock.getSimulationTime()); //$NON-NLS-1$

		return true;
	}

	/**
	 * Renvoi une map regroupant les positions des AnimatBody
	 * @return
	 */
	public Map<AgentIdentifier, EntityDescription> getState() {
		Collection<AnimatBody> bodies = getAllAgentBodies();
		Map<AgentIdentifier, EntityDescription> positions = new TreeMap<AgentIdentifier, EntityDescription>();
		EntityDescription desc;
		for (AnimatBody body : bodies) {

			desc = new EntityDescription(body.getViewPerceptions());
			desc.type = body.getType();
			desc.position.set(body.getX(), body.getY());
			desc.orientation
			.set(body.getOrientationX(), body.getOrientationY());
			positions.put(body.getAgent(), desc);
		}
		return positions;
	}

	/**
	 * Set les objets du worldModel
	 * @param objects
	 */
	public void setObjects(ArrayList<EnvironmentObject> objects) {
		this.objects = objects;
	}

	/**
	 * Renvoi les objets du WorldModel
	 * @return
	 */
	public ArrayList<EnvironmentObject> getObjects() {
		return objects;
	}

	/**
	 * Ajoute un objet passé en paramètre à la liste des objets du WorldModel
	 * @param o
	 */
	public void addObject(EnvironmentObject o) {
		this.objects.add(o);
	}

}