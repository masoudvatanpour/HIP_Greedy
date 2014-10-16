package HIP;

import java.util.ArrayList;

public class sensorSet {
	
	public ArrayList<shape> set;
//	public ArrayList<shape> setWithOrientations;
	public int angleStep = 180;
	
    public sensorSet(){
    	init();
//    	setOrientations();
    }
    
//    public void setOrientations(){
//    	setWithOrientations = new ArrayList<shape>();
//    	for (shape sensor : set) {
//    		for(int orientation : sensor.getOrientations()){
//    			sensor.applyRotation(orientation);
//    			setWithOrientations.add(sensor);
//    		}			
//		}    	
//    }
    
    public shape pickSensorAt(int index){
    	if(set.size() == 0){
    		System.err.println("Ran Out of Sensors: pickSensor/sensorSet");
    		return null;
    	}
    	shape result = set.get(index);
    	set.remove(index);    	
    	return result;
    }
    
    public void removeSensorAt(shape sensor){    	
    	set.remove(sensor);
    }
    
    public int getSetSize(){
    	return set.size();
    }
    
    public void printSet(){
		System.out.println("Printing Set ...");
    	for (int i = 0; i < set.size(); i++)
    		set.get(i).printSpecs();    		
    	System.out.println();			 	
    }
    
    public void init(){
    	set = new ArrayList<shape>();
    	for (int i = 0; i < 5; i++) {
        	set.add(new rectangle(114,86));
		}
    	for (int i = 0; i < 5; i++) {
    		set.add(new circle(57, 100));
		} 
        for (int i = 0; i < 5; i++) {
            set.add(new rectangle(100,100));
        }

//    	for (int i = 0; i < 1; i++) {
//        	set.add(new circleWedge(15,90,0,360));
//		}        
    	
//    	for (int i = 0; i < 1; i++) {
//        	set.add(new triangle(15,90,0));
//		}
    }
}
