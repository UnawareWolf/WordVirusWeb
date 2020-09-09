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
        if (useCopyConstructor(character, characterMap)) {
            return new VirusCharacter(characterMap.get(character));
        }
        return new VirusCharacter(character, characterMap, inputConfiguration);
    }

    private boolean useCopyConstructor(char character, Map<Character, VirusCharacter> characterMap) {
        if (characterMap.containsKey(character)) {
            return true;
        }
        return false;
    }

    public String[] formatOutputContentFont() {
        String[] output = new String[DEFAULT_CHARACTER_HEIGHT];

        InfectionLevelMap infectionLevelMap = new InfectionLevelMap("csv_letter_maps/stages-Table 1.csv");

        for (int h = 0; h < output.length; h ++) {
            String longLine = "";
            int spaceCount = 0;
//            boolean previousIsBlank = false;
            for (VirusCharacter virusCharacter : virusCharacters) {

                int length = virusCharacter.getGridSquares().length;

                for (int i = 0; i < length; i++) {

                    GridSquare currentSquare = virusCharacter.getGridSquares()[i][h];

                    String entry = infectionLevelMap.get(currentSquare.getFontCode().charAt(0), currentSquare.getInfectionLevel());

                    longLine += entry;
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
        return output;
    }

    public void setInputConfiguration(InputConfiguration inputConfiguration) {
        this.inputConfiguration = inputConfiguration;
    }

}
