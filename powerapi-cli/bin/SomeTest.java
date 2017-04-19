import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.omg.CORBA.portable.OutputStream;

public class SomeTest {

	private static void printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(ins));
	    while ((line = in.readLine()) != null) {
	        System.out.println(name + " " + line);
	    }
	  }

	  private static void runProcess(String command) throws Exception {
	    Process pro = Runtime.getRuntime().exec(command);
	    printLines(command + " stdout:", pro.getInputStream());
	    printLines(command + " stderr:", pro.getErrorStream());
	    pro.waitFor();
	    System.out.println(command + " exitValue() " + pro.exitValue());
	  }

	  public static void main(String[] args) {
	    try {
	      //runProcess("javac AnotherTest.java");
	      runProcess("java -jar SPECjvm2008.jar compiler");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

}
