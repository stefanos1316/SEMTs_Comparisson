import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			getImports(listOfStrings);
			getRemaining(listOfStrings, removeList);
			
			addInFileMain("mainCodeBlock.txt", args[1]);
			addInFileFunctions("functionsCodeBlock.txt", args[1]);
			addInFileImports("importsCodeBlock.txt", args[1]);
		}

	}

	// Function to get the main code block
	public static ArrayList<String> getMainBlock(ArrayList<String> listOfStrings)
			throws FileNotFoundException, UnsupportedEncodingException {
		// At this part of the program we will check the list to identify the
		// main
		ArrayList<String> newList = new ArrayList();
		for (int i = 0; i < listOfStrings.size(); ++i) {
			if (listOfStrings.get(i).contains("public static void main")) {
				int j = 0;
				int leftCurly = 0, rightCurly = 0;
				int starting = 0, ending = 0;
				if (listOfStrings.get(i).contains("{")) {
					j = i + 1;
					leftCurly++;
				} else {
					for (int k = i + 1; k < listOfStrings.size(); ++k) {
						if (listOfStrings.get(k).contains("{")) {
							j = k + 1;
							leftCurly++;
							break;
						}
					}
				}

				starting = j;
				for (; j < listOfStrings.size(); ++j) {
					if (listOfStrings.get(j).contains("{"))
						leftCurly++;
					if (listOfStrings.get(j).contains("}"))
						rightCurly++;

					// if (leftCurly == 2)
					// starting = j;

					// if the left and right are the same it means we have found
					// the end of the curly bracket
					if (leftCurly == rightCurly && leftCurly > 0) {
						ending = j;
						break;
					}
				}

				PrintWriter writer = new PrintWriter("mainCodeBlock.txt", "UTF-8");

				for (int k = starting; k < ending; ++k) {
					// System.out.println(listOfStrings.get(k).toString());
					writer.println(listOfStrings.get(k).toString());
					newList.add(listOfStrings.get(k));
				}
				writer.close();
				break;
			}
		}
		return newList;
	}

	// Function to get the remaining functions and add them in the
	// EnergyCheckUtils
	public static void getRemaining(ArrayList<String> listOfStrings, ArrayList<String> removeList)
			throws FileNotFoundException, UnsupportedEncodingException {
		// At this point we will retrieve all the functions
		// SOLUTION Remove the main class match it with the above
		int starting = 0, ending = 0;
		for (int j = 0; j < listOfStrings.size(); ++j) {
			if (listOfStrings.get(j).contains("public static void main")) {
				starting = j;
				int leftCurlyBrackets = 0, rightCurlyBrackets = 0;
				for (int i = j; i < listOfStrings.size(); ++i) {

					if (listOfStrings.get(i).contains("{"))
						leftCurlyBrackets++;
					if (listOfStrings.get(i).contains("}"))
						rightCurlyBrackets++;

					if (rightCurlyBrackets == leftCurlyBrackets && leftCurlyBrackets > 1) {
						ending = i;
						break;
					}
				}
			}
		}

		// Remove all the main class
		listOfStrings.subList(starting, ending + 1).clear();

		// Remove all imports
		int startImports = 0, endImports = 0;
		boolean findFirst = true;
		for (int t = 0; t < listOfStrings.size(); ++t) {
			if (findFirst) {
				if (listOfStrings.get(t).contains("import")) {
					startImports = t;
					findFirst = false;
				}
			}

			if (listOfStrings.get(t).contains("class")) {
				endImports = t - 1;
				break;
			}
		}

		listOfStrings.subList(startImports, endImports).clear();

		// Remove class and it's curly brackets
		boolean first = false;
		int posLast = 0, startingPoint = 0;

		// Remove class lines and the first bracket
		for (int i = 0; i < listOfStrings.size(); ++i) {
			if (listOfStrings.get(i).contains("class") && listOfStrings.get(i).contains("{")) {
				listOfStrings.remove(i);
				break;
			}

			if (listOfStrings.get(i).contains("class") && !listOfStrings.get(i).contains("{")) {
				listOfStrings.remove(i);
				for (int k = i + 1; k < listOfStrings.size(); ++k) {
					if (listOfStrings.get(k).contains("{")) {
						listOfStrings.remove(k);
						break;
					}
				}
				break;
			}
		}

		// Remove
		for (int j = listOfStrings.size(); j < 0; --j) {
			if (listOfStrings.get(j).contains("{")) {
				listOfStrings.remove(j);
				break;
			}
		}

		PrintWriter writers = new PrintWriter("functionsCodeBlock.txt", "UTF-8");
		for (int k = startingPoint; k < listOfStrings.size(); ++k)
			writers.println(listOfStrings.get(k).toString());
		writers.close();
	}

	// Function to append in EnergyCheckUtils (Main)
	public static void addInFileMain(String fileName, String EnergyCheckUtilspath)
			throws FileNotFoundException, IOException {

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;

			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				sb.append(line);
				sb.append("\n");
			}
		}

		// Now we will append the string in the new line
		Path path = Paths.get(EnergyCheckUtilspath);
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);

		int position = 0;
		// Find where the double[] before is

		for (int i = 0; i < lines.size(); ++i) {
			if (lines.get(i).contains("double[] before")) {
				position = i + 2;
				break;
			}
			// System.out.println(lines.get(i).toString());
		}

		lines.add(position, sb.toString());
		Files.write(path, lines, StandardCharsets.UTF_8);
	}

	// Function to append in EnergyCheckUtils (Functions)
	public static void addInFileFunctions(String fileName, String EnergyCheckUtilspath)
			throws FileNotFoundException, IOException {

		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;

			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				if (!line.contains("import")) {
					sb.append(line);
					sb.append("\n");
				}
			}
		}

		// Now we will append the string in the new line
		Path path = Paths.get(EnergyCheckUtilspath);
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);

		int position = 0;
		// Find where the double[] before is

		for (int i = 0; i < lines.size(); ++i) {
			if (lines.get(i).contains("ADD HERE ALL FUNCTIONS")) {
				position = i + 1;
				break;
			}
			// System.out.println(lines.get(i).toString());
		}

		lines.add(position, sb.toString());
		Files.write(path, lines, StandardCharsets.UTF_8);
	}

	// Function to get all imports
	public static void getImports(ArrayList<String> listOfStrings)
			throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<String> newList = new ArrayList();

		// Add the the import lines in a new array
		for (int i = 0; i < listOfStrings.size(); ++i) {
			if (listOfStrings.get(i).contains("import")) {
				newList.add(listOfStrings.get(i).toString());
			}
		}

		PrintWriter writer = new PrintWriter("importsCodeBlock.txt", "UTF-8");
		for (int k = 0; k < newList.size(); ++k)
			writer.println(newList.get(k).toString());
		writer.close();
	}

	// Function to add imports in EnergyCheckUtils.java
	public static void addInFileImports(String fileName, String EnergyCheckUtilspath) throws IOException {
		Path path = Paths.get(EnergyCheckUtilspath);
		ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);

		StringBuilder sb = new StringBuilder();
		int position = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;

			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				sb.append(line);
				sb.append("\n");
			}
		}

		for (int i = 0; i < lines.size(); ++i) {
			if (lines.get(i).contains("//START ADDING HERE ALL IMPORRTS")) {
				position = i + 1;
			}
			// System.out.println(lines.get(i).toString());
		}

		lines.add(position, sb.toString());
		Files.write(path, lines, StandardCharsets.UTF_8);

	}
}
