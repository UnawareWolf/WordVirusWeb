package com.unawarewolf.wordvirus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

public class InputConfiguration {

    private static final String DEFAULT_TXT = "templates/defaultText.txt";

    private static final double DEFAULT_INFECTION_LOW = 0.1;
    private static final double DEFAULT_INFECTION_HIGH = 0.6;
    private static final double DEFAULT_PROGRESSION_LOW = 0.4;
    private static final double DEFAULT_PROGRESSION_HIGH = 0.1;
    private static final double DEFAULT_RECOVERY_LOW = 0.2;
    private static final double DEFAULT_RECOVERY_HIGH = 0.4;

    private static final boolean DEFAULT_RANDOM_INITIALLY_INFECTED = true;
    private static final char DEFAULT_INITIALLY_INFECTED = 'e';

    private static final int DEFAULT_FONT_SIZE = 120;

    @NotNull
    private double infectionLow, infectionHigh, progressionLow, progressionHigh,
            recoveryLow, recoveryHigh;

    private boolean randomInitiallyInfected, colourStyle;
    private char initiallyInfected;

    private boolean showGenerated = false;

    @NotNull
    @Min(12)
    private int fontSize;

    private String input;
    private String[] output;

    public InputConfiguration() {
        setDefaultValues();
    }

    private void setDefaultValues() {
        infectionLow = DEFAULT_INFECTION_LOW;
        infectionHigh = DEFAULT_INFECTION_HIGH;
        progressionLow = DEFAULT_PROGRESSION_LOW;
        progressionHigh = DEFAULT_PROGRESSION_HIGH;
        recoveryLow = DEFAULT_RECOVERY_LOW;
        recoveryHigh = DEFAULT_RECOVERY_HIGH;
        input = FileHelper.getFileAsString(DEFAULT_TXT);
        fontSize = DEFAULT_FONT_SIZE;
        initiallyInfected = DEFAULT_INITIALLY_INFECTED;
        randomInitiallyInfected = DEFAULT_RANDOM_INITIALLY_INFECTED;
        output = new String[12];
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public double getInfectionLow() {
        return infectionLow;
    }

    public double getInfectionHigh() {
        return infectionHigh;
    }

    public double getProgressionLow() {
        return progressionLow;
    }

    public double getProgressionHigh() {
        return progressionHigh;
    }

    public double getRecoveryLow() {
        return recoveryLow;
    }

    public double getRecoveryHigh() {
        return recoveryHigh;
    }

    public int getFontSize() {
        return fontSize;
    }

    public char getInitiallyInfected() {
        return initiallyInfected;
    }

    public boolean getShowGenerated() {
        return showGenerated;
    }

    public String[] getOutput() {
        return output;
    }

    public boolean getRandomInitiallyInfected() {
        return randomInitiallyInfected;
    }

    public char getDefaultInitiallyInfected() {
        return DEFAULT_INITIALLY_INFECTED;
    }

    public boolean isColourStyle() {
        return colourStyle;
    }

    public void setInfectionLow(double infectionLow) {
        this.infectionLow = infectionLow;
    }

    public void setInfectionHigh(double infectionHigh) {
        this.infectionHigh = infectionHigh;
    }

    public void setProgressionLow(double progressionLow) {
        this.progressionLow = progressionLow;
    }

    public void setProgressionHigh(double progressionHigh) {
        this.progressionHigh = progressionHigh;
    }

    public void setRecoveryLow(double recoveryLow ) {
        this.recoveryLow = recoveryLow;
    }

    public void setRecoveryHigh(double recoveryHigh) {
        this.recoveryHigh = recoveryHigh;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setInitiallyInfected(char initiallyInfected) {
        this.initiallyInfected = initiallyInfected;
    }

    public void setOutput(String[] output) {
        this.output = output;
    }

    public void setRandomInitiallyInfected(boolean randomInitiallyInfected) {
        this.randomInitiallyInfected = randomInitiallyInfected;
    }

    public void setColourStyle(boolean colourStyle) {
        this.colourStyle = colourStyle;
    }

    public void randomiseInitiallyInfectedIfRandomSelected() {
        if (randomInitiallyInfected) {
            Set<Character> inputCharacters = new HashSet<>();
            for (char inputChar : input.toCharArray()) {
                inputCharacters.add(inputChar);
            }
            int randomIndex = new Random().nextInt(inputCharacters.size());
            int characterCount = 0;
            for (char inputChar : inputCharacters) {
                if (characterCount == randomIndex) {
                    initiallyInfected = inputChar;
                    return;
                }
                characterCount++;
            }
        }
    }

    public void setShowGenerated(boolean showGenerated) {
        this.showGenerated = showGenerated;
    }
}
