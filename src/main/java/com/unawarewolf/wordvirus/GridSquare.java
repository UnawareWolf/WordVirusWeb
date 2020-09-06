package com.unawarewolf.wordvirus;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class GridSquare {

    public static final int MAX_INFECTION_LEVEL = 4;

    private Map<String, Double> parameterMap;

    private char character;
    private String fontCode;
    private int infectionLevel;
    private boolean hasAntibodies, immune, dead;
    private boolean firstInfected;
    private boolean inRecovery, hasRelapsed, inSecondRecovery;
    private boolean blankSquare;
    private int[] coordinates;
    private int width;

    private Random rand;

    public GridSquare(GridSquare gridSquare) {
        parameterMap = gridSquare.getParameterMap();
        character = gridSquare.getCharacter();
        fontCode = gridSquare.getFontCode();
        infectionLevel = gridSquare.getInfectionLevel();
        immune = gridSquare.isImmune();
        hasAntibodies = gridSquare.getHasAntibodies();
//        this.gridMap = gridSquare.getGridMap();
        inRecovery = gridSquare.getInRecovery();
        hasRelapsed = gridSquare.getHasRelapsed();
        inSecondRecovery = gridSquare.getInSecondRecovery();
        blankSquare = gridSquare.isBlankSquare();
        width = gridSquare.getWidth();
//        infectionDuration = gridSquare.getInfectionDuration() + 1;
        rand = new Random();
        firstInfected = false;
        coordinates = gridSquare.getCoordinates();
    }


//    public GridSquare(GridVirusCharacter gridVirusCharacter, int[] coordinates, boolean blankSquare) {
//        this.character = gridVirusCharacter.getCharacter();
////        this.gridVirusCharacter = gridVirusCharacter;
//        this.coordinates = coordinates;
//        this.blankSquare = blankSquare;
//        this.width = gridVirusCharacter.getWidth();
//        if (gridVirusCharacter.getFirstInfected() && Arrays.equals(coordinates, gridVirusCharacter.getInitialSquareInfected())) {
//            infectionLevel = 1;
//            hasAntibodies = true;
//            firstInfected = true;
//        }
//        else {
//            infectionLevel = 0;
//            hasAntibodies = false;
//            firstInfected = false;
//        }
//        inRecovery = false;
//        hasRelapsed = false;
//        inSecondRecovery = false;
//        immune = false;
////        infectionDuration = 0;
//        rand = new Random();
//
//    }

    public GridSquare(VirusCharacter virusCharacter, int[] coordinates, String fontCode) {
        this.parameterMap = virusCharacter.getParameterMap();
        character = virusCharacter.getCharacter();
        this.fontCode = fontCode;
//        this.gridVirusCharacter = gridVirusCharacter;
        this.coordinates = coordinates;
        blankSquare = (fontCode == "B");
        width = virusCharacter.getWidth();
        if (virusCharacter.getFirstInfected() && Arrays.equals(coordinates, virusCharacter.getInitialSquareInfected())) {
            infectionLevel = 1;
            hasAntibodies = true;
            firstInfected = true;
        }
        else {
            infectionLevel = 0;
            hasAntibodies = false;
            firstInfected = false;
        }
        inRecovery = false;
        hasRelapsed = false;
        inSecondRecovery = false;
        immune = false;
//        infectionDuration = 0;
        rand = new Random();

    }

//    public void update(GridVirusCharacter previousCharacter) {
//        updateInfectionLevel(previousCharacter);
//
//        giveAntibodiesIfInfected();
//
//        giveDeathIfMaxInfectionLevel();
//
//        giveImmunityIfRecovered();
//
////        gridMap.put(coordinates, this);
//    }

    public void update() {

        giveAntibodiesIfInfected();

        giveDeathIfMaxInfectionLevel();

        giveImmunityIfRecovered();
    }

    private void updateInfectionLevel(VirusCharacter previousCharacter) {
//        if (infectionLevel < MAX_INFECTION_LEVEL && !immune && !firstInfected) {
//            if (catchesInfection(previousCharacter) || infectionProgresses()) {
//                infectionLevel++;
//                if (inRecovery) {
//                    hasRelapsed = true;
//                }
//            }
//            else if (infectionRecovers()) {
//                infectionLevel--;
//                setRecoveryCondition();
//            }
//        }
    }

    private void giveImmunityIfRecovered() {
        if (infectionLevel == 0 && hasAntibodies) {
            immune = true;
        }
    }

    private void giveDeathIfMaxInfectionLevel() {
        if (infectionLevel == MAX_INFECTION_LEVEL) {
            dead = true;
        }
    }

    private void giveAntibodiesIfInfected() {
        if (infectionLevel > 0) {
            hasAntibodies = true;
        }
    }

    private boolean previousCharacterIsLetter(VirusCharacter previousCharacter) {
        return previousCharacter.getCharacter() != ' ';
//        && previousCharacter.getCharacter() != ',' && previousCharacter.getCharacter() != '.' && previousCharacter.getCharacter() != '!';
    }

    public boolean infectionProgresses() {
        return infectionLevel > 0 && rand.nextDouble() < calculateProgressionRate() && !inSecondRecovery;
    }

    private double calculateProgressionRate() {
//        double mCurve = (LOW_LEVEL_PROGRESSION_RATE - HIGH_LEVEL_PROGRESSION_RATE) / Math.pow(MAX_INFECTION_LEVEL - 2, 2);
//        double progressionRate = LOW_LEVEL_PROGRESSION_RATE - mCurve * Math.pow(infectionLevel - 1, 2);
//        return progressionRate;
        double mCurve = (parameterMap.get("progressionRateLow") - parameterMap.get("progressionRateHigh")) / Math.pow(MAX_INFECTION_LEVEL - 2, 2);
        double progressionRate = parameterMap.get("progressionRateLow") - mCurve * Math.pow(infectionLevel - 1, 2);
        return progressionRate;
    }

    public boolean infectionRecovers() {
        return infectionLevel > 0 && rand.nextDouble() < calculateRecoveryRate();
    }

    private double calculateRecoveryRate() {
//        double mCurve = (LOW_LEVEL_RECOVERY_RATE - HIGH_LEVEL_RECOVERY_RATE) / Math.pow(MAX_INFECTION_LEVEL - 2, 2);
//        double recoveryRate = LOW_LEVEL_RECOVERY_RATE - mCurve * Math.pow(infectionLevel - 1, 2);
//        return recoveryRate;
        double mCurve = (parameterMap.get("recoveryRateLow") - parameterMap.get("recoveryRateHigh")) / Math.pow(MAX_INFECTION_LEVEL - 2, 2);
        double recoveryRate = parameterMap.get("recoveryRateLow") - mCurve * Math.pow(infectionLevel - 1, 2);
        return recoveryRate;
    }

    private double getTransmissionRate(GridSquare infectedSquare) {
//        double infectionRateDiff = HIGH_LEVEL_INFECTION_RATE - LOW_LEVEL_INFECTION_RATE;
//        double infectionLevelDiff = MAX_INFECTION_LEVEL - 2d;
//        double newInfectionRate = LOW_LEVEL_INFECTION_RATE + (Math.pow(infectedSquare.getInfectionLevel() - 1d, 2) * infectionRateDiff / Math.pow(infectionLevelDiff, 2));
//        return  newInfectionRate;
        double infectionRateDiff = parameterMap.get("infectionRateHigh") - parameterMap.get("infectionRateLow");
        double infectionLevelDiff = MAX_INFECTION_LEVEL - 2d;
        double newInfectionRate = parameterMap.get("infectionRateLow") + (Math.pow(infectedSquare.getInfectionLevel() - 1d, 2) * infectionRateDiff / Math.pow(infectionLevelDiff, 2));
        return  newInfectionRate;
    }

    public void setRecoveryCondition() {
        if (!inRecovery) {
            inRecovery = true;
        }
        else if (hasRelapsed) {
            inSecondRecovery = true;
        }
    }

    public int getInfectionLevel() {
        return infectionLevel;
    }

    public Character getCharacter() {
        return character;
    }

    public boolean isImmune() {
        return immune;
    }

    public boolean getHasAntibodies() {
        return hasAntibodies;
    }

    public boolean getIsInfected() {
        return infectionLevel > 0 && infectionLevel < 5;
    }

    public boolean getInRecovery() {
        return inRecovery;
    }

    public boolean getHasRelapsed() {
        return hasRelapsed;
    }

    public boolean getInSecondRecovery() {
        return inSecondRecovery;
    }

    public int getXCoordinate() {
        return coordinates[0];
    }

    public int getYCoordinate() {
        return coordinates[1];
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void increaseInfectionLevel() {
        infectionLevel++;
    }

    public void decreaseInfectionLevel() {
        infectionLevel--;
    }

    public void setHasRelapsed(boolean hasRelapsed) {
        this.hasRelapsed = hasRelapsed;
    }

    public boolean isBlankSquare() {
        return blankSquare;
    }

    public int getWidth() {
        return width;
    }

    public boolean catchesInfection(GridSquare infectedSquare, double absDist) {
        if (rand.nextDouble() < getTransmissionRate(infectedSquare) / absDist) {
                return true;
        }
        return false;
    }

    public String getFontCode() {
        return fontCode;
    }

    public boolean isDead() {
        return dead;
    }


    private Map<String, Double> getParameterMap() {
        return parameterMap;
    }

}
