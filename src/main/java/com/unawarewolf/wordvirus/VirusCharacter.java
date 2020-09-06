package com.unawarewolf.wordvirus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VirusCharacter {

    private Coordinate initialSquareInfected;

    private char character;
    private boolean firstInfected;
    private GridSquare[][] gridSquares;
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

    private GridSquare[][] copyGridSquares(GridSquare[][] gridSquares) {
        GridSquare[][] newGridSquares = new GridSquare[gridSquares.length][gridSquares[0].length];
        for (GridSquare[] gridSquareRow : gridSquares) {
            for (GridSquare gridSquare : gridSquareRow) {
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
        for (GridSquare[] gridSquareRow : gridSquares) {
            for (GridSquare gridSquare : gridSquareRow) {
                updateInfectionLevel(gridSquare, virusCharacters);
                gridSquare.update();
            }
        }
    }

    private void updateInfectionLevel(GridSquare gridSquare, List<VirusCharacter> virusCharacters) {

        if (gridSquare.getInfectionLevel() < GridSquare.MAX_INFECTION_LEVEL && !gridSquare.isImmune() && !firstInfected) {

            if (increaseLevelIfCatchesInfection(gridSquare, virusCharacters) || gridSquare.infectionProgresses()) {

                gridSquare.increaseInfectionLevel();
                if (gridSquare.isInRecovery()) {
                    gridSquare.setRelapsed(true);
                }
            }
            else if (gridSquare.infectionRecovers()) {

                gridSquare.decreaseInfectionLevel();
                gridSquare.setRecoveryCondition();
            }
        }

    }

    private boolean increaseLevelIfCatchesInfection(GridSquare gridSquare, List<VirusCharacter> previousCharacters) {

        PreviousCharacter previousCharacter = getNearestCharacterThatCanTransmit(previousCharacters, 0, 0);

        if (previousCharacter != null) {
            for (GridSquare[] gridSquareRows : previousCharacter.getVirusCharacter().getGridSquares()) {
                for (GridSquare previousSquare : gridSquareRows) {
                    if (gridSquare.getInfectionLevel() == 0 && catchesInfection(gridSquare, previousSquare, previousCharacter.getOffsetSquares())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public PreviousCharacter getNearestCharacterThatCanTransmit(List<VirusCharacter> previousCharacters, int countFromEnd, int offsetSquares) {
        int listSize = previousCharacters.size();
        PreviousCharacter previousCharacter;
        if (listSize > countFromEnd) {
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
    }

    private boolean catchesInfection(GridSquare gridSquare, GridSquare secondSquare, int offsetSquares) {
        if (secondSquare.isInfected() && !secondSquare.isBlankSquare()) {
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

        List<String[]> csvData = FileHelper.getCSVContent(filePath);
        return csvData.size() > 0 ? csvData : FileHelper.getCSVContent("csv_letter_maps/space-Table 1.csv");
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
                if (squareCode.equals(VirusGenerator.BLANK_SQUARE_CODE)) {
                    possibleCoordinates.add(new Coordinate(rowCount, columnCount));
                }
            }
        }
        initialSquareInfected = possibleCoordinates.get(rand.nextInt(possibleCoordinates.size()));
    }
}
