package com.unawarewolf.wordvirus;

import java.io.File;
import java.util.*;

public class VirusGenerator {

    public static final int DEFAULT_CHARACTER_HEIGHT = 12;
    public static final int MAX_INFECTION_LEVEL = 4;

    public static final String BLANK_SQUARE_CODE = "B";

    private InputConfiguration inputConfiguration;
    private List<VirusCharacter> virusCharacters;
    private Map<String, VirusCharacter> characterMap;

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

        boolean quoteStart;
        char previousChar = ' ';
        for (char character : inputConfiguration.getInput().toCharArray()) {
            quoteStart = (previousChar == ' ');
            VirusCharacter virusCharacter = createVirusCharacter(character, quoteStart);
            virusCharacter.update(virusCharacters);
            characterMap.put(getCharacterKey(character, quoteStart), virusCharacter);
            virusCharacters.add(virusCharacter);
            previousChar = character;
        }
    }

    private String getCharacterKey(char character, boolean quoteStart) {
        String charKey = Character.toString(character);
        if (!charKey.equals("\"") && !charKey.equals("'")) {
            return charKey;
        }
        return quoteStart ? charKey + "l" : charKey + "r";
    }

    private VirusCharacter createVirusCharacter(char character, boolean quoteStart) {
        return characterMap.containsKey(getCharacterKey(character, quoteStart)) ? new VirusCharacter(characterMap.get(getCharacterKey(character, quoteStart)), quoteStart) :
                new VirusCharacter(this, character, quoteStart);
    }

    public String[] formatOutputContentFont() {
        String[] output = new String[DEFAULT_CHARACTER_HEIGHT];

        InfectionLevelMap infectionLevelMap = new InfectionLevelMap("csv_letter_maps/stages-Table 1.csv",
                inputConfiguration.isColourStyle());

        Set<String> letterSpacePairs = getLetterSpacePairs(); // Make new class for this. This won't work because I need duplicate keys

        char previousChar = 0;

        for (int j = 0; j < output.length; j ++) {
            String currentLayerOutput = "";
            for (VirusCharacter virusCharacter : virusCharacters) {

                for (int i = 0; i < virusCharacter.getGridSquares().length; i++) {

                    GridSquare currentSquare = virusCharacter.getGridSquares()[i][j];

                    currentLayerOutput += currentSquare.getOutput(infectionLevelMap, previousChar);
                }

                String pairWithNextCharacter = "" + virusCharacter.getCharacter() + getNextCharacterInList(virusCharacter);
                if (!letterSpacePairs.contains(pairWithNextCharacter) &&
                        virusCharacter.getCharacter() != '\r' && previousChar != '\r') {
                    currentLayerOutput += BLANK_SQUARE_CODE;
                }

                if (virusCharacter.getCharacter() == '\r') {
                    currentLayerOutput += "\n";
                }

                else if (virusCharacter.getCharacter() == ' ') {
                    currentLayerOutput += " ";
                }

                previousChar = virusCharacter.getCharacter();

            }
            output[j] = currentLayerOutput;
        }
        return output;
    }

    private char getNextCharacterInList(VirusCharacter current) {
        int index = virusCharacters.indexOf(current);
        if (index < 0 || index + 1 == virusCharacters.size()) {
            return '\0';
        }
        return virusCharacters.get(index + 1).getCharacter();
    }

    private Set<String> getLetterSpacePairs() {
        List<String[]> letterSpacePairContent = FileHelper.getCSVContent("csv_letter_maps/no-letterspace-pairs-Table 1.csv");
        Set<String> letterSpacePairMap = new HashSet<>();
        for (String[] row : letterSpacePairContent) {
            letterSpacePairMap.add(row[0] + row[1]);
        }
        return letterSpacePairMap;
    }

    public void setInputConfiguration(InputConfiguration inputConfiguration) {
        this.inputConfiguration = inputConfiguration;
    }

    public InputConfiguration getInputConfiguration() {
        return inputConfiguration;
    }

    public Map<String, VirusCharacter> getCharacterMap() {
        return characterMap;
    }

}
