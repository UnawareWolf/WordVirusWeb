package com.unawarewolf.wordvirus;

public class Greeting {

    private double infectionLow, infectionHigh;
    private double progressionLow, progressionHigh;
    private double recoveryLow, recoveryHigh;
    private int fontSize;
    private String input;
    private String[] output;

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

}
