package fr.disp.polytech.sma.tp1.sma.environment.objet;
 
import java.util.concurrent.TimeUnit;

import javax.vecmath.Point2d;

import org.arakhne.tinyMAS.situatedEnvironment.environment.SituatedObject;

import fr.disp.polytech.sma.tp1.sma.environment.WorldModel;
 
/**
 * Classe EnvironmentObject
 */
public abstract class EnvironmentObject implements SituatedObject{

	private double posX;
	private double posY;
	protected int identifier;
	protected WorldModel model;
	protected boolean mousePicked;
	protected boolean open;
	
	/**
	 * Set le worldModel à celui passé en paramètre
	 * @param model
	 */
	public void setModel(WorldModel model)
	{
		this.model = model;
	}
	
	/**
	 * Set le Picker à celui passé en paramètre
	 * @param mousePicked
	 */
	public void setMousePicked(boolean mousePicked) {
		this.mousePicked = mousePicked;
		if(mousePicked)
			open = !open;
	}
	
	/**
	 * Informe du caractère
	 * @return
	 */
	public boolean isMousePicked() {
		return mousePicked;
	}

	public double getSimulationStepDuration() {
		return model.getSimulationStepDuration();
	}
	public double getSimulationStepDuration(TimeUnit arg0) {
		return model.getSimulationStepDuration(arg0);
	}
	public double getSimulationTime() {
		return model.getSimulationTime();
	}
	public double getSimulationTime(TimeUnit arg0) {
		return model.getSimulationTime(arg0);
	}
	public double perSecond(double arg0) {
		return 0;
	}
	
	/**
	 * Renvoi le type de l'objet 
	 * @return
	 */
	public abstract PerceptionType getPerceptionType();
	
	/**
	 * Renvoi la position selon x de l'objet
	 * @return
	 */
	public double getPosX()
	{
		return this.posX;
	}
	
	/**
	 * Renvoi la position selon y de l'objet
	 * @return
	 */
	public double getPosY()
	{
		return this.posY;
	}

	/**
	 * Set la position de l'objet dans l'environement
	 * @param point2d
	 */
	public void setLocation(Point2d point2d) {
		this.setPosX(point2d.x);
		this.setPosY(point2d.y);
		
	}

	/**
	 * Renvoi la position de l'objet dans l'environement
	 * @return
	 */
	public Point2d getLocation() {
		return new Point2d(getPosX(),getPosY());
	}

	/**
	 * Définit la position en X
	 * @param posX
	 */
	public void setPosX(double posX) {
		this.posX = posX;
	}

	/**
	 * Définit la position en Y
	 * @param posY
	 */
	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	public WorldModel getWorld(){
		return this.model;
	}
	
	
}
