import java.io.BufferedReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.PrintWriter;

public class EnergyCheckUtils {
	public native static int scale(int freq);
	public native static int[] freqAvailable();

	public native static double[] GetPackagePowerSpec();
	public native static double[] GetDramPowerSpec();
	public native static void SetPackagePowerLimit(int socketId, int level, double costomPower);
	public native static void SetPackageTimeWindowLimit(int socketId, int level, double costomTimeWin);
	public native static void SetDramTimeWindowLimit(int socketId, int level, double costomTimeWin);
	public native static void SetDramPowerLimit(int socketId, int level, double costomPower);
	public native static int ProfileInit();
	public native static int GetSocketNum();
	public native static String EnergyStatCheck();
	public native static void ProfileDealloc();
	public native static void SetPowerLimit(int ENABLE);
	public static int wraparoundValue;

	public static int socketNum;
	static {
		System.setProperty("java.library.path", System.getProperty("user.dir"));
//				System.out.print(System.getProperty("user.dir"));
		try {
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		} catch (Exception e) { }

		//System.loadLibrary("CPUScaler");
	
		//Modification added by Stefanos Georgiou
		System.load("/home/sgeorgiou/GitHub/SEMTs_Comparisson/jRAPL/libCPUScaler.so");
		wraparoundValue = ProfileInit();
		socketNum = GetSocketNum();
	}

	/**
	 * @return an array of current energy information.
	 * The first entry is: Dram/uncore gpu energy(depends on the cpu architecture.
	 * The second entry is: CPU energy
	 * The third entry is: Package energy
	 */

	public static double[] getEnergyStats() {
		socketNum = GetSocketNum();
		String EnergyInfo = EnergyStatCheck();
		/*One Socket*/
		if(socketNum == 1) {
			double[] stats = new double[3];
			String[] energy = EnergyInfo.split("#");

			stats[0] = Double.parseDouble(energy[0]);
			stats[1] = Double.parseDouble(energy[1]);
			stats[2] = Double.parseDouble(energy[2]);

			return stats;

		} else {
		/*Multiple sockets*/
			String[] perSockEner = EnergyInfo.split("@");
			double[] stats = new double[3*socketNum];
			int count = 0;


			for(int i = 0; i < perSockEner.length; i++) {
				String[] energy = perSockEner[i].split("#");
				for(int j = 0; j < energy.length; j++) {
					count = i * 3 + j;	//accumulative count
					stats[count] = Double.parseDouble(energy[j]);
				}
			}
			return stats;
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException,NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException, InterruptedException{	
	
		//Added from Stefanos
		if ( args.length < 4 ) {
                	System.out.println("Error: Invalid number of command line arguments");
			System.out.println("Enter the following parameters in order to execute:");
			System.out.println("$3 -> Number of Cores (Physical)");
			System.out.println("$4 -> Number of threads per Core");
			System.out.println("$5 -> Load per logical core (e.g., 1 for full, 0.5 for half)");
			System.out.println("$6 -> Duration (give interger for minutes)");
			System.exit(1);
        	}

		int numCore = Integer.parseInt(args[2]);
      	  	int numThreadsPerCore = Integer.parseInt(args[3]);
        	double load = Double.parseDouble(args[4]);
     	   	final long duration = 100000 * Integer.parseInt(args[5]);

		PrintWriter writer = new PrintWriter(args[1],"UTF-8");
		long start_time = System.currentTimeMillis();
		double[] before = getEnergyStats();

		//Process proc = Runtime.getRuntime().exec("java -jar SPECjvm2008.jar -ikv -ict "+args[0]);
	    	//proc.waitFor();
		
	

        	for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            		new BusyThread("Thread" + thread, load, duration).start();
        	}
		long startTime = System.currentTimeMillis();
            	try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    	}
                	}
           	 } catch (InterruptedException e) {
                e.printStackTrace();
            	}

	
		double[] after = getEnergyStats();
		long end_time = System.currentTimeMillis();

		long difference = end_time-start_time;
		long seconds = (difference / 1000) % 60;
		//Line below will convert milliseconds to seconds
		System.out.println("Elapse time is "+(difference / 1000) % 60);
	
		//Call method to create log files
		//collectExecutionInformation( after, before, args);
	
		for(int i = 0; i < socketNum; i++) {
			writer.println("Power consumption of dram: " + (after[0] - before[0])  + " power consumption of cpu: " + (after[1] - before[1])  + " joules, power consumption of package: " + (after[2] - before[2]) + " joules");
		}
		writer.close();
		//System.out.println("Took "+(endTime - startTime) + " ns");
		ProfileDealloc();
	}
	
    
    //This part of code will capture information and create log files
    static void collectExecutionInformation(double[] after, double[] before,String[] argument) throws IOException{
    	
    	//Initialize parameters
    	//Name the file
		RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
		List<String> arguments = runtimeMxBean.getInputArguments();
		
		System.out.println(arguments.get(0));
		
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	Date date = new Date();
    	DecimalFormat df = new DecimalFormat("#.####");
    	String[] splinter = argument[2].split("_");		
		String[] splinter_again = splinter[2].split("\\.");
    	System.out.println(dateFormat.format(date));
        FileWriter writer = new FileWriter(argument[3]+"/"+argument[0]+"_Smell.Compiler_"+arguments.get(0)+"."+argument[1]+"_Choice.MethodsCall_"+splinter_again[0]+"."+dateFormat.format(date)+".txt",true);
        //writer.write("Start time = "+dateFormat.format(executionStart)+"\n");
        //writer.write("End time = "+dateFormat.format(executionEnd)+"\n");
       // writer.write("Execution time = "+(end-start)+" ns\n");
        writer.write("Energy Code Smell Name = "+argument[0]+"\n");  
        writer.write("Execution parameters: "+argument[0]+" "+argument[1]+" "+argument[2]);
        
        if (argument[1].equals("0"))
        	writer.write("Execution type = out of line method\n");
        else
        	writer.write("Execution type = in line method\n");
        
        writer.write("GPU power consumption = "+df.format((after[0] - before[0])) +" joules\n");
        writer.write("CPU power consumption = "+df.format((after[1] - before[1])) +" joules\n");
        writer.write("Package power consumption = "+df.format((after[2] - before[2])) +" joules\n");
        writer.write("Total power consumption = "+df.format(((after[0] - before[0]) + (after[1] - before[1]) + (after[2] - before[2]))) +" joules\n\n");
        writer.close();
        
        FileWriter writeToPlotdData;
        
		if (argument[1].equals("0"))
        	 writeToPlotdData = new FileWriter(argument[3]+"/plotData_Out_Of_Line_"+arguments.get(0)+"_"+argument[1]+"_"+splinter_again[0]+".txt",true);
        else
        	 writeToPlotdData = new FileWriter(argument[3]+"/plotData_In_Line_"+arguments.get(0)+"_"+argument[1]+"_"+splinter_again[0]+".txt",true);
        
        writeToPlotdData.write(df.format(((after[0] - before[0]) + (after[1] - before[1]) + (after[2] - before[2])))+"\n");
        writeToPlotdData.close();   	
    }
 


	private static class BusyThread extends Thread {
        private double load;
        private long duration;

        /**
         * Constructor which creates the thread
         * @param name Name of this thread
         * @param load Load % that this thread should generate
         * @param duration Duration that this thread should generate the load for
         */
        public BusyThread(String name, double load, long duration) {
            super(name);
            this.load = load;
            this.duration = duration;
        }

        /**
         * Generates the load when run
         */
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            try {
                // Loop for the given duration
                while (System.currentTimeMillis() - startTime < duration) {
                    // Every 100ms, sleep for the percentage of unladen time
                    if (System.currentTimeMillis() % 100 == 0) {
                        Thread.sleep((long) Math.floor((1 - load) * 100));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
