package org.usfirst.frc.team2791.trajectory.io;

import org.usfirst.frc.team2791.trajectory.Path;

/**
 * Interface for methods that deserializes a Path or Trajectory.
 * 
 * @author Jared341
 * @author unbun
 */
public interface IPathDeserializer {
  
  public Path deserialize(String serialized);
}
