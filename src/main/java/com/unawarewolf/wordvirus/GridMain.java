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

//        char[] charArray = getFileText("res/input_text.txt").toCharArray();
//
//        List<GridVirusCharacter> virusCharacters = createVirusCharacterList(charArray);
//
//        int count = 0;
//        for (GridVirusCharacter virusCharacter : virusCharacters) {
//            for (GridSquare[] row : virusCharacter.getGridSquares()) {
//                String infectionRow = "";
//                for (GridSquare square : row) {
//                    if (square.isDead()) {
//                        System.out.println();
//                    }
//                    infectionRow += square.getInfectionLevel();
//                }
//            }
//            count++;
//            if (count > 2000) {
//                break;
//            }
//        }
//
//        List<String> outputText = formatOutputContentFont(virusCharacters);
//
//        writeFormattedStringToFile("res/output_text.html", outputText);
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

//    public static List<String> formatOutputContentFont(List<GridVirusCharacter> virusCharacters) {
//        List<String> lines = new ArrayList<>();
//
//        int fontSize = 4;
//
////        lines.add("<!DOCTYPE html>");
////        lines.add("<html>");
////        lines.add("<style>");
////        lines.add("@font-face {");
////        lines.add("font-family: virusIcons;");
////        lines.add("src: url(Zygoponent-Regular.woff) format(\"woff\");");
////        lines.add("}");
////        lines.add("div {\n" +
////                "font-family: virusIcons;\n" +
////                "font-size: " + fontSize + "px;\n" +
////                "padding: 0px;\n" +
////                "line-height: " + fontSize * GridVirusCharacter.HEIGHT + "px;\n" +
////                "text-align: left;\n" +
////                "left: 10px;\n" +
////                "right: 10px;\n" +
////                "bottom: 10px;\n" +
////                "position: absolute;\n" +
////                "}");
////        lines.add("blank {");
////        lines.add("font-family: virusIcons;");
////        lines.add("color: white;");
////        lines.add("}");
//
//
////        for (int i = 0; i < GridVirusCharacter.HEIGHT; i++) {
////            int top = 10 + i * fontSize;
////            lines.add("#layer" + i + " {\n" +
////                    "top: " + top + "px;\n" +
////                    "}");
////        }
//
////        lines.add("</style>");
//
//        int characterCount = 0;
//
//        InfectionLevelMap infectionLevelMap = new InfectionLevelMap("csv_letter_maps/stages-Table 1.csv");
//
//        for (int h = 0; h < GridVirusCharacter.HEIGHT; h ++) {
//            String longLine = "";
////            lines.add("<div id = \"layer" + h + "\">");
//            int spaceCount = 0;
//            boolean previousIsBlank = false;
//            for (GridVirusCharacter virusCharacter : virusCharacters) {
//                characterCount ++;
//
//                int length = virusCharacter.getGridSquares().length;
//
//                for (int i = 0; i < length; i++) {
//
//                    GridSquare currentSquare = virusCharacter.getGridSquares()[i][h];
//
//                    String output = infectionLevelMap.get(currentSquare.getFontCode().charAt(0), currentSquare.getInfectionLevel());
//
//                    longLine += output;
//                }
//
//                longLine += "B";
//                if (virusCharacter.getCharacter() == ' ') {
//                    longLine += " ";
//                    spaceCount++;
//                }
//                else {
//                    spaceCount = 0;
//                }
//                if (spaceCount == 4) {
//                    longLine += "<br /><br />";
//                    spaceCount = 0;
//                }
//
//                if (characterCount > 5000) {
////                    break;
//                }
//
//            }
//            lines.add(longLine);
////            lines.add("</div>");
//
//            characterCount = 0;
//        }
//
////        lines.add("</html>");
//
//        return lines;
//    }

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

    public static String getFileText(String filePath) {
        String document = "";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                document += content;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

//    public static List<String[]> getCSVContent(String filePath) {
//        List<String[]> document = new ArrayList<>();
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
//            String content;
//            while ((content = bufferedReader.readLine()) != null) {
//                if (!content.equals("")) {
//                    document.add(content.split(","));
//                }
//            }
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return document;
//    }

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
//
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
//            String content;
//            while ((content = bufferedReader.readLine()) != null) {
//                if (!content.equals("")) {
//                    document.add(content.split(","));
//                }
//            }
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return document;
    }

    private static void writeFormattedStringToFile(String filePath, List<String> content) throws IOException {
        FileWriter fileWriter = new FileWriter(filePath);
        String newLine = System.getProperty("line.separator");

        for (String line : content) {
            fileWriter.write(line + newLine);
        }
        fileWriter.close();
    }

}
