package me.xerces.utils.managers;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author Xerces
 * @since 18/01/2015
 */
public class FileManager {

    /**
     * Reads a text file into a {@link String[]}
     * @param filePath the path of the file to read
     * @return an empty string array if an error and the contents of the text file if successful
     */
    public static String[] readTextFile(String filePath)
    {
        File textFile = new File(filePath);
        LinkedList<String> textLines = new LinkedList<String>();
        if(textFile.exists())
        {
            try {
                Scanner scanner = new Scanner(new FileInputStream(textFile));
                while(scanner.hasNext())
                {
                    String textLine = scanner.nextLine();
                    if(textLine != null)
                    {
                        textLines.add(textLine);
                    }
                    return textLines.toArray(new String[textLines.size()]);
                }
                scanner.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return new String[] {};
    }

    /**
     * Writes the String array to a text file
     * @param filePath the file path of the file to write to
     * @param fileLines the String array to write
     * @return true of it succeeded false if not
     */
    public static boolean writeTextFile(String filePath, String[] fileLines)
    {
        File textFile = new File(filePath);
        if(!textFile.exists()) {
            try {
                textFile.mkdirs();
                textFile.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(textFile));
            for(String string : fileLines)
            {
                bufferedWriter.write(string, 0, string.length());
            }
            bufferedWriter.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
