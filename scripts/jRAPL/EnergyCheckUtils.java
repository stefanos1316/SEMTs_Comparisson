import java.lang.reflect.Field;

//START ADDING HERE ALL IMPORRTS
import java.util.Arrays;


//STOP ADDING IMPORTS


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
		try {
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);
		} catch (Exception e) { }

		System.loadLibrary("CPUScaler");
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
		System.out.println(EnergyInfo);
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

	public static void main(String[] args) {    

		double[] before = getEnergyStats();
    	
    	
    	//Give command line arguments in order run this applications
    	//Command line arguments
    	// $1 -> number of random int elements in the array
    	// $2 -> the number of iteration to execute each sorting algorithm
    	// ${3,4,5,6,7} -> are the numbers for of sorting algorithms
    	
    	if ( args.length < 3 ) {
    		System.out.println("The given number of command line arguments is wrong");
    		System.out.println("$1 -> the number of random int elements in an unsorted array");
    		System.out.println("$2 -> the number of iteration to execute each sorting algorithm");
    		System.out.println("${3,4,5,6,7} -> 3 for bubbleSort, 4 for selectionSort, 5 for insertionSort,");
    		System.out.println("6 for benchmarkQuickSort, and 7 for benchmarkMergeSort");
    		System.out.println("Please, try again!");
    		System.exit(1);
    	}
   	
    	if (Integer.parseInt(args[0]) > 1000000) {
    		System.out.println("Please provide a smaller number not larger than 1,000,000");
    		System.exit(2);
    	}
        
        int numberOfIterations = Integer.parseInt(args[1]);    
        int[] giveSortingAlgorithms = new int[args.length - 2];
        for (int i = 2; i < args.length; ++i) {
        	giveSortingAlgorithms[i-2] = Integer.parseInt(args[i]);
        }
        
        System.out.println("Printing new array " + Arrays.toString(giveSortingAlgorithms));
        
        //Iterate array with selection and run the sorting algorithms
        for ( int i = 0; i < giveSortingAlgorithms.length; ++ i) {
        	switch (giveSortingAlgorithms[i]) {
			case 3:
				System.out.println("Executing bubbleSort Algorithm " + numberOfIterations + " times");
				for (int j = 0; j < numberOfIterations; ++j) {
					int[] unsortedArray = createArrayWithRandomInts(Integer.parseInt(args[0]));
					bubbleSort(unsortedArray);
					System.out.println("Done with " + (j+1) + " execution");
				}
				break;
			
			case 4:
				System.out.println("Executing selectionSort Algorithm " + numberOfIterations + " times");
				for (int j = 0; j < numberOfIterations; ++j) {
					int[] unsortedArray = createArrayWithRandomInts(Integer.parseInt(args[0]));
					selectionSort(unsortedArray);
					System.out.println("Done with " + (j+1) + " execution");
				}
				break;

			case 5:
				System.out.println("Executing insertionSort Algorithm " + numberOfIterations + " times");
				for (int j = 0; j < numberOfIterations; ++j) {
					int[] unsortedArray = createArrayWithRandomInts(Integer.parseInt(args[0]));
					insertionSort(unsortedArray);
					System.out.println("Done with " + (j+1) + " execution");
				}
				break;

			case 6:
				System.out.println("Executing benchmarkQuickSort Algorithm " + numberOfIterations + " times");
				for (int j = 0; j < numberOfIterations; ++j) {
					int[] unsortedArray = createArrayWithRandomInts(Integer.parseInt(args[0]));
					benchmarkQuickSort(unsortedArray);
					System.out.println("Done with " + (j+1) + " execution");
				}
				break;

			case 7:
				System.out.println("Executing benchmarkMergeSort Algorithm " + numberOfIterations + " times");
				for (int j = 0; j < numberOfIterations; ++j) {
					int[] unsortedArray = createArrayWithRandomInts(Integer.parseInt(args[0]));
					benchmarkMergeSort(unsortedArray);
					System.out.println("Done with " + (j+1) + " execution");
				}
				break;

			default:
				break;
			}
        }
  	
	//System.exit(1);     


    	
		double[] after = getEnergyStats();
		for(int i = 0; i < socketNum; i++) {
			System.out.println("Power consumption of dram: " + (after[0] - before[0]) / 10.0 + " power consumption of cpu: " + (after[1] - before[1]) / 10.0 + " power consumption of package: " + (after[2] - before[2]) / 10.0);
		}
		ProfileDealloc();
	}

//ADD HERE ALL FUNCTIONS

{

    /**
     * Sorting an array of ints in ascending order using bubbleSort
     * Best-Case Complexity: O(n), Average Complexity: O(n^2), Worst-Case Complexity: O(n^2)
     * O(n) is achieved in Best-Case (already sorted array) using the alreadySorted flag
     * @param array
     * @return
     */
    static int[] bubbleSort(int[] array)
        int temp;
        long start = System.currentTimeMillis();
        boolean alreadySorted = true;
        for (int i = 0; i < array.length; i++)
        {
            for (int j = 0; j < array.length - 1; j++)
            {
                if (array[j] > array[j + 1])
                {
                    alreadySorted = false;
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
            if (alreadySorted == true)
            {
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Array sorted with bubble sort in :" + (end - start) + "ms");
        return array;
    }

    /**
     * Sorting an array of ints in ascending order using selectionSort
     * Best-Case Complexity: O(n^2), Average Complexity: O(n^2), Worst-Case Complexity: O(n^2)
     * @param array
     * @return
     */
    static int[] selectionSort(int[] array)
    {
        int min;
        int pos = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < array.length - 1; i++)
        {
            min = array[i];
            for (int j = i + 1; j < array.length; j++)
            {
                if (array[j] < min)
                {
                    min = array[j];
                    pos = j;
                }
            }
            array[pos] = array[i];
            array[i] = min;
        }
        long end = System.currentTimeMillis();
        System.out.println("Array sorted with selection sort in :" + (end - start) + "ms");
        return array;
    }

    /**
     * Sorting an array of ints in ascending order using insertionSort
     * Best-Case Complexity: O(n), Average Complexity: O(n^2), Worst-Case Complexity: O(n^2)
     * @param array
     * @return
     */
    static int[] insertionSort(int[] array)
    {
        long start = System.currentTimeMillis();
        int j;
        for (int i = 1; i < array.length; i++)
        {
            int key = array[i];

            for (j = i - 1; (j >= 0) && (key < array[j]); j--)
            {
                array[j + 1] = array[j];
            }
            array[j + 1] = key;
        }
        long end = System.currentTimeMillis();
        System.out.println("Array sorted with insertion sort in :" + (end - start) + "ms");

        return array;
    }

    /**
     * Sorting an array of ints in ascending order using quickSort
     * Best-Case Complexity: O(n log(n)), Average Complexity: O(n log(n)), Worst-Case Complexity: O(n^2))
     * @param array
     * @return
     */
    static void quickSort(int[] array, int low, int high)
    {
        int pivot = array[low + ((high - low) / 2)];
        int i = low;
        int j = high;
        while (i <= j)
        {
            while (array[i] < pivot)
            {
                i++;
            }
            while (array[j] > pivot)
            {
                j--;
            }
            if (i <= j)
            {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j)
        {
            quickSort(array, low, j);
        }

        if (i < high)
        {
            quickSort(array, i, high);
        }
    }

    /**
     * Helping method to benchmark quick sort's execution time
     * @param array
     */
    static void benchmarkQuickSort(int[] array)
    {
        long start = System.currentTimeMillis();
        quickSort(array, 0, array.length - 1);
        long end = System.currentTimeMillis();
        System.out.println("Array sorted with quick sort in :" + (end - start) + "ms");
    }

    /**
     * Sorting an array of ints in ascending order using mergeSort
     * Best-Case Complexity: O(n log(n)), Average Complexity: O(n log(n)), Worst-Case Complexity: O(n log(n)))
     * @param array
     * @return
     */
    public static int[] mergeSort(int[] array)
    {
        if (array.length == 1)
        {
            return array;
        }

        int[] array1 = new int[(array.length/2)];
        int[] array2 = new int[(array.length-array1.length)];

        System.arraycopy(array, 0, array1, 0, array1.length);
        System.arraycopy(array, array1.length, array2, 0, array2.length);

        mergeSort(array1);
        mergeSort(array2);

        merge(array1, array2, array);
        return array;
    }

    /**
     * Merges 2 sorted arrays of ints
     * @param array1
     * @param array2
     * @param mergedArray
     * @return
     */
    static void merge(int[] array1, int[] array2, int[] mergedArray)
    {
        int array1Index = 0;
        int array2Index = 0;
        int pos = 0;
        while ((array1Index < array1.length) && (array2Index < array2.length))
        {
            if (array1[array1Index] < array2[array2Index])
            {
                mergedArray[pos] = array1[array1Index];
                array1Index++;
                pos++;
            } else
            {
                mergedArray[pos] = array2[array2Index];
                array2Index++;
                pos++;
            }
        }

        if (array1Index < array2Index)
        {
            System.arraycopy(array1, array1Index, mergedArray, pos, array1.length - array1Index);
        }
        else if (array2Index < array1Index) ;
        {
            System.arraycopy(array2, array2Index, mergedArray, pos, array2.length - array2Index);
        }
    }

    /**
     * Helping method to benchmark merge sort's execution time
     * @param array
     */
    static void benchmarkMergeSort(int[] array)
    {
        long start = System.currentTimeMillis();
        mergeSort(array);
        long end = System.currentTimeMillis();
        System.out.println("Array sorted with merge sort in :" + (end - start) + "ms");
    }


    /**
     * Creates and returns an array with random ints
     * @param size the size of the array to be created
     * @return
     */
    static int[] createArrayWithRandomInts(int size)
    {
        int[] array = new int[size];

        for (int i = 0; i < size; i++)
        {
            array[i] = (int) (Math.random() * Math.random() * 100000);
        }
        return array;
    }

    /**
     * Prints the elements of  one dimensional array of type int
     * @param array
     */
    static void printArray(int[] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

}


}
