package com.unawarewolf.wordvirus;

import java.util.Random;

public class GridSquare {

    private char character;
    private String fontCode;
    private int infectionLevel;
    private boolean hasAntibodies, immune, dead;
    private boolean firstInfected;
    private boolean inRecovery, relapsed, inSecondRecovery;
    private boolean blankSquare;
    private Coordinate coordinates;
    private int width;

    private Random rand;

    private InputConfiguration inputConfiguration;

    public GridSquare(VirusCharacter virusCharacter, Coordinate coordinates, String fontCode) {
        inputConfiguration = virusCharacter.getInputConfiguration();
        character = virusCharacter.getCharacter();
        this.fontCode = fontCode;
        this.coordinates = coordinates;
        blankSquare = (fontCode.equals(VirusGenerator.BLANK_SQUARE_CODE));
        width = virusCharacter.getWidth();
        if (virusCharacter.getFirstInfected() && coordinates.equals(virusCharacter.getInitialSquareInfected())) {
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
        relapsed = false;
        inSecondRecovery = false;
        immune = false;
        rand = new Random();
    }

    public GridSquare(GridSquare gridSquare) {
        inputConfiguration = gridSquare.getInputConfiguration();
        character = gridSquare.getCharacter();
        fontCode = gridSquare.getFontCode();
        infectionLevel = gridSquare.getInfectionLevel();
        immune = gridSquare.isImmune();
        hasAntibodies = gridSquare.hasAntibodies();
        inRecovery = gridSquare.isInRecovery();
        relapsed = gridSquare.isInRelapse();
        inSecondRecovery = gridSquare.isInSecondRecovery();
        blankSquare = gridSquare.isBlankSquare();
        width = gridSquare.getWidth();
        rand = new Random();
        firstInfected = false;
        coordinates = gridSquare.getCoordinates();
    }

    public void update() {

        giveAntibodiesIfInfected();

        giveDeathIfMaxInfectionLevel();

        giveImmunityIfRecovered();
    }

    private void giveImmunityIfRecovered() {
        if (!immune && infectionLevel == 0 && hasAntibodies) {
            immune = true;
        }
    }

    private void giveDeathIfMaxInfectionLevel() {
        if (!dead && infectionLevel == VirusGenerator.MAX_INFECTION_LEVEL) {
            dead = true;
        }
    }

    private void giveAntibodiesIfInfected() {
        if (!hasAntibodies && infectionLevel > 0) {
            hasAntibodies = true;
        }
    }

    public boolean infectionProgresses() {
        return infectionLevel > 0 && rand.nextDouble() < calculateProgressionRate() && !inSecondRecovery;
    }

    private double calculateProgressionRate() {
        double mCurve = (inputConfiguration.getProgressionLow() - inputConfiguration.getProgressionHigh()) / Math.pow(VirusGenerator.MAX_INFECTION_LEVEL - 2, 2);
        return inputConfiguration.getProgressionLow() - mCurve * Math.pow(infectionLevel - 1, 2);
    }

    public boolean infectionRecovers() {
        return infectionLevel > 0 && rand.nextDouble() < calculateRecoveryRate();
    }

    private double calculateRecoveryRate() {
        double mCurve = (inputConfiguration.getRecoveryLow() - inputConfiguration.getRecoveryHigh()) / Math.pow(VirusGenerator.MAX_INFECTION_LEVEL - 2, 2);
        return inputConfiguration.getRecoveryLow() - mCurve * Math.pow(infectionLevel - 1, 2);
    }

    public double getTransmissionRate() {
        double infectionRateDiff = inputConfiguration.getInfectionHigh() - inputConfiguration.getInfectionLow();
        double infectionLevelDiff = VirusGenerator.MAX_INFECTION_LEVEL - 2d;
        return inputConfiguration.getInfectionLow() + (Math.pow(infectionLevel - 1d, 2) * infectionRateDiff / Math.pow(infectionLevelDiff, 2));
    }

    public void setRecoveryCondition() {
        if (!inRecovery) {
            inRecovery = true;
        }
        else if (relapsed) {
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

    public boolean hasAntibodies() {
        return hasAntibodies;
    }

    public boolean isInfected() {
        return infectionLevel > 0 && infectionLevel < VirusGenerator.MAX_INFECTION_LEVEL;
    }

    public boolean isInRecovery() {
        return inRecovery;
    }

    public boolean isInRelapse() {
        return relapsed;
    }

    public boolean isInSecondRecovery() {
        return inSecondRecovery;
    }

    public int getXCoordinate() {
        return coordinates.getX();
    }

    public int getYCoordinate() {
        return coordinates.getY();
    }

    public Coordinate getCoordinates() {
        return coordinates;
    }

    public void increaseInfectionLevel() {
        infectionLevel++;
    }

    public void decreaseInfectionLevel() {
        infectionLevel--;
    }

    public void setRelapsed(boolean relapsed) {
        this.relapsed = relapsed;
    }

    public boolean isBlankSquare() {
        return blankSquare;
    }

    public int getWidth() {
        return width;
    }

    public boolean catchesInfection(GridSquare infectedSquare, double absDist) {
        double transmissionRate = infectedSquare.getTransmissionRate();
        return rand.nextDouble() < transmissionRate / absDist;
    }

    public String getFontCode() {
        return fontCode;
    }

    public InputConfiguration getInputConfiguration() {
        return inputConfiguration;
    }

    public String getOutput(InfectionLevelMap infectionLevelMap, char previousChar) {
        String output = "";

        if (character != '\r' && character != '\n') {
            output += infectionLevelMap.get(fontCode.charAt(0), infectionLevel);
        }
        return wrapOutputInColourTag(output, previousChar);
//        return inputConfiguration.isColourStyle() ? wrapOutputInColourTag(output) : output;
    }

    private String wrapOutputInColourTag(String output, char previousChar) {
        String tagClass = "";

        if (infectionLevel == 1) {
            tagClass = immune ? "blueSeven" : "blueOne";
        }
        else if (infectionLevel == 2) {
            tagClass = inRecovery ? "brownSix" : "redTwo";
        }
        else if (infectionLevel == 3) {
            tagClass = inRecovery ? "brownFive" : "redThree";
        }
        else if (infectionLevel == 4) {
            tagClass = "pinkFour";
        }

        if (previousChar == ' ' && coordinates.getX() == 0) {
            output = "B" + output;
        }

        if (tagClass.length() > 0) {
            output = "<span class=\"" + tagClass + "\">" + output + "</span>";
        }

        return output;
    }

}
