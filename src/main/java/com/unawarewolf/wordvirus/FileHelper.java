package com.unawarewolf.wordvirus;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
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
//        PathMatchingResourcePatternResolver matchingResolver = new PathMatchingResourcePatternResolver();
        
//        Resource imagesRoot = matchingResolver.getResource("/static/images");
        
//        String[] imagePaths = imagesRoot.getFile().list();
//        imagesRoot.getInputStream();
        List<String> imagePaths = getFilenamesForDirnameFromCP("static/images");
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePath = imagePaths.get(new Random().nextInt(imagePaths.size()));
            String[] segments = imagePath.split("\\\\");
            imagePath = segments[segments.length - 1];
        }
        return imagePath;
    }
    public static List<String> getFilenamesForDirnameFromCP(String directoryName) throws URISyntaxException, UnsupportedEncodingException, IOException {
        List<String> filenames = new ArrayList<>();

        URL url = Thread.currentThread().getContextClassLoader().getResource(directoryName);
        if (url != null) {
            if (url.getProtocol().equals("file")) {
                File file = Paths.get(url.toURI()).toFile();
                if (file != null) {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File filename : files) {
                            filenames.add(filename.toString());
                        }
                    }
                }
            } else if (url.getProtocol().equals("jar")) {
                String dirname = directoryName + "/";
                String path = url.getPath();
                String jarPath = path.substring(5, path.indexOf("!"));
                try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8.name()))) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.startsWith(dirname) && !dirname.equals(name)) {
                            URL resource = Thread.currentThread().getContextClassLoader().getResource(name);
                            filenames.add(resource.toString());
                        }
                    }
                }
            }
        }
        return filenames;
    }
    
    public static String getImageInfo(String imagePath) {
        return imagePath;
    }

}
