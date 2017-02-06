package fr.disp.polytech.sma.tp1.sma.environment;

import java.util.ArrayList;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.arakhne.tinyMAS.core.SimulationClock;
import org.arakhne.tinyMAS.situatedEnvironment.agent.SituatedAgent;
import org.arakhne.tinyMAS.situatedEnvironment.body.AbstractAgentBody;
import org.arakhne.tinyMAS.situatedEnvironment.perception.PerceptionBody;

import fr.disp.polytech.sma.tp1.sma.environment.objet.PerceptionType;
import fr.disp.polytech.sma.tp1.util.GeometryUtil;


@SuppressWarnings("restriction")
/**
 * Classe AnimatBody
 */
public class AnimatBody extends AbstractAgentBody<PerceptionBody, AnimatInfluence> {

	public static int total = 0;
	public int id;
	private double x = Double.NaN;
	private double y = Double.NaN;
	private PerceptionType type= PerceptionType.STANDARDAGENT;

	private double orientation = 0;

	private double maxLinearSpeed;
	private double maxLinearAcceleration;
	private double maxAngularSpeed;
	private double maxAngularAcceleration;
	private double currentAngularSpeed = 0;
	protected CircularFrustrum audioFustrum;
	protected CircularFrustrum viewFustrum;

 
	private Vector2d linearMove = new Vector2d();

 
	//private ArrayList<AnimatPerception> allPerceptions;
	private ArrayList<AnimatViewPerception> viewPerceptions= new ArrayList<AnimatViewPerception>();

	public AnimatBody(SituatedAgent<?,?,?,?> agent, double maxLinearSpeed, double maxLinearAcceleration, double maxAngularSpeed, double maxAngularAcceleration) {
		super(agent);
		this.maxLinearSpeed = Math.abs(maxLinearSpeed);
		this.maxLinearAcceleration = Math.abs(maxLinearAcceleration);
		this.maxAngularAcceleration = Math.abs(maxAngularAcceleration);
		this.maxAngularSpeed = Math.abs(maxAngularSpeed);
 		viewFustrum = new CircularFrustrum(new Point2d(0,0), 10);
		//		viewFustrum = new CircularFrustrum(new Point2d(0,0), 30); // TESTS
		this.total++;
		this.id = total;
	}

	

	/**
	 * Renvoi le type de perception de l'AnimatBody
	 * @return
	 */
	public PerceptionType getType() {
		return type;
	}

	/**
	 * Set le type de perception de l'animatBody
	 * @param type
	 */
	public void setType(PerceptionType type) {
		this.type = type;
	}

	


	/**
	 * Renvoi l'orientation de l'animatBody
	 * @return
	 */
	public double getOrientation() {
		return orientation;
	}

	/**
	 * Set l'orientation de l'animatBody
	 * @param orientation
	 */
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}

	/**
	 * Renvoi le fustrum audio circulaire de l'animatBody
	 * @return
	 */
	public CircularFrustrum getAudioFustrum() {
		return audioFustrum;
	}

	/**
	 * Set le fustrum Audio
	 * @param fustrum
	 */
	public void setAudioFustrum(CircularFrustrum fustrum) {
		this.audioFustrum = fustrum;
	}

	/**
	 * Set la position en x de l'animatBody
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Set la position en y de l'animatbody
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Set la vitesse Linéaire maximale
	 * @param maxLinearSpeed
	 */
	public void setMaxLinearSpeed(double maxLinearSpeed) {
		this.maxLinearSpeed = maxLinearSpeed;
	}

	/**
	 * Set l'acceleration linéaire maximale
	 * @param maxLinearAcceleration
	 */
	public void setMaxLinearAcceleration(double maxLinearAcceleration) {
		this.maxLinearAcceleration = maxLinearAcceleration;
	}

	/**
	 * Set la vitesse angulaire maximale
	 * @param maxAngularSpeed
	 */
	public void setMaxAngularSpeed(double maxAngularSpeed) {
		this.maxAngularSpeed = maxAngularSpeed;
	}

	/**
	 * Set l'acceleration angulaire maximale
	 * @param maxAngularAcceleration
	 */
	public void setMaxAngularAcceleration(double maxAngularAcceleration) {
		this.maxAngularAcceleration = maxAngularAcceleration;
	}

	/**
	 * Set la vitesse agulaire courante
	 * @param currentAngularSpeed
	 */
	public void setCurrentAngularSpeed(double currentAngularSpeed) {
		this.currentAngularSpeed = currentAngularSpeed;
	}

	/**
	 * Set le vecteur2d de deplacement
	 * @param linearMove
	 */
	public void setLinearMove(Vector2d linearMove) {
		this.linearMove = linearMove;
	}

	/**
	 * Méthode influenceSteering
	 * @param linearAcceleration
	 * @param angularAcceleration
	 */
	public void influenceSteering(Vector2d linearAcceleration, double angularAcceleration) {
		double acc = linearAcceleration.length();
		if (acc>this.maxLinearAcceleration) {
			linearAcceleration.normalize();
			linearAcceleration.scale(this.maxLinearAcceleration);
		}
		influence(new AnimatSteeringInfluence(getAgent(),
				linearAcceleration,
				GeometryUtil.clamp(angularAcceleration, -this.maxAngularAcceleration, this.maxAngularAcceleration)));
	}

	/**
	 * set la position de l'animatBody
	 * @param x
	 * @param y
	 */
	void setPosition(double x, double y) {
		this.linearMove.set(0,0);
		this.x = x;
		this.y = y;
	}

	/**
	 * Déplacement selon des coordonnees passés en paramètre ainsi qu'en fonction d'une durée de simulation
	 * @param dx
	 * @param dy
	 * @param simulationDuration
	 */
	void move(double dx, double dy, double simulationDuration) {
		if (simulationDuration>0) {
			this.linearMove.set(dx, dy);
			double distance = this.linearMove.length();
			if (distance>0) {
				this.linearMove.normalize();
				this.linearMove.scale(distance/simulationDuration);
			}
		}
		else {
			this.linearMove.set(0,0);
		}
		this.x += dx;
		this.y += dy;
 		viewFustrum.setAgentPos(new Point2d(this.x,this.y));
	}

	/**
	 * Méthode de rotation
	 * @param rotation
	 * @param simulationDuration
	 */
	void rotate(double rotation, double simulationDuration) {
		this.currentAngularSpeed = rotation / simulationDuration;
		this.orientation += rotation;
	}

	/**
	 * Renvoi le vecteur de déplacement lineaire
	 * @return
	 */
	public Vector2d getLinearMove() {
		return this.linearMove;
	}

	/**
	 * Renvoi le vecteur correspondant à l'orientation (double) de l'animatbody
	 * @return
	 */
	public Vector2d getOrientationUnitVector() {
		return GeometryUtil.toOrientationVector(this.orientation);
	}

	/**
	 * Renvoi l'angle d'orientation de l'animatBody
	 * @return
	 */
	public double getOrientationAngle() {
		return this.orientation;
	}

	/**
	 * Renvoi la coordonnée x correspondant à l'orientation de l'animatBody
	 * @return
	 */
	public double getOrientationX() {
		return Math.cos(this.orientation);
	}

	/**
	 * Renvoi la coordonnée y correspondant à l'orientation de l'animatBody
	 * @return
	 */
	public double getOrientationY() {
		return Math.sin(this.orientation);
	}

	/**
	 * Renvoi la vitesse linéaire maximale
	 * @return
	 */
	public double getMaxLinearSpeed() {
		return this.maxLinearSpeed;
	}

	/**
	 * Renvoi la vitesse angulaire maximale
	 * @return
	 */
	public double getMaxAngularSpeed() {
		return this.maxAngularSpeed;
	}

	/**
	 * Renvoi la vitesse linéaire courante
	 * @return
	 */
	public double getCurrentLinearSpeed() {
		return this.linearMove.length();
	}

	/**
	 * Renvoi la coordonnée x correspondant à l'orientation de l'animatBody
	 * @return
	 */
	public double getCurrentAngularSpeed() {
		return this.currentAngularSpeed;
	}

	/**
	 * Renvoi l'acceleration linéaire maximale
	 * @return
	 */
	public double getMaxLinearAcceleration() {
		return this.maxLinearAcceleration;
	}

	/**
	 * Renvoi l'acceleration angulaire maximale 
	 * @return
	 */
	public double getMaxAngularAcceleration() {
		return this.maxAngularAcceleration;
	}

	/**
	 * Renvoi la position x de l'animatBody
	 * @return
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * Renvoi la position y de l'animatBody
	 * @return
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * Mise à l'échelle du vecteur
	 * @param v
	 * @param length
	 * @param clock
	 * @return
	 */
	private Vector2d scaleVector(Vector2d v, double length, SimulationClock clock) {
		Vector2d v2 = new Vector2d(v);
		if (v2.length()>0) v2.normalize();
		v2.scale(clock.perTimeUnit(length));
		return v2;
	}

	/**
	 * Calcule les mouvements en steering
	 * @param linearAcceleration
	 * @param clock
	 * @return
	 */
	Vector2d computeSteeringMove(Vector2d linearAcceleration, SimulationClock clock) {
		Vector2d m = new Vector2d();
		m.add(this.linearMove,linearAcceleration);
		double lSpeed = m.length();
		if (lSpeed<0) lSpeed = 0.;
		if (lSpeed>this.maxLinearSpeed) lSpeed = this.maxLinearSpeed;

		return scaleVector(m, lSpeed, clock);
	}

	/**
	 * Calcule les mouvements en cinématique
	 * @param linearSpeed
	 * @param clock
	 * @return
	 */
	Vector2d computeKinematicMove(Vector2d linearSpeed, SimulationClock clock) {
		double lSpeed = linearSpeed.length();
		if (lSpeed<0) lSpeed = 0.;
		if (lSpeed>this.maxLinearSpeed) lSpeed = this.maxLinearSpeed;

		return scaleVector(linearSpeed, lSpeed, clock);
	}

	/**
	 * Calcule la rotation en steering
	 * @param angularAcceleration
	 * @param clock
	 * @return
	 */
	double computeSteeringRotation(double angularAcceleration, SimulationClock clock) {
		double speed = this.currentAngularSpeed + clock.perTimeUnit(angularAcceleration);
		if (speed<-this.maxAngularSpeed) speed = -this.maxAngularSpeed;
		else if (speed>this.maxAngularSpeed) speed = this.maxAngularSpeed;
		return clock.perTimeUnit(speed);
	}

	/**
	 * Calcule la rotation en cinématique
	 * @param angularSpeed
	 * @param clock
	 * @return
	 */
	double computeKinematicRotation(double angularSpeed, SimulationClock clock) {
		double speed = angularSpeed;
		if (speed<-this.maxAngularSpeed) speed = -this.maxAngularSpeed;
		else if (speed>this.maxAngularSpeed) speed = this.maxAngularSpeed;
		return clock.perTimeUnit(speed);
	}

	/**
	 * Renvoi la position de l'animatBody sous forme de point2d
	 * @return
	 */
	public Point2d getLocation() {
		return new Point2d(this.x,this.y);
	}

	 
	/**
	 * Renvoi la liste des perceptions de l'animatBody
	 * @return
	 */
	public ArrayList<AnimatViewPerception> getViewPerceptions() {
		return viewPerceptions;
	}

	/**
	 * Set la liste des perceptions
	 * @param viewPerceptions
	 */
	public void setViewPerceptions(ArrayList<AnimatViewPerception> viewPerceptions) {
		this.viewPerceptions = viewPerceptions;
	}

	/**
	 * Renvoi le fustrum ciculaire visuel
	 * @return
	 */
	public CircularFrustrum getViewFustrum() {
		return viewFustrum;
	}

	/**
	 * Set le fustrum circulaire visuel
	 * @param viewFustrum
	 */
	public void setViewFustrum(CircularFrustrum viewFustrum) {
		this.viewFustrum = viewFustrum;
	}

	 

	/**
	 * Affichage human-friendly
	 */
	public String toString() {
		return String.valueOf(this.id);
	}
}