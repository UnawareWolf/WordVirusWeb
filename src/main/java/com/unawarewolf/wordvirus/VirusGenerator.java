package com.unawarewolf.wordvirus;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirusGenerator {

    public static final int DEFAULT_CHARACTER_HEIGHT = 12;

    private static final String DEFAULT_TXT = "templates/defaultText.txt";

    private static final double DEFAULT_INFECTION_LOW = 0.1;
    private static final double DEFAULT_INFECTION_HIGH = 0.6;
    private static final double DEFAULT_PROGRESSION_LOW = 0.4;
    private static final double DEFAULT_PROGRESSION_HIGH = 0.1;
    private static final double DEFAULT_RECOVERY_LOW = 0.2;
    private static final double DEFAULT_RECOVERY_HIGH = 0.4;

    private static final char DEFAULT_INITIALLY_INFECTED = 'e';
    public static final String BLANK_SQUARE_CODE = "B";

    private static final int DEFAULT_FONT_SIZE = 120;

    @NotNull
    private double infectionLow, infectionHigh, progressionLow, progressionHigh,
            recoveryLow, recoveryHigh;

    private char initiallyInfected;

    @NotNull
    @Min(12)
    private int fontSize;

    private String input;
    private String[] output;

    private List<VirusCharacter> virusCharacters;

    public VirusGenerator() {
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
    }

    public void generateOutputText() {
        generateVirusCharacters();
        formatOutputContentFont();
    }

    private void generateVirusCharacters() {
        virusCharacters = new ArrayList<>();
        Map<Character, VirusCharacter> characterMap = new HashMap<>();
        Map<String, Double> parameterMap = new HashMap<>();

        parameterMap.put("infectionRateLow", infectionLow);
        parameterMap.put("infectionRateHigh", infectionHigh);
        parameterMap.put("progressionRateLow", progressionLow);
        parameterMap.put("progressionRateHigh", progressionHigh);
        parameterMap.put("recoveryRateLow", recoveryLow);
        parameterMap.put("recoveryRateHigh", recoveryHigh);

        for (char character : input.toCharArray()) {
            VirusCharacter virusCharacter = createVirusCharacter(character, characterMap, parameterMap);
            virusCharacter.update(virusCharacters);
            virusCharacters.add(virusCharacter);
        }
    }

    private VirusCharacter createVirusCharacter(char character, Map<Character, VirusCharacter> characterMap, Map<String, Double> parameterMap) {
        if (useCopyConstructor(character, characterMap)) {
            return new VirusCharacter(characterMap.get(character));
        }
        return new VirusCharacter(character, characterMap, parameterMap, initiallyInfected);
    }

    private boolean useCopyConstructor(char character, Map<Character, VirusCharacter> characterMap) {
        if (characterMap.containsKey(character)) {
            return true;
        }
        return false;
    }

    public void formatOutputContentFont() {
        output = new String[DEFAULT_CHARACTER_HEIGHT];

        InfectionLevelMap infectionLevelMap = new InfectionLevelMap("csv_letter_maps/stages-Table 1.csv");

        for (int h = 0; h < output.length; h ++) {
            String longLine = "";
            int spaceCount = 0;
//            boolean previousIsBlank = false;
            for (VirusCharacter virusCharacter : virusCharacters) {

                int length = virusCharacter.getGridSquares().length;

                for (int i = 0; i < length; i++) {

                    GridSquare currentSquare = virusCharacter.getGridSquares()[i][h];

                    String output = infectionLevelMap.get(currentSquare.getFontCode().charAt(0), currentSquare.getInfectionLevel());

                    longLine += output;
                }

                longLine += BLANK_SQUARE_CODE;
                if (virusCharacter.getCharacter() == ' ') {
                    longLine += " ";
                    spaceCount++;
                }
                else {
                    spaceCount = 0;
                }
                if (spaceCount == 4) {
                    spaceCount = 0;
                }

            }
            output[h] = longLine;
        }
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

    public String[] getOutput() {
        return output;
    }

    public char getInitiallyInfected() {
        return initiallyInfected;
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

    public void setOutput(String[] output) {
        this.output = output;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setInitiallyInfected(char initiallyInfected) {
        this.initiallyInfected = initiallyInfected;
    }

}
