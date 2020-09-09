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
        double progressionRate = inputConfiguration.getProgressionLow() - mCurve * Math.pow(infectionLevel - 1, 2);
        return progressionRate;
    }

    public boolean infectionRecovers() {
        return infectionLevel > 0 && rand.nextDouble() < calculateRecoveryRate();
    }

    private double calculateRecoveryRate() {
        double mCurve = (inputConfiguration.getRecoveryLow() - inputConfiguration.getRecoveryHigh()) / Math.pow(VirusGenerator.MAX_INFECTION_LEVEL - 2, 2);
        double recoveryRate = inputConfiguration.getRecoveryLow() - mCurve * Math.pow(infectionLevel - 1, 2);
        return recoveryRate;
    }

    public double getTransmissionRate() {
        double infectionRateDiff = inputConfiguration.getProgressionHigh() - inputConfiguration.getProgressionLow();
        double infectionLevelDiff = VirusGenerator.MAX_INFECTION_LEVEL - 2d;
        double newInfectionRate = inputConfiguration.getProgressionLow() + (Math.pow(infectionLevel - 1d, 2) * infectionRateDiff / Math.pow(infectionLevelDiff, 2));
        return  newInfectionRate;
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
        return rand.nextDouble() < infectedSquare.getTransmissionRate() / absDist;
    }

    public String getFontCode() {
        return fontCode;
    }

    public boolean isDead() {
        return dead;
    }

    public InputConfiguration getInputConfiguration() {
        return inputConfiguration;
    }

}
