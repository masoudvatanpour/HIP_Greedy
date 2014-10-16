package HIP;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class showImage {

    public JFrame mainMap;
    public ArrayList<shape> shapes;
    public BufferedImage image;

    public showImage() {
		  mainMap = new JFrame();
//		  mainMap.setResizable(false);
		  mainMap.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		  shapes = new ArrayList<shape>();
		  
		  try {
				image = ImageIO.read(new File(global.pictureName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		 
    }

    public void paintAll() {
        JPanel p = new JPanel() {
        	public void paintComponent(Graphics g) {
        	   super.paintComponent(g);
        	   g.drawImage(image, 0, 0, null); 
               g.setColor(Color.BLUE);
               for (int i = 0; i < shapes.size(); i++) {
            	   g.fillOval(shapes.get(i).xcenter -1, shapes.get(i).ycenter -1, 2, 2);
            	   g.drawPolygon(shapes.get(i).poly);
//            	   shapes.get(i).printPolygon();
               }               
           }
        	
    	   public Dimension getPreferredSize() {
               return new Dimension(image.getWidth(), image.getHeight());
           }
        };
        mainMap.add(p);
        mainMap.pack();
        mainMap.setVisible(true);
    }
    
    public void fillShapes(String name, int x, int y, Polygon poly, int orientation){
    	shape s = new shape();
    	s.name = name;
    	s.setCenterToLocation(x, y);
    	s.poly = poly;
    	s.applyRotation(orientation);
    	shapes.add(s);
    }
    
    public void printArrayList(){
    	for(shape sensor: shapes){
    		sensor.printSpecs();
    	}
    }
}
