package HIP;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main implements Runnable {
	int n = 2; // how many sensors do you want to deploy?
    public int[][] room = new int[global.hight][global.width];
    boolean [][] roomCovered = new boolean[global.hight][global.width];
	int roomCopy[][] = new int [global.hight][global.width];
    double posTot;
    long startTime;
    long after1Iter;
    long endTime;
    public ArrayList<shape> solution = new ArrayList<shape>();
    public static showImage sI;

    public void readImage()
    {
    	try {
            BufferedReader in = new BufferedReader(new FileReader(global.filename));
            String str;
            int row = 0;
            str = in.readLine();
            while ((str = in.readLine()) != null) {
                String[] strArray = str.split(",");
                int[] intArray = new int[strArray.length];
                for(int i = 0; i < strArray.length; i++) {
                    intArray[i] = Integer.parseInt(strArray[i]);
                }           
                for (int i = 0; i < intArray.length; i++) {
					room[row][i] = intArray[i];
				}
                row ++;               
            }
            in.close();
        } catch (IOException e) {
            System.out.println("File Read Error");
        }
    	posTot = 0;
    	for (int i = 0; i < global.hight; i++) {
			for (int j = 0; j < global.width; j++) {
				if(room[i][j]>0){
					posTot += room[i][j];
				}
			}
    	}
		System.out.println("Heat to cover: " + posTot);
		System.out.println("-------------------");

    }
    
    public void run(){    
	    readImage();	
	    sI = new showImage();
//	    start the clock!
    	startTime = System.nanoTime();
    	for (int j = 0; j < global.hight; j++) {
			for (int j2 = 0; j2 < global.width; j2++) {
				roomCopy[j][j2] = room[j][j2];	
				roomCovered[j][j2] = false;
			}
		}
    	sensorSet s = new sensorSet();
    	int cost;
    	int xsave;
    	int ysave;
    	int osave;
    	long tempTime;
    	shape selectedSensor[] = new shape[n]; 
    	
    	int totalMaxCost = 0;
    	for (int i = 0; i < n; i++) {
        	int maxCost = 0;
        	xsave = -1;
        	ysave = -1;
        	osave = -1;
        	for(shape sensor : s.set){  
        		for(int orientation : sensor.getOrientations()){
        			shape temp = null;
        			temp = sensor;   	      
        			temp.applyRotation(orientation);
//        			sensor.applyRotation(orientation);
        			for (int y = 0; y < global.hight; y++) {
        				for (int x = 0; x < global.width; x++) {
        					cost = 0;
							temp.setCenterToLocation(x, y); 
							Rectangle rect = temp.poly.getBounds();							
							for (int j = (int)rect.getMinY(); j < rect.getMaxY(); j++) {
				    			for (int k = (int)rect.getMinX(); k < rect.getMaxX(); k++) { 
				    	    		if(j >=0 && k >=0 && j < global.hight && k < global.width){
				    					if(temp.poly.contains(k,j)){					
				    						cost += roomCopy[j][k];  			    						
				    					}	    					
				    	    		}
				    			}
				    		}
							if(cost > maxCost){
				    			maxCost = cost;
								xsave = x;
								ysave = y;
								osave = orientation;
								selectedSensor[i] = temp;
				    		}														
						}				  							    					    		
					}				
        		}
        	}	
        	totalMaxCost += maxCost;
        	selectedSensor[i].setCenterToLocation(xsave, ysave);        	
	    	Rectangle rectangle = selectedSensor[i].poly.getBounds();
	    	s.removeSensorAt(selectedSensor[i]);
			for (int j = (int)rectangle.getMinY(); j < rectangle.getMaxY(); j++){ 
    			for (int k = (int)rectangle.getMinX(); k < rectangle.getMaxX(); k++) {
    	    		if(j >=0 && k >=0 && j < global.hight && k < global.width){
    					if(selectedSensor[i].poly.contains(k,j)){
    						roomCopy[j][k] -= 1;
    					}
    	    		}
    			}
			}	
			selectedSensor[i].printSpecs();
			sI.fillShapes(selectedSensor[i].name, selectedSensor[i].xcenter, selectedSensor[i].ycenter, selectedSensor[i].poly, osave);
			tempTime = System.nanoTime();
			long duration = tempTime - startTime;
			System.out.println("Time till Now: " + duration);
			System.out.println("Accuracy till Now: " + totalMaxCost);
			System.out.println("------------------- " + i + " sensor placed");
    	}       
//		endTime = System.nanoTime();
//		long duration = endTime - startTime;
//		System.out.println("Elapsed time: " + duration);
		saveImage();			
	}
    
    public void print2DArray(int [][] array){
    	for (int i = 80; i < 120; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if(array[i][j] == 0)System.out.print(" ");
				if(array[i][j] == 1)System.out.print("-");
				if(array[i][j] == 2)System.out.print("=");
			}
			System.out.println();
		}
    }
    
    public void saveImage(){    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	sI.paintAll();
            }
        });
    }
}    
   