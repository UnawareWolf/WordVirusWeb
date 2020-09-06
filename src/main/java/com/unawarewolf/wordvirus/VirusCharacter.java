package com.unawarewolf.wordvirus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VirusCharacter {

    public static final int WIDTH = 12;
    public static final int HEIGHT = 12;

//    private static final char INITIAL_CHAR_INFECTED = 'a';
//    private static final int[] INITIAL_SQUARE_INFECTED = new int[] {0, 4};

    private Coordinate initialSquareInfected;

    private char character;
    private boolean firstInfected;
    private GridSquare[][] gridSquares;
    private GridSquare[][] duplicateSquares;
    private int width, height;

    private Map<String, Double> parameterMap;

    private Map<Character, VirusCharacter> characterMap; // Same map for all virus characters.

    private Random rand;

    public VirusCharacter(char character, Map<Character, VirusCharacter> characterMap, Map<String, Double> parameterMap, char initiallyInfected) {
        this.parameterMap = parameterMap;
        this.character = character;
        this.characterMap = characterMap;
        rand = new Random();
        firstInfected = (character == initiallyInfected);
        initialiseGridSquares();
    }

    public VirusCharacter(VirusCharacter virusCharacter) {
        parameterMap = virusCharacter.getParameterMap();
        character = virusCharacter.getCharacter();
        characterMap = virusCharacter.getCharacterMap();
        gridSquares = copyGridSquares(virusCharacter.getGridSquares());
        width = virusCharacter.getWidth();
        height = virusCharacter.getHeight();
        rand = new Random();
        firstInfected = false;
    }

    private void initialiseGridSquares() {
        List<String[]> csvData = getCharacterCSVData();
        setInitialSquareInfected();
        if (csvData.size() > 0) {
            width = csvData.get(0).length;
            height = csvData.size();
            gridSquares = new GridSquare[width][height];

            int xCount = 0;
            int yCount = 0;

            for (String[] csvRow : csvData) {

                for (String csvCell : csvRow) {

                    Coordinate coordinates = new Coordinate(xCount, yCount);
                    gridSquares[xCount][yCount] = new GridSquare(this, coordinates, csvCell);
                    xCount++;
                }

                xCount = 0;
                yCount++;
            }
        }
        else {
            width = WIDTH;
            height = HEIGHT;
            gridSquares = new GridSquare[WIDTH][HEIGHT];
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    Coordinate coordinates = new Coordinate(i, j);
                    gridSquares[i][j] = new GridSquare(this, coordinates, "B");
                }
            }
        }
    }

    private GridSquare[][] copyGridSquares(GridSquare[][] gridSquares) {
//        this.gridSquares = new GridSquare[WIDTH][HEIGHT];
        GridSquare[][] newGridSquares = new GridSquare[gridSquares.length][gridSquares[0].length];
        for (GridSquare[] gridSquareRow : gridSquares) {
            for (GridSquare gridSquare : gridSquareRow) {
//                this.gridSquares[gridSquare.getXCoordinate()][gridSquare.getYCoordinate()] = new GridSquare(gridSquare);
                newGridSquares[gridSquare.getXCoordinate()][gridSquare.getYCoordinate()] = new GridSquare(gridSquare);


            }
        }
        return newGridSquares;
    }

    public void update(List<VirusCharacter> virusCharacters) {

        updateInfectionLevels(virusCharacters);

        characterMap.put(character, this);
    }

    private void updateInfectionLevels(List<VirusCharacter> virusCharacters) {
//        duplicateSquares = copyGridSquares(gridSquares);
        for (GridSquare[] gridSquareRow : gridSquares) {
            for (GridSquare gridSquare : gridSquareRow) {
                updateInfectionLevel(gridSquare, virusCharacters);
                gridSquare.update();
            }
        }
    }

    private void updateInfectionLevel(GridSquare gridSquare, List<VirusCharacter> virusCharacters) {
//        boolean levelHasIncreased = increaseLevelIfCatchesInfection(gridSquare, previousCharacter);
        if (gridSquare.getInfectionLevel() < GridSquare.MAX_INFECTION_LEVEL && !gridSquare.isImmune() && !firstInfected) {
            if (increaseLevelIfCatchesInfection(gridSquare, virusCharacters) || gridSquare.infectionProgresses()) {
                gridSquare.increaseInfectionLevel();
                if (gridSquare.getInRecovery()) {
                    gridSquare.setHasRelapsed(true);
                }
            } else if (gridSquare.infectionRecovers()) {
                gridSquare.decreaseInfectionLevel();
                gridSquare.setRecoveryCondition();
            }
        }

    }

    private boolean increaseLevelIfCatchesInfection(GridSquare gridSquare, List<VirusCharacter> previousCharacters) {

        PreviousCharacter previousCharacter = getNearestCharacterThatCanTransmit(previousCharacters, 0, 0);

        if (previousCharacter == null) {
            return false;
        }

        for (GridSquare[] gridSquareRows : previousCharacter.getVirusCharacter().getGridSquares()) {
            for (GridSquare previousSquare : gridSquareRows) {
                if (gridSquare.getInfectionLevel() == 0 && catchesInfection(gridSquare, previousSquare, previousCharacter.getOffsetSquares())) {
                    return true;
                }
            }
        }

        return false;

//        for (GridSquare[] gridSquareRows : duplicateSquares) {
//            for (GridSquare secondSquare : gridSquareRows) {
//                if (gridSquare.getInfectionLevel() == 0 && catchesInfection(gridSquare, secondSquare, 0)) {
//                    return true;
//                }
//            }
//        }
//
//        int countFromEnd = 0;
//        VirusCharacter previousCharacter = null;
//        int offsetSquares = 0;
//        while (pastSquareCannotTransmit(virusCharacters, countFromEnd) || (virusCharacters.size() > countFromEnd + 1 && countFromEnd == 0)) {
//            previousCharacter = virusCharacters.get(virusCharacters.size() - 1 - countFromEnd);
//            offsetSquares += previousCharacter.getWidth() + 1;
//            countFromEnd++;
//        }
//
//        if (previousCharacter == null) {
//            return false;
//        }
//
////        if (previousCharacter != null) {
//            for (GridSquare[] previousSquareRows : previousCharacter.getGridSquares()) {
//                for (GridSquare previousSquare : previousSquareRows) {
//                    if (gridSquare.getInfectionLevel() == 0 && catchesInfection(gridSquare, previousSquare, offsetSquares)) {
//                        return true;
//                    }
//                }
//            }
////        }
//
//
//
////        if (gridVirusCharacters.size() > 2) {
////
////            GridVirusCharacter previousCharacter = gridVirusCharacters.get(gridVirusCharacters.size() - 1);
////            if (!previousCharacter.getCharacter().equals(' ')) {
////                for (GridSquare[] previousSquareRows : previousCharacter.getGridSquares()) {
////                    for (GridSquare previousSquare : previousSquareRows) {
////                        if (gridSquare.getInfectionLevel() == 0 && catchesInfection(gridSquare, previousSquare, previousSquare.getWidth() + 1)) {
////                            return true;
////                        }
////                    }
////                }
////            }
////            else {
////                // check previous square (and one before if full stop).
////
////            }
////        }
//        return false;
    }

    public PreviousCharacter getNearestCharacterThatCanTransmit(List<VirusCharacter> previousCharacters, int countFromEnd, int offsetSquares) {
        int listSize = previousCharacters.size();
        PreviousCharacter previousCharacter;
        if (listSize > countFromEnd + 1) {
            VirusCharacter previousVirusCharacter = previousCharacters.get(previousCharacters.size() - 1 - countFromEnd);
            previousCharacter = new PreviousCharacter(previousVirusCharacter, offsetSquares);

            if (previousCharacter.getVirusCharacter().canTransmit()) {
                return previousCharacter;
            }

            offsetSquares += previousCharacter.getVirusCharacter().getWidth() + 1;
            return getNearestCharacterThatCanTransmit(previousCharacters, countFromEnd + 1, offsetSquares);
        }

        return null;
    }

    public boolean canTransmit() {
        return character != ' ';
//        return Character.isLetterOrDigit(character);
    }

//    public boolean pastSquareCannotTransmit(List<VirusCharacter> virusCharacters, int countFromEnd) {
//        // should at least be previousCharacterCannotTransmit...
//        int listSize = virusCharacters.size();
//        if (listSize > countFromEnd + 1) {
//            char pastCharacter = virusCharacters.get(listSize - 1 - countFromEnd).getCharacter();
//            if (!Character.isLetterOrDigit(pastCharacter)) {
//                return true;
//            }
//        }
//        return false;
//    }

    private boolean catchesInfection(GridSquare gridSquare, GridSquare secondSquare, int offsetSquares) {
        if (secondSquare.getIsInfected() && !secondSquare.isBlankSquare()) {
            double xDist = gridSquare.getXCoordinate() + offsetSquares - secondSquare.getXCoordinate();
            double yDist = gridSquare.getYCoordinate() - secondSquare.getYCoordinate();
            double absDist = Math.pow(Math.pow(xDist, 2) + Math.pow(yDist, 2), 0.5);
            return gridSquare.catchesInfection(secondSquare, absDist);
        }
        return false;
    }

    private List<String[]> getCharacterCSVData() {
        String filePath;
        if (character == '.') {
            filePath = "csv_letter_maps/point-Table 1.csv";
        }
        else if (character == ' ') {
            filePath = "csv_letter_maps/space-Table 1.csv";
        }
        else {
            filePath = "csv_letter_maps/";
            if (Character.isUpperCase(character)) {
                filePath += "caps/";
            }
            filePath += character + "-Table 1.csv";
        }

        return FileHelper.getCSVContent(filePath);
    }

    public Character getCharacter() {
        return character;
    }

    public Map<Character, VirusCharacter> getCharacterMap() {
        return characterMap;
    }

    public boolean getFirstInfected() {
        return firstInfected;
    }

    public Coordinate getInitialSquareInfected() {
//        return INITIAL_SQUARE_INFECTED;
        return initialSquareInfected;
    }

    public GridSquare[][] getGridSquares() {
        return gridSquares;
    }

    public List<GridSquare> getGridSquareList() {
        List<GridSquare> gridSquareList = new ArrayList<>();
        for (GridSquare[] gridSquareRow : gridSquares) {
            for (GridSquare gridSquare : gridSquareRow) {
                gridSquareList.add(gridSquare);
            }
        }
        return gridSquareList;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<String, Double> getParameterMap() {
        return parameterMap;
    }

    private void setInitialSquareInfected() {
        List<Coordinate> possibleCoordinates = new ArrayList<>();
        int rowCount = 0;
        for (String[] letterRow : getCharacterCSVData()) {
            int columnCount = 0;
            for (String squareCode : letterRow) {
                if (squareCode.equals("B")) {
                    possibleCoordinates.add(new Coordinate(rowCount, columnCount));
                }
            }
        }
//        Coordinate initialInfectedCoordinates = possibleCoordinates.get(rand.nextInt(possibleCoordinates.size()));
//        if (initialInfectedCoordinates != null) {
            initialSquareInfected = possibleCoordinates.get(rand.nextInt(possibleCoordinates.size()));
//        }
//        else {
//            initialSquareInfected = INITIAL_SQUARE_INFECTED;
//        }

    }
}
