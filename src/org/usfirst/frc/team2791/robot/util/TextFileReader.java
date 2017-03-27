package org.usfirst.frc.team2791.robot.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


//import com.sun.squawk.microedition.io.FileConnection;
//import javax.microedition.io.Connector;

/**
 * Read a text file into a string.
 * @param String uri is the name of the file and the path (should be the same)
 *
 * @author Jared341
 */
public class TextFileReader{

	private InputStream file_connection_ = null;
	private BufferedReader reader_ = null;

	public TextFileReader(String uri) {
		try {
//			String current = new java.io.File( "." ).getCanonicalPath();
//	        System.out.println("Current dir:"+current);
//	        String currentDir = System.getProperty("user.dir");
//	        System.out.println("Current dir using System:" +currentDir);
//	        System.out.println("Working Directory = " +
//	                System.getProperty("user.dir"));
//			
////			File f = new File(".");
//			ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
//			for (String name: names){
//				System.out.println(name);
//			}
			
	        System.out.println("Trying to open file " + uri);
			// Open the new file
			file_connection_ = new FileInputStream(uri);

			// Make an I/O adapter sandwich to actually get some text out
			reader_ = new BufferedReader(new InputStreamReader(file_connection_, Charset.forName("UTF-8")));

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not open file connection!");
			closeFile();
		}
	}

	private void closeFile() {
		try {
			// If we have a file open, close it
			if (file_connection_ != null) {
				if (reader_ != null) {
					reader_.close();
					file_connection_.close();
				}
			}
		} catch (IOException e) {
			System.err.println("Could not close file");
		}
	}

	// Returns null at end of file
	public String readLine() {
		String line = null;
		try {
			line = reader_.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			closeFile();
		}
		return line;
	}

	public String readWholeFile() {
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = readLine()) != null) {
			buffer.append(line);
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
