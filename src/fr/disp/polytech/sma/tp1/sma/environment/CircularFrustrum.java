package fr.disp.polytech.sma.tp1.sma.environment;

import javax.vecmath.Point2d;

 
@SuppressWarnings("restriction")
/**
 * Classe CircularFrustrum
 */
public class CircularFrustrum {

	protected Point2d agentPos;
	protected int radius = 10;
	
	/**
	 * Constructeur avec paramètres
	 * @param pos
	 * @param radius
	 */
	CircularFrustrum(Point2d pos, int radius) {
		this.agentPos = new Point2d(pos);
		this.radius = radius;
	}
	
	/**
	 * Renvoi le rayon du frustrum
	 * @return
	 */
	public int getRadius() {
		return radius;
	}
	
	/**
	 * Set le rayon du frustrum
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Informe si un point2d passé en paramètre est à l'intérieur du frustrum circulaire
	 * @param p
	 * @return
	 */
	public boolean isPointInside(Point2d p) {

		float dist = (float) Math
				.sqrt(((agentPos.x - p.x) * (agentPos.x - p.x))
						+ ((agentPos.y - p.y) * (agentPos.y - p.y)));
		if (dist <= radius)
			return true;
		return false;
	}

	 
	
	/**
	 * Déplace le frustrum selon x et y
	 * @param dx
	 * @param dy
	 */
	public void move(double dx, double dy) {
		setPosition(new Point2d(dx + agentPos.x, dy + agentPos.y));
	}

	/**
	 * Informe si un point2d passé en paramètre est à l'intérieur du frustrum circulaire
	 * @param p
	 * @return
	 */
	public void setPosition(Point2d point2d) {
		agentPos.x = point2d.x;
		agentPos.y = point2d.y;

	}
	
	/**
	 * Renvoi la position de l'agent
	 * @return
	 */
	public Point2d getAgentPos() {
		return agentPos;
	}

	/**
	 * Set la position de l'agent
	 * @param agentPos
	 */
	public void setAgentPos(Point2d agentPos) {
		this.agentPos = agentPos;
	}

	/**
	 * Renvoi la coordonnée en x de la position de l'agent
	 * @return
	 */
	public double getX() {
		return this.agentPos.x;
	}
	
	/**
	 * Renvoi la coordonnée en y de la position de l'agent
	 * @return
	 */
	public double getY() {
		return this.agentPos.y;
	}

	/**
	 * Informe si un point2d passé en paramètre est à l'intérieur du frustrum circulaire
	 * @param p
	 * @return
	 */
	public boolean intersects(AnimatBody data) {
		 
		return this.radius>data.getLocation().distance(agentPos);
	}

}
