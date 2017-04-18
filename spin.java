//package stressCPU;

public class spin {

	private static void spinner(int milliseconds){
			long sleepTime = milliseconds*1000000L; // convert to nanoseconds
			    long startTime = System.nanoTime();
			    while ((System.nanoTime() - startTime) < sleepTime) {}
			
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int NUM_TESTS = 1000;
	    long start = System.nanoTime();
	   // Thread[] thread = new Thread[8];
	    for (int i = 0; i < NUM_TESTS; i++) {
	      //  for (int j = 0; j < 8; ++j) 
	       // {
	        //	new Thread(new Runnable() {
	        //	     public void run() {
	        	          // code goes here.
	        	    	 spinner(500);
	        //	     }
	        //	}).start();
	       // }
	    }
	    System.out.println("Took " + (System.nanoTime()-start)/1000000 +
	        "ms (expected " + (NUM_TESTS*500) + ")");
	}

}
