package HIP;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class shape {
	public int xcenter;
	public int ycenter;
	public String name;
	protected Polygon poly;
	public int angleStep = 90;
	public ArrayList<Integer> orientations = new ArrayList<Integer>();

	public shape(){  
	     poly = new Polygon();
	}
	
	public ArrayList<Integer> getOrientations(){
		if(this.name == "c"){
			orientations.add(0);
			return orientations;
		}
		int angle = 0;
		while(angle < 360){
			orientations.add(angle);
			angle += angleStep;
		}
		return orientations;
	}
	
	public double getArea(){
		return PolygonUtilities.area(getVertices2D());		
	}
	
	public Point2D[] getVertices2D(){
		Point2D [] points = new Point2D[poly.npoints];
		for (int i = 0; i < poly.npoints; i++) {
			points[i] = new Point2D.Double();
			points[i].setLocation(poly.xpoints[i], poly.ypoints[i]);					
		}
		if(points.length == 0) 
			System.err.print("Size of point array is Zero: getVertices2D/shape.java");
		return points;
	}
	
	public void setCenterToLocation(int x, int y){  
		Polygon translatedPoly = new Polygon();
		for (int i = 0; i < poly.npoints; i++) {
	    	 translatedPoly.addPoint(poly.xpoints[i]+x-xcenter,poly.ypoints[i]+y-ycenter);
	    }
		xcenter = x;
		ycenter = y; 
		poly = translatedPoly;
	}
	
	public void printPolygon(){
		for (int i = 0; i < poly.npoints; i++) {
			System.out.print(poly.xpoints[i] + "|" + poly.ypoints[i]+ "  ");
		}
		System.out.println();
	}
	
	public void printSpecs(){
		if(name == "c"){
			((circle) this).printYourSelf();
		}else if(name == "r"){
			((rectangle) this).printYourSelf();
		}else if(name == "cw"){
			((circleWedge) this).printYourSelf();
		}else if(name == "t"){
			((triangle) this).printYourSelf();
		}			
	}
	
	public void applyRotation(double angleInDegree){
		double angle = Math.toRadians(angleInDegree);		
		Polygon result = new Polygon();
		
		for (int i = 0; i < poly.npoints; i++) {			
			//TRANSLATE TO ORIGIN
			double x1 = poly.xpoints[i] - xcenter;
			double y1 = poly.ypoints[i] - ycenter;			

			double newX;
			double newY;
			
			//APPLY ROTATION
			newX = x1 * Math.cos(angle) - y1 * Math.sin(angle);
			newY = x1 * Math.sin(angle) + y1 * Math.cos(angle);

			//TRANSLATE BACK
			result.addPoint((int)(newX + xcenter), (int)(newY + ycenter));
		}
		poly = result;
	}	
}