package com.unawarewolf.wordvirus;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
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
    
    private String imagePath;

    @NotNull
    private int infectionLow, infectionHigh, progressionLow, progressionHigh,
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
        infectionLow = (int) (DEFAULT_INFECTION_LOW * 100);
        infectionHigh = (int) (DEFAULT_INFECTION_HIGH * 100);
        progressionLow = (int) (DEFAULT_PROGRESSION_LOW * 100);
        progressionHigh = (int) (DEFAULT_PROGRESSION_HIGH * 100);
        recoveryLow = (int) (DEFAULT_RECOVERY_LOW * 100);
        recoveryHigh = (int) (DEFAULT_RECOVERY_HIGH * 100);
        input = FileHelper.getFileAsString(DEFAULT_TXT);
        fontSize = DEFAULT_FONT_SIZE;
        randomiseInitiallyInfectedIfRandomSelected();
//        initiallyInfected = DEFAULT_INITIALLY_INFECTED;
        randomInitiallyInfected = DEFAULT_RANDOM_INITIALLY_INFECTED;
        output = new String[12];
        try {
            imagePath = FileHelper.getRandomImagePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public double getInfectionLowDecimal() {
        return infectionLow / 100d;
    }

    public double getInfectionHighDecimal() {
        return infectionHigh / 100d;
    }

    public double getProgressionLowDecimal() {
        return progressionLow / 100d;
    }

    public double getProgressionHighDecimal() {
        return progressionHigh / 100d;
    }

    public double getRecoveryLowDecimal() {
        return recoveryLow / 100d;
    }

    public double getRecoveryHighDecimal() {
        return recoveryHigh / 100d;
    }

    public int getInfectionLow() {
        return infectionLow;
    }

    public int getInfectionHigh() {
        return infectionHigh;
    }

    public int getProgressionLow() {
        return progressionLow;
    }

    public int getProgressionHigh() {
        return progressionHigh;
    }

    public int getRecoveryLow() {
        return recoveryLow;
    }

    public int getRecoveryHigh() {
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
    
    public String getImagePath() {
        return imagePath;
    }

    public boolean isColourStyle() {
        return colourStyle;
    }

    public void setInfectionLow(int infectionLow) {
        this.infectionLow = infectionLow;
    }

    public void setInfectionHigh(int infectionHigh) {
        this.infectionHigh = infectionHigh;
    }

    public void setProgressionLow(int progressionLow) {
        this.progressionLow = progressionLow;
    }

    public void setProgressionHigh(int progressionHigh) {
        this.progressionHigh = progressionHigh;
    }

    public void setRecoveryLow(int recoveryLow ) {
        this.recoveryLow = recoveryLow;
    }

    public void setRecoveryHigh(int recoveryHigh) {
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
        if (initiallyInfected == 0) {

//        }


//        if (randomInitiallyInfected) {
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
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
