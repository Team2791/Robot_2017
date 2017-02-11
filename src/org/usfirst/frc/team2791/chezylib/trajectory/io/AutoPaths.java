package org.usfirst.frc.team2791.chezylib.trajectory.io;


import edu.wpi.first.wpilibj.Timer;
import java.util.Hashtable;

import org.usfirst.frc.team2791.chezylib.trajectory.Path;
import org.usfirst.frc.team2791.robot.util.TextFileReader;

/**
 * Load ALL autonomous mode paths and access them
 * Uses txt files that contain Trajectory Parameters that are generated from TrajectoryGenerator
 * Init this with loadPaths() to create all the Paths and use get() to access those Pats
 * 
 * @author Jared341
 * @author Stephen Pinkerton
 * @author Unnas Hussain
 */
public class AutoPaths {
	// Make sure these match up!
	public static final int WALL_LANE_ID = 2;
	public static String fileLocation="/paths/";

	public final static String[] kPathNames = { "InsideLanePathFar",
			"CenterLanePathFar",
			"WallLanePath",
			"InsideLanePathClose", 
			"StraightAheadPath",
	};
	public final static String[] kPathDescriptions = { "Inside, Far", 
			"Middle Lane",
			"Wall Lane",
			"Inside, Close",
			"Straight ahead",
	};
	static Hashtable paths_ = new Hashtable();

	/*
	 * @param: String pathname_ is a path that you want to make sure exists
	 */
	public AutoPaths(String pathname_){
		for (int c=0;c<kPathNames.length;c++){
			if (kPathNames[c].equals(pathname_)){
				break;
			}
		}
		loadPaths();
	}

	public AutoPaths(){
		loadPaths();
	}
	/*
	 * loads all paths so that they can be accessed 
	 */
	public static void loadPaths() {
		Timer t = new Timer();
		t.start();
		TextFileDeserializer deserializer = new TextFileDeserializer();
		for (int i = 0; i < kPathNames.length; ++i) {
			String filepath = fileLocation + kPathNames[i] + ".txt";
			System.out.println(filepath);
			TextFileReader reader = new TextFileReader(filepath);

			Path path = deserializer.deserialize(reader.readWholeFile());
			paths_.put(kPathNames[i], path);
		}
		System.out.println("Parsing paths took: " + t.get());


	}

	/*
	 * @param name is a String that is the path and file's name
	 * @return is the Path that is you want generated
	 */
	public static Path get(String name) {
		System.out.println("got a Path from AutoPaths");
		loadPaths();
		return (Path)paths_.get(name);
	}

	/*
	 * @param name is the index of the path and file's name
	 * @return is the Path that is you want generated
	 */

	public static Path getByIndex(int index) {
		loadPaths();
		return (Path)paths_.get(kPathNames[index]);
	}
}