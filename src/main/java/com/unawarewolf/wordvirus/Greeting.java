package com.unawarewolf.wordvirus;

public class Greeting {

//    private long id;
    private double infectionLow, infectionHigh;
    private double progressionLow, progressionHigh;
    private double recoveryLow, recoveryHigh;
    private String input;
    private String[] output;

//    public long getId() {
//        return id;
//    }

//    public void setId(long id) {
//        this.id = id;
//    }

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

//    public void setContent(String content) {
//        this.content = new String[1];
//        this.content[0] = content;
//    }

    public void setOutput(String[] output) {
        this.output = output;
    }

}
