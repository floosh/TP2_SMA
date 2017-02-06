package fr.disp.polytech.sma.tp1.sma.environment;

import javax.vecmath.Vector2d;
/**
 * Classe AnimatAction
 */
class AnimatAction {

	private final AnimatBody body;
	private final Vector2d move;
	private double rotation;
	/**
	 * Constructeur de AnimatAction
	 * @param object
	 * @param move
	 * @param rotation
	 */
	public AnimatAction(AnimatBody object, Vector2d move, double rotation) {
		this.body = object;
		this.move = move;
		this.rotation = rotation;
	}
	
	/**
	 * renvoi l'AnimatBody de l'AnimatAction
	 * @return
	 */
	public AnimatBody getObjecttoMove() {
		return this.body;
	}
	
	/**
	 * Renvoi le vecteur de deplacement de l'AnimatAction
	 * @return
	 */
	public Vector2d getTranslation() {
		return this.move;
	}
	
	/**
	 * Renvoi langle de rotation de l'AnimatAction
	 */
	public double getRotation() {
		return this.rotation;
	}

}