package com.unawarewolf.wordvirus;
//import spark.ModelAndView;
//import spark.template.velocity.VelocityTemplateEngine;
//
//import static spark.Spark.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class GridMain {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(GridMain.class, args);
    }

    public static List<GridVirusCharacter> createVirusCharacterList(char[] charArray, Greeting params) {
        List<GridVirusCharacter> gridVirusCharacters = new ArrayList<>();
        Map<Character, GridVirusCharacter> characterMap = new HashMap<>();
        Map<String, Double> parameterMap = new HashMap<>();

        parameterMap.put("infectionRateLow", params.getInfectionLow());
        parameterMap.put("infectionRateHigh", params.getInfectionHigh());
        parameterMap.put("progressionRateLow", params.getProgressionLow());
        parameterMap.put("progressionRateHigh", params.getProgressionHigh());
        parameterMap.put("recoveryRateLow", params.getRecoveryLow());
        parameterMap.put("recoveryRateHigh", params.getRecoveryHigh());

        for (char character : charArray) {
            GridVirusCharacter gridVirusCharacter = createVirusCharacter(character, characterMap, parameterMap);
//            gridVirusCharacter.update(gridVirusCharacters.size() > 0 ? gridVirusCharacters.get(gridVirusCharacters.size() - 1) : null);
            gridVirusCharacter.update(gridVirusCharacters);
            gridVirusCharacters.add(gridVirusCharacter);
        }
        return gridVirusCharacters;
    }

    public static String[] formatOutputContentFont(List<GridVirusCharacter> virusCharacters) {
        String[] layers = new String[GridVirusCharacter.HEIGHT];

        int fontSize = 4;

        InfectionLevelMap infectionLevelMap = new InfectionLevelMap("csv_letter_maps/stages-Table 1.csv");

        for (int h = 0; h < layers.length; h ++) {
            String longLine = "";
            int spaceCount = 0;
            boolean previousIsBlank = false;
            for (GridVirusCharacter virusCharacter : virusCharacters) {

                int length = virusCharacter.getGridSquares().length;

                for (int i = 0; i < length; i++) {

                    GridSquare currentSquare = virusCharacter.getGridSquares()[i][h];

                    String output = infectionLevelMap.get(currentSquare.getFontCode().charAt(0), currentSquare.getInfectionLevel());

                    longLine += output;
                }

                longLine += "B";
                if (virusCharacter.getCharacter() == ' ') {
                    longLine += " ";
                    spaceCount++;
                }
                else {
                    spaceCount = 0;
                }
                if (spaceCount == 4) {
//                    longLine += "<br /><br />";
                    spaceCount = 0;
                }

            }
            layers[h] = longLine;
        }

        return layers;
    }

    private static GridVirusCharacter createVirusCharacter(char character, Map<Character, GridVirusCharacter> characterMap, Map<String, Double> parameterMap) {
        if (useCopyConstructor(character, characterMap)) {
            return new GridVirusCharacter(characterMap.get(character));
        }
        return new GridVirusCharacter(character, characterMap, parameterMap);
    }

    private static boolean useCopyConstructor(char character, Map<Character, GridVirusCharacter> characterMap) {
        if (characterMap.containsKey(character)) {
            return true;
        }
        return false;
    }

    public static List<String[]> getCSVContent(String filePath) {

        List<String[]> document = new ArrayList<>();
        InputStream inputStream = GridMain.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String content;
            try {
                while ((content = bufferedReader.readLine()) != null) {
                    if (!content.equals("")) {
                        document.add(content.split(","));
                    }
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        return document;
    }

    public static String getFileAsString(String filePath) {

        String document = "";
        InputStream inputStream = GridMain.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String content;
            try {
                while ((content = bufferedReader.readLine()) != null) {
                    document += content;
                }
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        return document;
    }

}
