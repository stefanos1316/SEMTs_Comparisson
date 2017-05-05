public class Load {
    /**
     * Starts the Load Generation
     * @param args Command line arguments, ignored
     */
    public static void main(String[] args) {

	//Check if the user gave the correct number of command line arguments
        if ( args.length < 4 ) {
                System.out.println("Error: Invalid number of command line arguments");
		System.out.println("Enter the following parameters in order to execute:");
		System.out.println("$3 -> Number of Cores (Physical)");
		System.out.println("$4 -> Number of threads per Core");
		System.out.println("$5 -> Load per logical core (e.g., 1 for full, 0.5 for half)");
		System.out.println("$6 -> Duration (give interger for minutes)");
		System.exit(1);
        }

	int numCore = Integer.parseInt(args[0]);
        int numThreadsPerCore = Integer.parseInt(args[1]);
        double load = Double.parseDouble(args[2]);
        final long duration = 100000 * Integer.parseInt(args[3]);

        for (int thread = 0; thread < numCore * numThreadsPerCore; thread++) {
            new BusyThread("Thread" + thread, load, duration).start();
        }
    }

    /**
     * Thread that actually generates the given load
     * @author Sriram
     */
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
