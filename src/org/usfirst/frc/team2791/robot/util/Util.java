package org.usfirst.frc.team2791.util;

public class Util {
    //A class with a bunch of useful tools(methods)
    public static String newline = System.lineSeparator();

    private Util() {
    }

    public static double limit(double val, double limit) {
        return Math.abs(val) < limit ? val : limit * (val < 0 ? -1 : 1);
    }

    public static double deadzone(double min, double val, double max) {
        double absVal = Math.abs(val);
        double absMin = Math.abs(min);
        double absMax = Math.abs(max);

        if (absMin <= absVal && absVal <= absMax) {
            return val;
        } else if (absVal <= absMin) {
            return 0.0;
        } else {
            return val < 0 ? -absMax : absMax;
        }
    }

    public static String repeatString(String s, int repetitions) {
        return new String(new char[repetitions]).replace("\0", s);
    }

    public static String truncateLastTerm(String message, String term) {
        int lastIndex = getLastIndex(message, term);

        if (lastIndex == -1) {
            return message;
        }

        return message.substring(0, lastIndex);
    }

    public static int getLastIndex(String message, String term) {
        int index = message.indexOf(term);
        while (index >= 0) {
            index = message.indexOf(term, index + 1);
        }

        return index;
    }

    public static double tickToFeet(double encoderTicks, double wheelDiameter_inFeet) {
        return (wheelDiameter_inFeet * Math.PI / encoderTicks);
    }

    public enum UnitLength {
        FEET(1.0 / 12.0, "ft"), INCHES(1.0, "in");
        private double unitsPerInch;
        private String abbreviation;

        UnitLength(double unitsPerInch, String abbreviation) {
            this.unitsPerInch = unitsPerInch;
            this.abbreviation = abbreviation;
        }

        public double getUnitsPerInch() {
            return unitsPerInch;
        }

        @Override
        public String toString() {
            return abbreviation;
        }
    }
}