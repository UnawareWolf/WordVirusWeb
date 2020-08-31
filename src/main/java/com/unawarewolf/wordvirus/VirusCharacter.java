package com.unawarewolf.wordvirus;

import java.util.Map;
import java.util.Random;

public class VirusCharacter {

    public static final int MAX_INFECTION_LEVEL = 5;

    private static final double LOW_LEVEL_INFECTION_RATE = 0.1;
    private static final double HIGH_LEVEL_INFECTION_RATE = 0.8;
    private static final double LOW_LEVEL_PROGRESSION_RATE = 0.4;
    private static final double HIGH_LEVEL_PROGRESSION_RATE = 0.06;
    private static final double LOW_LEVEL_RECOVERY_RATE = 0.14;
    private static final double HIGH_LEVEL_RECOVERY_RATE = 0.4;

    private static final char INITIAL_INFECTED = 'e';

    private char character;
    private int infectionLevel;
    private boolean hasAntibodies, immune, dead;
    private boolean firstInfected;
    private boolean inRecovery, hasRelapsed, inSecondRecovery;
    private Map<Character, VirusCharacter> characterMap; // Same map for all virus characters.

    private Random rand;

    public VirusCharacter(char character, Map<Character, VirusCharacter> characterMap) {
        this.character = character;
        this.characterMap = characterMap;
        if (character == INITIAL_INFECTED) {
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
        rand = new Random();
    }

    public VirusCharacter(VirusCharacter virusCharacter) {
        character = virusCharacter.getCharacter();
        infectionLevel = virusCharacter.getInfectionLevel();
        immune = virusCharacter.isImmune();
        hasAntibodies = virusCharacter.getHasAntibodies();
        this.characterMap = virusCharacter.getCharacterMap();
        inRecovery = virusCharacter.getInRecovery();
        hasRelapsed = virusCharacter.getHasRelapsed();
        inSecondRecovery = virusCharacter.getInSecondRecovery();
        rand = new Random();
        firstInfected = false;
    }

    public void update(VirusCharacter previousCharacter) {
        updateInfectionLevel(previousCharacter);

        giveAntibodiesIfInfected();

        giveDeathIfMaxInfectionLevel();

        giveImmunityIfRecovered();

        characterMap.put(character, this);
    }

    private void updateInfectionLevel(VirusCharacter previousCharacter) {
        if (infectionLevel < MAX_INFECTION_LEVEL && !immune && !firstInfected) {
            if (catchesInfection(previousCharacter) || infectionProgresses()) {
                infectionLevel++;
                if (inRecovery) {
                    hasRelapsed = true;
                }
            }
            else if (infectionRecovers()) {
                infectionLevel--;
                setRecoveryCondition();
            }
        }
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

    private boolean catchesInfection(VirusCharacter previousCharacter) {
        if (previousCharacterCanTransmit(previousCharacter) && infectionLevel == 0) {
            if (rand.nextDouble() < getTransmissionRate(previousCharacter)) {
                return true;
            }
        }
        return false;
    }

    private boolean previousCharacterCanTransmit(VirusCharacter previousCharacter) {
        return previousCharacter != null && previousCharacter.getIsInfected() && previousCharacterIsLetter(previousCharacter);
    }

    private boolean previousCharacterIsLetter(VirusCharacter previousCharacter) {
        return previousCharacter.getCharacter() != ' ';
//        && previousCharacter.getCharacter() != ',' && previousCharacter.getCharacter() != '.' && previousCharacter.getCharacter() != '!';
    }

    private boolean infectionProgresses() {
        return infectionLevel > 0 && rand.nextDouble() < calculateProgressionRate() && !inSecondRecovery;
    }

    private double calculateProgressionRate() {
        double mCurve = (LOW_LEVEL_PROGRESSION_RATE - HIGH_LEVEL_PROGRESSION_RATE) / Math.pow(MAX_INFECTION_LEVEL - 2, 2);
        double progressionRate = LOW_LEVEL_PROGRESSION_RATE - mCurve * Math.pow(infectionLevel - 1, 2);
        return progressionRate;
//        return MAX_PROGRESSION_RATE;
    }

    private boolean infectionRecovers() {
        return infectionLevel > 0 && rand.nextDouble() < calculateRecoveryRate();
    }

    private double calculateRecoveryRate() {
        double mCurve = (LOW_LEVEL_RECOVERY_RATE - HIGH_LEVEL_RECOVERY_RATE) / Math.pow(MAX_INFECTION_LEVEL - 2, 2);
        double recoveryRate = LOW_LEVEL_RECOVERY_RATE - mCurve * Math.pow(infectionLevel - 1, 2);
        return recoveryRate;
//        return RECOVERY_RATE;
    }

    private double getTransmissionRate(VirusCharacter previousCharacter) {
        double infectionRateDiff = HIGH_LEVEL_INFECTION_RATE - LOW_LEVEL_INFECTION_RATE;
        double infectionLevelDiff = MAX_INFECTION_LEVEL - 2d;
        double newInfectionRate = LOW_LEVEL_INFECTION_RATE + (Math.pow(previousCharacter.getInfectionLevel() - 1d, 2) * infectionRateDiff / Math.pow(infectionLevelDiff, 2));
        return  newInfectionRate;
    }

    private void setRecoveryCondition() {
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

    public Map<Character, VirusCharacter> getCharacterMap() {
        return characterMap;
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

    public int getTextStyle() {
        return immune ? MAX_INFECTION_LEVEL + 1 : infectionLevel;
    }
}
