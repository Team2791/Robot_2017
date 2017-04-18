package org.usfirst.frc.team2791.robot.util;

public class AnalyzedContour {
    
    public double area, centerY, centerX, height, width, solidity;

    public AnalyzedContour(double area, double centerY, double centerX, double height, double width, double solidity) {
        this.area = area;
        // TODO make this not a hack. lol like we'll ever get around to that.
        this.centerY = VisionNetworkTable.SIZEY - centerY;
        this.centerX = centerX;
        this.height = height;
        this.width = width;
        this.solidity = solidity;
    }

}
