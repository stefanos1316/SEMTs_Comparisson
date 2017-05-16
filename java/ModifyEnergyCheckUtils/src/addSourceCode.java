import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class addSourceCode {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> listOfStrings = new ArrayList();
		ArrayList<String> removeList = new ArrayList();

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line;

			while ((line = br.readLine()) != null) {
				// process the line.
				// Find which point to create and add the source code between
				// the doulbe[] before and after getEnergyStats(); function
				listOfStrings.add(line);
			}

			removeList = getMainBlock(listOfStrings);
			getRemaining(listOfStrings, removeList);
		}

	}

	// Function to get the main code block
	public static ArrayList<String> getMainBlock(ArrayList<String> listOfStrings)
			throws FileNotFoundException, UnsupportedEncodingException {
		// At this part of the program we will check the list to identify the
		// main
		ArrayList<String> newList = new ArrayList();
		for (int i = 0; i < listOfStrings.size(); ++i) {
			// System.out.println(listOfStrings.get(i).toString());
			if (listOfStrings.get(i).contains("public static void main")) {
				// System.out.println("Found you");

				// At this point of the code will find all the right curly
				// brackets and
				int j = 0;
				int leftCurly = 0, rightCurly = 0;
				int starting = 0, ending = 0;
				if (!listOfStrings.get(i).contains("{")) {
					j = i;
				} else {
					j = i + 1;
				}

				starting = j;
				for (; j < listOfStrings.size(); ++j) {
					if (listOfStrings.get(j).contains("{"))
						leftCurly++;
					if (listOfStrings.get(j).contains("}"))
						rightCurly++;

					if (leftCurly == 2)
						starting = j;

					// if the left and right are the same it means we have found
					// the end of the curly bracket
					if (leftCurly == rightCurly && leftCurly > 0) {
						ending = j;
						break;
					}
				}

				PrintWriter writer = new PrintWriter("mainCodeBlock.txt", "UTF-8");

				for (int k = starting; k < ending; ++k) {
					writer.println(listOfStrings.get(k).toString());
					newList.add(listOfStrings.get(k));
				}
				writer.close();
				break;
			}
		}

		// Write to a newFile the code blocks
		return newList;
	}

	// Function to get the remaining functions and add them in the
	// EnergyCheckUtils
	public static void getRemaining(ArrayList<String> listOfStrings, ArrayList<String> removeList) {
		// At this point we will retrieve all the functions
		// SOLUTION Remove the main class match it with the above
		for (int i = 0; i < removeList.size(); ++i) {
			for (int j = 0; j < listOfStrings.size(); ++j) {
				if (removeList.get(i).toString().equals(listOfStrings.get(j).toString())) {
					listOfStrings.remove(j);
				}

				if (listOfStrings.get(j).toString().contains("main")
						|| listOfStrings.get(j).toString().contains("class")
						|| listOfStrings.get(j).toString().contains("import")) {
					listOfStrings.remove(j);
				}
			}
		}


		// Now explicitly remove curly brackets not needed

		// More filtering is required, I know that
		for (int k = 0; k < listOfStrings.size(); ++k)
			System.out.println(listOfStrings.get(k).toString());
	}

}
