package com.unawarewolf.wordvirus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VirusCharacter {

    private Coordinate initialSquareInfected;

    private char character;
    private boolean firstInfected;
    private GridSquare[][] gridSquares;
    private int width, height;
    private boolean quoteStart;

    private VirusGenerator virusGenerator;
    private InputConfiguration inputConfiguration;

    private Random rand;

    public VirusCharacter(VirusGenerator virusGenerator, char character, boolean quoteStart) {
        this.quoteStart = quoteStart;
        this.inputConfiguration = virusGenerator.getInputConfiguration();
        this.character = character;
        this.virusGenerator = virusGenerator;
        rand = new Random();
        firstInfected = (character == inputConfiguration.getInitiallyInfected());
        initialiseGridSquares();
    }

    public VirusCharacter(VirusCharacter virusCharacter, boolean quoteStart) {
        this.quoteStart = quoteStart;
        inputConfiguration = virusCharacter.getInputConfiguration();
        character = virusCharacter.getCharacter();
        virusGenerator = virusCharacter.getVirusGenerator();
        gridSquares = copyGridSquares(virusCharacter.getGridSquares());
        width = virusCharacter.getWidth();
        height = virusCharacter.getHeight();
        rand = new Random();
        firstInfected = false;
    }

    private void initialiseGridSquares() {
        List<String[]> csvData = getCharacterCSVData();
        if (firstInfected) {
            setInitialSquareInfected();
        }
        width = csvData.get(0).length;
        height = csvData.size();
        gridSquares = new GridSquare[width][height];

        int yCount = 0;

        for (String[] csvRow : csvData) {

            int xCount = 0;
            for (String csvCell : csvRow) {

                Coordinate coordinates = new Coordinate(xCount, yCount);
                gridSquares[xCount][yCount] = new GridSquare(this, coordinates, csvCell);
                xCount++;
            }

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

//        virusGenerator.getCharacterMap().put(character, this);
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

        if (gridSquare.getInfectionLevel() < VirusGenerator.MAX_INFECTION_LEVEL && !gridSquare.isImmune() && !firstInfected) {

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

        return catchesInfectionFromWithinCharacter(gridSquare) || catchesInfectionFromPreviousCharacter(gridSquare, previousCharacter);
    }

    private boolean catchesInfectionFromWithinCharacter(GridSquare gridSquare) {
        return catchesInfectionFromCharacter(gridSquare, this, 0);
    }

    private boolean catchesInfectionFromPreviousCharacter(GridSquare gridSquare, PreviousCharacter previousCharacter) {
        return (previousCharacter != null && catchesInfectionFromCharacter(gridSquare, previousCharacter.getVirusCharacter(), previousCharacter.getOffsetSquares()));
    }

    private boolean catchesInfectionFromCharacter(GridSquare gridSquare, VirusCharacter virusCharacter, int offsetSquares) {
        for (GridSquare[] gridSquareRows : virusCharacter.getGridSquares()) {
            for (GridSquare previousSquare : gridSquareRows) {
                if (catchesInfectionFromGridSquare(gridSquare, previousSquare, offsetSquares)) {
                    return true;
                }
            }
        }
        return false;
    }

    private PreviousCharacter getNearestCharacterThatCanTransmit(List<VirusCharacter> previousCharacters, int countFromEnd, int offsetSquares) {
        int listSize = previousCharacters.size();
        PreviousCharacter previousCharacter;
        if (listSize > countFromEnd) {

            VirusCharacter previousVirusCharacter = previousCharacters.get(previousCharacters.size() - 1 - countFromEnd);

            offsetSquares += previousVirusCharacter.getWidth() + 1;

            previousCharacter = new PreviousCharacter(previousVirusCharacter, offsetSquares);

            if (previousCharacter.getVirusCharacter().canTransmit()) {
                return previousCharacter;
            }

            return getNearestCharacterThatCanTransmit(previousCharacters, countFromEnd + 1, offsetSquares);
        }

        return null;
    }

    private boolean catchesInfectionFromGridSquare(GridSquare gridSquare, GridSquare secondSquare, int offsetSquares) {
        if (!gridSquare.isBlankSquare() && gridSquare.getInfectionLevel() == 0 && secondSquare.isInfected() && !secondSquare.isBlankSquare()) {
            double xDist = gridSquare.getXCoordinate() + offsetSquares - secondSquare.getXCoordinate();
            double yDist = gridSquare.getYCoordinate() - secondSquare.getYCoordinate();
            double absDist = Math.pow(Math.pow(xDist, 2) + Math.pow(yDist, 2), 0.5);
            return gridSquare.catchesInfection(secondSquare, absDist);
        }
        return false;
    }

    private void setInitialSquareInfected() {
        List<Coordinate> possibleCoordinates = new ArrayList<>();
        int rowCount = 0;
        for (String[] letterRow : getCharacterCSVData()) {
            int columnCount = 0;
            for (String squareCode : letterRow) {
                if (!squareCode.equals(VirusGenerator.BLANK_SQUARE_CODE)) {
                    possibleCoordinates.add(new Coordinate(columnCount, rowCount));
                }
                columnCount++;
            }
            rowCount++;
        }
        if (possibleCoordinates.size() == 0) {
            possibleCoordinates.add(new Coordinate(0,0));
        }
        initialSquareInfected = possibleCoordinates.get(rand.nextInt(possibleCoordinates.size()));
    }

    private List<String[]> getCharacterCSVData() {
        String filePath = "csv_letter_maps/";

        switch(character) {
            case '.':
                filePath += "point-Table 1.csv";
                break;
            case ' ':
                filePath += "space-Table 1.csv";
                break;
            case '?':
                filePath += "question-Table 1.csv";
                break;
            case ':':
                filePath += "colon-Table 1.csv";
                break;
            case '-':
                filePath += "hyphen-Table 1.csv";
                break;
            case '–':
                filePath += "dash-Table 1.csv";
                break;
            case '&':
                filePath += "ampersand-Table 1.csv";
                break;
            case '"':
                if (quoteStart) {
                    filePath += "quote left double-Table 1.csv";
                }
                else {
                    filePath += "quote right double-Table 1.csv";
                }
                break;
            case '\'':
                if (quoteStart) {
                    filePath += "quote left single-Table 1.csv";
                }
                else {
                    filePath += "quote right single-Table 1.csv";
                }
                break;
            case '′':
                filePath += "prime-Table 1.csv";
                break;
            case '″':
                filePath += "double prime-Table 1.csv";
                break;
            default:
                filePath += character;
                if (Character.isUpperCase(character)) {
                    filePath += "cap";
                }
                filePath += "-Table 1.csv";
        }

        List<String[]> csvData = FileHelper.getCSVContent(filePath);
        return csvData.size() > 0 ? csvData : FileHelper.getCSVContent("csv_letter_maps/space-Table 1.csv");
    }

    public Character getCharacter() {
        return character;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public InputConfiguration getInputConfiguration() {
        return inputConfiguration;
    }

    public boolean canTransmit() {
        return character != ' ';
    }

    public VirusGenerator getVirusGenerator() {
        return virusGenerator;
    }

}
