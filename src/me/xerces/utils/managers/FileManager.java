package me.xerces.utils.managers;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        LinkedList<String> textLines = new LinkedList<>();
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
                }
                scanner.close();
                return textLines.toArray(new String[textLines.size()]);
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

    /**
     * Convert a {@link java.util.jar.JarEntry} to a byte array
     * @param jarFile the {@link java.util.jar.JarFile} so we can get the {@link java.io.InputStream}
     * @param jarEntry {@link java.util.jar.JarEntry} to convert
     * @return the byte array
     * @throws IOException
     */
    public static byte[] getBytesFromEntry(ZipFile zipFile, ZipEntry zipEntry) throws IOException
    {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStream = zipFile.getInputStream(zipEntry);
        int readIn;
        while((readIn = inputStream.read()) != -1)
        {
            arrayOutputStream.write(readIn);
        }
        return arrayOutputStream.toByteArray();
    }
}
