package com.unawarewolf.wordvirus;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SquareDraw extends JPanel {

    public static final int SQUARES_PER_LINE = 300;
    public static final int PIXELS_PER_SQUARE = 3;

    private int xDraw;
    private int yDraw;

    private Graphics2D graphics2D;

    private List<GridVirusCharacter> virusCharacters;

    public SquareDraw(List<GridVirusCharacter> virusCharacters) {
        super();
        this.virusCharacters = virusCharacters;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        int xDraw = 0;
//        int yDraw = 0;
        int x = 0;
        int y = 0;

        List<GridVirusCharacter> currentWord = new ArrayList<>();
        int consecutiveSpaces = 0;
        for (GridVirusCharacter virusCharacter : virusCharacters) {
//            if (virusCharacter.getCharacter() == ' ' && currentWord.size() > 0) {
//                consecutiveSpaces++;
//                drawCurrentWord(g2d, currentWord, x, y);
//                currentWord.clear();
//            }
//            else {
//                consecutiveSpaces = 0;
//            }
//
//            currentWord.add(virusCharacter);
//
//            if ()

            drawVirusCharacter(g2d, virusCharacter, x, y);

            x += virusCharacter.getWidth() * PIXELS_PER_SQUARE;

            for (int i = 0; i < virusCharacter.getHeight(); i++) {
                g2d.setColor(new Color(240, 240, 240));
//                int x = xDraw;
//                int y = yDraw;
                g2d.fillRect(x, y + i * PIXELS_PER_SQUARE, PIXELS_PER_SQUARE, PIXELS_PER_SQUARE);

            }
            x += PIXELS_PER_SQUARE;

            if (x >= SQUARES_PER_LINE * PIXELS_PER_SQUARE) {
                y += virusCharacter.getHeight() * PIXELS_PER_SQUARE;
                x = 0;
            }

        }

    }

    private void drawCurrentWord(Graphics2D g2d, List<GridVirusCharacter> currentWord, int x, int y) {
        for (GridVirusCharacter virusCharacter : currentWord) {
            drawVirusCharacter(g2d, virusCharacter, x, y);
        }
    }

    private void drawVirusCharacter(Graphics2D g2d, GridVirusCharacter virusCharacter, int x, int y) {

        for (GridSquare gridSquare : virusCharacter.getGridSquareList()) {
            int colour = gridSquare.getInfectionLevel() * (240 / GridSquare.MAX_INFECTION_LEVEL);
            if (!gridSquare.isBlankSquare()) {
                g2d.setColor(new Color(colour, colour, colour));
            }
            else {
                g2d.setColor(new Color(240, 240, 240));
            }
//            int x = xDraw;
//            int y = yDraw;
            g2d.fillRect(x + PIXELS_PER_SQUARE * gridSquare.getXCoordinate(),
                    y + PIXELS_PER_SQUARE * gridSquare.getYCoordinate(), PIXELS_PER_SQUARE, PIXELS_PER_SQUARE);
        }


//        x += virusCharacter.getWidth() * PIXELS_PER_SQUARE;
//
//        for (int i = 0; i < virusCharacter.getHeight(); i++) {
//            g2d.setColor(new Color(240, 240, 240));
//            g2d.fillRect(x, y + i * PIXELS_PER_SQUARE, PIXELS_PER_SQUARE, PIXELS_PER_SQUARE);
//
//        }
//        x += PIXELS_PER_SQUARE;
    }
}
