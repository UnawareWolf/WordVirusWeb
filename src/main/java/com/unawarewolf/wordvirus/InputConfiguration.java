package com.unawarewolf.wordvirus;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class InputConfiguration {

    private static final String DEFAULT_TXT = "templates/defaultText.txt";

    private static final BigDecimal DEFAULT_INFECTION_LOW = new BigDecimal(0.1);
    private static final BigDecimal DEFAULT_INFECTION_HIGH = new BigDecimal(0.6);
    private static final BigDecimal DEFAULT_PROGRESSION_LOW = new BigDecimal(0.4);
    private static final BigDecimal DEFAULT_PROGRESSION_HIGH = new BigDecimal(0.1);
    private static final BigDecimal DEFAULT_RECOVERY_LOW = new BigDecimal(0.2);
    private static final BigDecimal DEFAULT_RECOVERY_HIGH = new BigDecimal(0.4);

    private static final int DEFAULT_FONT_SIZE = 60;

    @NotNull
    private BigDecimal infectionLow, infectionHigh;

    @NotNull
    private BigDecimal progressionLow, progressionHigh;

    @NotNull
    private BigDecimal recoveryLow, recoveryHigh;

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
        input = GridMain.getFileAsString(DEFAULT_TXT);
        fontSize = DEFAULT_FONT_SIZE;
    }

    public void generateOutputText() {
        char[] inputCharArray = input.toCharArray();

        List<GridVirusCharacter> virusCharacters = GridMain.createVirusCharacterList(inputCharArray, this);

        output = GridMain.formatOutputContentFont(virusCharacters);
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public double getInfectionLow() {
        return infectionLow.doubleValue();
    }

    public double getInfectionHigh() {
        return infectionHigh.doubleValue();
    }

    public double getProgressionLow() {
        return progressionLow.doubleValue();
    }

    public double getProgressionHigh() {
        return progressionHigh.doubleValue();
    }

    public double getRecoveryLow() {
        return recoveryLow.doubleValue();
    }

    public double getRecoveryHigh() {
        return recoveryHigh.doubleValue();
    }

    public int getFontSize() {
        return fontSize;
    }

    public String[] getOutput() {
        return output;
    }

    public void setInfectionLow(double infectionLow) {
        this.infectionLow = new BigDecimal(infectionLow);
    }

    public void setInfectionHigh(double infectionHigh) {
        this.infectionHigh = new BigDecimal(infectionHigh);
    }

    public void setProgressionLow(double progressionLow) {
        this.progressionLow = new BigDecimal(progressionLow);
    }

    public void setProgressionHigh(double progressionHigh) {
        this.progressionHigh = new BigDecimal(progressionHigh);
    }

    public void setRecoveryLow(double recoveryLow ) {
        this.recoveryLow = new BigDecimal(recoveryLow);
    }

    public void setRecoveryHigh(double recoveryHigh) {
        this.recoveryHigh = new BigDecimal(recoveryHigh);
    }

    public void setOutput(String[] output) {
        this.output = output;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}
