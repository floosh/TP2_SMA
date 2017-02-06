package fr.disp.polytech.sma.tp1.data.box;

import java.util.ArrayList;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import fr.disp.polytech.sma.tp1.util.GeometryUtil;

 

public class AABoundingBox {

	private double height; //distance sur l'axe des ordonnees
	private double width; // distance sur l'axe des abscisses

	public Point2d lower = new Point2d();
	public Point2d upper = new Point2d();
	private boolean isBoundInit;

	public  AABoundingBox(ArrayList<Double> beaconsData) {

		Vector2d baliseG = new Vector2d();
		baliseG.x =   (beaconsData.get(4)/1000)* Math.sin(-(beaconsData.get(5)/100)*Math.PI/180);
		baliseG.y =  (beaconsData.get(4)/1000)* Math.cos(-(beaconsData.get(5)/100)*Math.PI/180); 


		Vector2d baliseD = new Vector2d();
		baliseD.x = (beaconsData.get(0)/1000)* Math.sin(-(beaconsData.get(1)/100)*Math.PI/180);
		baliseD.y = (beaconsData.get(0)/1000)* Math.cos(-(beaconsData.get(1)/100)*Math.PI/180);

		Vector2d baliseHG = new Vector2d(baliseD.x-baliseG.x, baliseD.y- baliseG.y);
		baliseHG.normalize();
		GeometryUtil.turnVector(baliseHG, Math.toRadians(90));
		baliseHG.scale(1);
		baliseHG.add(baliseG);

		Vector2d baliseHD = new Vector2d(baliseG.x-baliseD.x, baliseG.y- baliseD.y);
		baliseHD.normalize();
		GeometryUtil.turnVector(baliseHD, Math.toRadians(-90));
		baliseHD.scale(1);
		baliseHD.add(baliseD);

		
		
		if(baliseG.y < baliseD.y)
		{
		
			this.lower.y = baliseG.y-1;
			this.lower.x = baliseHG.x-1;
			
			this.upper.x= baliseD.x+1;
			this.upper.y = baliseHD.y+1;
			
			//System.out.println(baliseD);
			//System.out.println(baliseHG);
			//this.lower.set(baliseD);
			//this.upper.set(baliseHG);
			this.setBoundInit(true);
			//checkBounds();
		}
		else{
			
			this.lower.y = baliseD.y-1;
			this.lower.x = baliseG.x-1;
			
			this.upper.x= baliseHD.x+1;
			this.upper.y = baliseHG.y+1;
			
			//System.out.println(baliseG);
			//System.out.println(baliseHD);
			//this.lower.set(baliseG);
			//this.upper.set(baliseHD);
			this.setBoundInit(true);
			//checkBounds();
		}
		//checkBounds();

	}
	
	public AABoundingBox(AABoundingBox car) {
		this.upper=car.upper;
		this.lower=car.lower;
	}

	protected void checkBounds() {
		if (this.upper.x < this.lower.x) {
			double t = this.upper.x;
			this.upper.x = this.lower.x;
			this.lower.x = t;
		}
		if (this.upper.y < this.lower.y) {
			double t = this.upper.y;
			this.upper.y = this.lower.y;
			this.lower.y = t;
		}
		isBoundInit=true;
	}
	
	public boolean intersects(Point2d p) {
		
/*		System.out.println(this.lower.x+"<="+p.x+" && "+p.x+"<="+this.upper.x);
		System.out.println(this.lower.y+"<="+p.y+" && "+p.y+"<="+this.upper.y);
		System.out.println("-----------");*/
		return  (this.lower.x   <=p.x && p.x<=this.upper.x 
				&&
				this.lower.y<=p.y && p.y<=this.upper.y);
	}

	public boolean isInit() {
		return isBoundInit;
	}

	public void setBoundInit(boolean isBoundInit) {
		this.isBoundInit = isBoundInit;
	}

	public boolean intersects(Vector2d vecDir) {
		// TODO Auto-generated method stub
		return intersects(new Point2d(vecDir.x,vecDir.y));
	}
	
 
	public String toString() {
		return this.lower.x+" "+this.upper.y+" "+(this.upper.x-this.lower.x)+" "+ (this.upper.y-this.lower.y);
	}

	public boolean intersects(double d, double y) {
		// TODO Auto-generated method stub
		return intersects(new Point2d(d, y));
	}

}