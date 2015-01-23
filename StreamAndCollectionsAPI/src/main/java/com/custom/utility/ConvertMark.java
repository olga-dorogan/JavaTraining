package com.custom.utility;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by olga on 22.01.15.
 */
public class ConvertMark {
    private static final Map<Character, Integer> ectsToMinPercent = new HashMap<Character, Integer>();

    static {
        ectsToMinPercent.put('A', 90);
        ectsToMinPercent.put('B', 80);
        ectsToMinPercent.put('C', 65);
        ectsToMinPercent.put('D', 55);
        ectsToMinPercent.put('E', 50);
    }

    public static int fromECTSToFourPoint(char ects) {
        switch (ects) {
            case 'A':
                return 5;
            case 'B':
            case 'C':
                return 4;
            case 'D':
            case 'E':
                return 3;
            default:
                return 2;
        }
    }

    public static char fromPercentToECTS(int markInPercent) {
        char result = 'F';
        if (markInPercent >= ectsToMinPercent.get('A')) {
            result = 'A';
        } else if (markInPercent >= ectsToMinPercent.get('B')) {
            result = 'B';
        } else if (markInPercent >= ectsToMinPercent.get('C')) {
            result = 'C';
        } else if (markInPercent >= ectsToMinPercent.get('D')) {
            result = 'D';
        } else if (markInPercent >= ectsToMinPercent.get('E')) {
            result = 'E';
        } else {
            result = 'F';
        }
        return result;
    }


    public static int fromPercentToFourPoint(int markInPercent) {
        return fromECTSToFourPoint(fromPercentToECTS(markInPercent));
    }

    public static int fromFourPointToRandPercent(int markInFourPoint) {
        int minPercent;
        int interval;
        switch (markInFourPoint) {
            case 5:
                minPercent = ectsToMinPercent.get('A');
                interval = 100 - minPercent;
                return minPercent + (int) (Math.random() * interval);
            case 4:
                minPercent = ectsToMinPercent.get('C');
                interval = ectsToMinPercent.get('A') - minPercent - 1;
                return minPercent + (int) (Math.random() * interval);
            case 3:
                minPercent = ectsToMinPercent.get('E');
                interval = ectsToMinPercent.get('C') - minPercent - 1;
                return minPercent + (int) (Math.random() * interval);
            case 2:
                interval = ectsToMinPercent.get('E') - 1;
                return (int) (Math.random() * interval);
        }
        return 0;
    }
}
