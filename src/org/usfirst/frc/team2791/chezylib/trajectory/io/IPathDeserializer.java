package org.usfirst.frc.team2791.chezylib.trajectory.io;

import org.usfirst.frc.team2791.chezylib.trajectory.Path;

/**
 * Interface for methods that deserializes a Path or Trajectory.
 * 
 * @author Jared341
 */
public interface IPathDeserializer {
  
  public Path deserialize(String serialized);
}
