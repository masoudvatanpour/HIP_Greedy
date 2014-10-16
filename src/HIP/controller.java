package HIP;


public class controller {	
		Main main;
		public controller(){	
			main = new Main();
			Thread t1 = new Thread(main);			
			t1.start();						
		}			   
		
}

