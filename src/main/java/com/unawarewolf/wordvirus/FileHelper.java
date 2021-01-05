package com.unawarewolf.wordvirus;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    
    public static String getRandomImagePath() throws IOException, URISyntaxException {
        String imagePath = "";
        PathMatchingResourcePatternResolver matchingResolver = new PathMatchingResourcePatternResolver();
        
        Resource imagesRoot = matchingResolver.getResource("/static/images");
        
//        String[] imagePaths = imagesRoot.getFile().list();
        InputStream resourceStream = imagesRoot.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(resourceStream));
        String resource;
        List<String> imagePaths = new ArrayList<>();
        while ((resource = br.readLine()) != null) {
            imagePaths.add(resource);
        }
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePath = imagePaths.get(new Random().nextInt(imagePaths.size()));
            String[] segments = imagePath.split("\\\\");
            imagePath = segments[segments.length - 1];
        }
        return imagePath;
    }
    
    public static String getImageInfo(String imagePath) {
        return imagePath;
    }

}
