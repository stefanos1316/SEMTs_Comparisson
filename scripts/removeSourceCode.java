import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.xml.stream.events.StartDocument;

public class removeSourceCode {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> listOfStrings = new ArrayList();

		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String line;

			while ((line = br.readLine()) != null) {
				// process the line.
				// Find which point to create and add the source code between
				// the doulbe[] before and after getEnergyStats(); function
				listOfStrings.add(line);
			}
		}

		removeMain(listOfStrings);
		removeFunctions(listOfStrings);
		removeImports(listOfStrings);

		// Remove the oldFile and write all the results to a new one.
		File deleting = new File(args[0]);

		// Create the new file and write inside of it
		try {
			PrintWriter writer = new PrintWriter(args[0], "UTF-8");
			for (int i = 0; i < listOfStrings.size(); ++i) {
				writer.println(listOfStrings.get(i).toString());
			}
			writer.close();
		} catch (IOException e) {
			// do something
		}
	}

	// Function to remove the main
	public static void removeMain(ArrayList<String> list) {
		int statingPosition = 0, endingPosition = 0;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).contains("double[] before = getEnergyStats();"))
				statingPosition = i + 2;
			if (list.get(i).contains("double[] after = getEnergyStats();"))
				endingPosition = i - 2;
		}

		list.subList(statingPosition, endingPosition).clear();
//		for (int i = 0; i < list.size(); ++i) {
//			System.out.println(list.get(i).toString());
//		}

	}

	// Function to remove the functions
	public static void removeFunctions(ArrayList<String> list) {
		int statingPosition = 0, endingPosition = 0;
		for (int i = 0; i < list.size(); ++i) {
			if (list.get(i).contains("//ADD HERE ALL FUNCTIONS"))
				statingPosition = i + 1;
			if (list.get(i).contains("}"))
				endingPosition = i - 1;
		}

		list.subList(statingPosition, endingPosition).clear();
//		for (int i = 0; i < list.size(); ++i) {
//			System.out.println(list.get(i).toString());
//		}

	}

	// Function to remove the imports
		public static void removeImports(ArrayList<String> list) {
			int statingPosition = 0, endingPosition = 0;
			for (int i = 0; i < list.size(); ++i) {
				if (list.get(i).contains("//START ADDING HERE ALL IMPORRTS"))
					statingPosition = i + 1;
				if (list.get(i).contains("//STOP ADDING IMPORTS"))
					endingPosition = i - 1;
			}

			list.subList(statingPosition, endingPosition).clear();
//			for (int i = 0; i < list.size(); ++i) {
//				System.out.println(list.get(i).toString());
//			}

		}
}
