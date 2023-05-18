package com.mfarioli.JavaTD.Helpers;

import com.mfarioli.JavaTD.Objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {
    public static BufferedImage getSpriteAtlas() {
        InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream("spriteatlas.png");

        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    public static BufferedImage getMenuBackground() {
        InputStream inputStream = LoadSave.class.getClassLoader().getResourceAsStream("menubackground.png");

        BufferedImage image = null;
        try {
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    }

    /*
     * createLevel(String levelName, int[][] idArray)
     * creates a .txt file called levelName.txt in the resources/levels folder
     * levelName HAS to be in the levelN (where N is a positive number)
     * since getLevelData  can only search for levels called levelN.
     * idArray is the 2D array containing the level we want to store.
     */
    public static void createLevel(String levelName, int[][] idArray, PathPoint start, PathPoint end) {
        File newLevel = new File("/Users/marco/IdeaProjects/JavaTD/src/main/resources/levels/" + levelName + ".txt");
        if (!newLevel.getParentFile().exists()) {
            newLevel.getParentFile().mkdirs();
        }

        if (newLevel.exists()) {
            System.out.println("File with that name (" + levelName + ") already exists");
            return;
        }

        try {
            newLevel.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WriteToFile(newLevel, idArray, start, end);
    }

    private static void WriteToFile(File f, int[][] idArray, PathPoint start, PathPoint end) {
        try {
            PrintWriter printWriter = new PrintWriter(f);

            for (int y = 0; y < idArray.length; y++) {
                for (int x = 0; x < idArray[y].length; x++) {
                    printWriter.println(idArray[y][x]);
                }
            }

            printWriter.println(start.getxCord());
            printWriter.println(start.getyCord());
            printWriter.println(end.getxCord());
            printWriter.println(end.getyCord());

            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * getLevelData(int n)
     * loads a 2D int array with the data contained in a .txt file
     * located in the resources/levels folder.
     * n is the number of the level we want to load (files in the levels
     * folder all have levelN names, where N is positive number)
     */
    public static int[][] getLevelData(int n) {
        File levelFile = new File("/Users/marco/IdeaProjects/JavaTD/src/main/resources/levels/level" + n + ".txt");

        if (!levelFile.exists()) {
            System.out.println("Level with that number doesn't exist");
            return null;
        }

        ArrayList<Integer> list = readFromFile(levelFile);
        return Utilities.arrayListTo2Dint(list, 20, 20);
    }

    private static ArrayList<Integer> readFromFile(File file) {
        ArrayList<Integer> list = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                list.add(Integer.parseInt(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public static ArrayList<PathPoint> getLevelPathPoints(int n) {
        File levelFile = new File("/Users/marco/IdeaProjects/JavaTD/src/main/resources/levels/level" + n + ".txt");

        if (!levelFile.exists()) {
            System.out.println("Level with that number doesn't exist");
            return null;
        }

        ArrayList<Integer> list = readFromFile(levelFile);
        ArrayList<PathPoint> pathPoints = new ArrayList<>();
        pathPoints.add(new PathPoint(list.get(400), list.get(401)));
        pathPoints.add(new PathPoint(list.get(402), list.get(403)));

        return pathPoints;
    }
}
