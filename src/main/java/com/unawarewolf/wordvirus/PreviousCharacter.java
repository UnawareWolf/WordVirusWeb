package com.unawarewolf.wordvirus;

public class PreviousCharacter {

    private VirusCharacter virusCharacter;
    private int offsetSquares;

    public PreviousCharacter(VirusCharacter virusCharacter, int offsetSquares) {
        this.virusCharacter = virusCharacter;
        this.offsetSquares = offsetSquares;
    }

    public VirusCharacter getVirusCharacter() {
        return virusCharacter;
    }

    public int getOffsetSquares() {
        return offsetSquares;
    }
}
