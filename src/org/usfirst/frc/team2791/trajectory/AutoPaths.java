package org.usfirst.frc.team2791.trajectory;


import edu.wpi.first.wpilibj.Timer;

import java.io.IOException;
import java.util.Hashtable;

import org.usfirst.frc.team2791.robot.util.TextFileReader;
import org.usfirst.frc.team2791.trajectory.io.TextFileDeserializer;

/**
 * Load ALL autonomous mode paths and access them
 * Uses txt files that contain Trajectory Parameters that are generated from TrajectoryGenerator
 * Init this with loadPaths() to create all the Paths and use get() to access those Paths
 * All paths are initially designed for the Blue Side, going forward
 * 
 * @author Jared341
 * @author Stephen Pinkerton
 * @author team2791
 */
public class AutoPaths {
	// Make sure these match up!
	public static final int WALL_LANE_ID = 2;
	public static String fileLocation="/paths/";

	public final static String[] kPathNames = { "BLUELeftGear",
			"BLUELeftGearToLeftHopper",
			"BLUEBoilerToRightGear",
			"CenterGear",
			"TestingOneTwo",
			"BLUELeftGearToLeftWall",
			"BLUELeftWallToLoadingStation"
	};
	public final static String[] kPathDescriptions = { "Left Gear-Blue",
			"Left Gear to Left Hopper-Blue",
			"Blue Boiler to Right Gear",
			"Center Gear-Both",
			"Generic Testing Path",
			"BLUELeftGearToLeftWall",
			"BLUELeftWallToLoadingStation"
	};
	static Hashtable paths_ = new Hashtable();

	/*
	 * @param: String pathname_ is a path that you want to make sure exists
	 */
	public AutoPaths(String pathname_){
		boolean allPathsFound=false;
		for (int c=0;c<kPathNames.length;c++){
			if (kPathNames[c].equals(pathname_)){
				allPathsFound=true;
				break;
			}
		}
		if(allPathsFound)
			loadPaths();
		else
			System.out.println("One or more Paths are not in AutoPaths");
	}

	public AutoPaths(){
		loadPaths();
	}
	
	/**
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

			Path path;
			path = deserializer.deserialize(reader.readWholeFile());
			paths_.put(kPathNames[i], path);
		}
		System.out.println("Parsing paths took: " + t.get());


	}

	/**
	 * @param name is a String that is the path and file's name
	 * @return is the Path that is you want generated
	 */
	public static Path get(String name) {
		System.out.println("got a Path from AutoPaths");
		loadPaths();
		return (Path)paths_.get(name);
	}

	/**
	 * @param name is the index of the path and file's name
	 * @return is the Path that is you want generated
	 */

	public static Path getByIndex(int index) {
		loadPaths();
		return (Path)paths_.get(kPathNames[index]);
	}
}