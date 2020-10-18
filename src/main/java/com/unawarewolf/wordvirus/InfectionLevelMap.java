package com.unawarewolf.wordvirus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfectionLevelMap {

    private Map<Character, InfectionCharacterProgression> stagesRowMap;
    private boolean useColour;

    public InfectionLevelMap(String filePath, boolean useColour) {
        this.useColour = useColour;
        List<String[]> content = FileHelper.getCSVContent(filePath);

        stagesRowMap = new HashMap<>();

        for (String[] line : content) {
            char initialLetter = line[0].charAt(0);
            stagesRowMap.put(initialLetter, new InfectionCharacterProgression(line));
        }

    }

    public String get(Character initialLetter, int level) {
        return useColour ? stagesRowMap.get(initialLetter).get(0) : stagesRowMap.get(initialLetter).get(level);
    }

    private class InfectionCharacterProgression {

        private Map<Integer, String> integerToInfectionCharacterMap;

        public InfectionCharacterProgression(String[] csvLine) {
            integerToInfectionCharacterMap = new HashMap<>();
            int infectionLevelCount = 0;
            for (String cell : csvLine) {
                integerToInfectionCharacterMap.put(infectionLevelCount, cell);
                infectionLevelCount++;
            }
        }

        public String get(int key) {
            return integerToInfectionCharacterMap.get(key);
        }
    }

}
