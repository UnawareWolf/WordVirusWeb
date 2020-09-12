package com.unawarewolf.wordvirus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirusGenerator {

    public static final int DEFAULT_CHARACTER_HEIGHT = 12;
    public static final int MAX_INFECTION_LEVEL = 4;

    public static final String BLANK_SQUARE_CODE = "B";

    private InputConfiguration inputConfiguration;
    private List<VirusCharacter> virusCharacters;
    private Map<Character, VirusCharacter> characterMap;

    public VirusGenerator(InputConfiguration inputConfiguration) {
        this.inputConfiguration = inputConfiguration;
    }

    public String[] generateOutputText() {
        generateVirusCharacters();
        return formatOutputContentFont();
    }

    private void generateVirusCharacters() {
        virusCharacters = new ArrayList<>();
        characterMap = new HashMap<>();

        for (char character : inputConfiguration.getInput().toCharArray()) {
            VirusCharacter virusCharacter = createVirusCharacter(character);
            virusCharacter.update(virusCharacters);
            virusCharacters.add(virusCharacter);
        }
    }

    private VirusCharacter createVirusCharacter(char character) {
        return characterMap.containsKey(character) ? new VirusCharacter(characterMap.get(character)) :
                new VirusCharacter(this, character);
    }

    public String[] formatOutputContentFont() {
        String[] output = new String[DEFAULT_CHARACTER_HEIGHT];

        InfectionLevelMap infectionLevelMap = new InfectionLevelMap("csv_letter_maps/stages-Table 1.csv");

        for (int j = 0; j < output.length; j ++) {
            String currentLayerOutput = "";
            for (VirusCharacter virusCharacter : virusCharacters) {

                for (int i = 0; i < virusCharacter.getGridSquares().length; i++) {

                    GridSquare currentSquare = virusCharacter.getGridSquares()[i][j];

                    currentLayerOutput += infectionLevelMap.get(currentSquare.getFontCode().charAt(0), currentSquare.getInfectionLevel());
                }

                currentLayerOutput += BLANK_SQUARE_CODE;
                if (virusCharacter.getCharacter() == ' ') {
                    currentLayerOutput += " ";
                }

            }
            output[j] = currentLayerOutput;
        }
        return output;
    }

    public void setInputConfiguration(InputConfiguration inputConfiguration) {
        this.inputConfiguration = inputConfiguration;
    }

    public InputConfiguration getInputConfiguration() {
        return inputConfiguration;
    }

    public Map<Character, VirusCharacter> getCharacterMap() {
        return characterMap;
    }

}
