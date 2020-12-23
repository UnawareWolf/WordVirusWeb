package com.unawarewolf.wordvirus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static List<String[]> getCSVContent(String filePath) {
        List<String[]> csvContent = new ArrayList<>();
        for (String line : readFileLineByLine(filePath)) {
            if (!line.equals("")) {
               csvContent.add(convertDoubleQuotesToCommas(line.split(",")));
            }
        }
        return csvContent;
    }

    public static String[] convertDoubleQuotesToCommas(String[] strArray) {
        String rebuiltLine = "";
        for (String entry : strArray) {
            rebuiltLine += entry;
        }
        rebuiltLine = rebuiltLine.replace("\"\"", ",");

        String[] rebuiltStrArray = new String[rebuiltLine.length()];
        int charCount = 0;
        for (char singleChar : rebuiltLine.toCharArray()) {
            rebuiltStrArray[charCount] = Character.toString(singleChar);
            charCount += 1;
        }
        return rebuiltStrArray;
    }

    public static String getFileAsString(String filePath) {
        String document = "";
        for (String line : readFileLineByLine(filePath)) {
            document += line;
        }
        return document;
    }

    private static List<String> readFileLineByLine(String filePath) {
        List<String> document = new ArrayList<>();
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String content;
            try {
                while ((content = bufferedReader.readLine()) != null) {
                    document.add(content);
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
