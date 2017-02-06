package fr.disp.polytech.sma.tp1.util;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;
/**
 * Classe GeometryUtil
 */
public class GeometryUtil {
	/**
	 * Renvoi l'angle entre 2 vecteurs passés en paramètres
	 * @param v1
	 * @param v2
	 * @return
	 */
	
	public static double signedAngle(Vector2d v1, Vector2d v2) {
		Vector2d a = new Vector2d(v1);
		if (a.length()==0) return Double.NaN;
		Vector2d b = new Vector2d(v2);
		if (b.length()==0) return Double.NaN;
		a.normalize();
		b.normalize();
		
		double cos = a.x * b.x + a.y * b.y;
		// A x B = |A|.|B|.sin(theta).N = sin(theta) (where N is the unit vector perpendicular to plane AB)
		double sin = a.x*b.y - a.y*b.x;
		
		double angle = Math.atan2(sin, cos);

		return angle;
	}
	
	/**
	 * Positionner une valeur entre 2 bordures min et max
	 * @param v
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clamp(double v, double min, double max) {
		if (v<min) return min;
		if (v>max) return max;
		return v;
	}
	
	/**
	 * Tourne un vecteur d'un angle passé en paramètre
	 * @param v
	 * @param angle
	 */
	public static void turnVector(Vector2d v, double angle) {
		double length = v.length();
		if (length==0.) return;
		double currentAngle = signedAngle(new Vector2d(1,0), v);
		currentAngle += angle;
		v.set(Math.cos(currentAngle),Math.sin(currentAngle));
		v.scale(length);
	}
	
	/**
	 * Créé un vecteur à partir d'un angle passé en paramètre
	 * @param angle
	 * @return
	 */
	public static Vector2d toOrientationVector(double angle) {
		return new Vector2d(
				Math.cos(angle),
				Math.sin(angle));
	}
	
	/**
	 * Renvoi l'angle qui correspond à une orientation passée en paramètre
	 * @param orientation
	 * @return
	 */
	public static double toOrientationAngle(Vector2d orientation) {
		return Math.acos(orientation.x);
	}

	/**
	 * Calcul d'intersection (unused)
	 * @param a
	 * @param b
	 * @param upLeftCorner
	 * @param upRight
	 * @return
	 */
	public static boolean intersect(Point2d a, Point2d b, Point2d upLeftCorner, Point2d upRight) {
		// TODO Auto-generated method stub
		return false;
	}

}