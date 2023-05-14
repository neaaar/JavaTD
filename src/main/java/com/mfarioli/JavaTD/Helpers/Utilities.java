package com.mfarioli.JavaTD.Helpers;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Utilities {

    public static int[][] arrayListTo2Dint(ArrayList<Integer> list, int xSize, int ySize) {
        int[][] array = new int[xSize][ySize];

        for(int y = 0; y < array.length; y++) {
            for(int x = 0; x < array[y].length; x++) {
                int index = y * ySize + x;
                array[y][x] = list.get(index);
            }
        }

        return array;
    }

    //rotates a tile
    public static BufferedImage getRotImg(BufferedImage image, int rotAngle) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage rotatedImage = new BufferedImage(width, height, image.getType());
        Graphics2D graphics2D = rotatedImage.createGraphics();

        //rotates around the center thanks to width / 2, height / 2
        graphics2D.rotate(Math.toRadians(rotAngle), width / 2, height / 2);
        graphics2D.drawImage(image, 0, 0, null);
        graphics2D.dispose();

        return rotatedImage;
    }

    //creates a new image by layering  images on top of each other
    public static BufferedImage buildImg(BufferedImage[] images) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
        Graphics2D graphics2D = newImage.createGraphics();

        for(BufferedImage image : images) {
            graphics2D.drawImage(image, 0, 0, null);
        }
        graphics2D.dispose();

        return newImage;
    }

    //rotates second image only
    public static BufferedImage getBuildRotImg(BufferedImage[] images, int rotAngle, int rotAtIndex) {
        int width = images[0].getWidth();
        int height = images[0].getHeight();

        BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
        Graphics2D graphics2D = newImage.createGraphics();

        for(int i = 0; i < images.length; i++) {
            if(rotAtIndex == i) {
                graphics2D.rotate(Math.toRadians(rotAngle), width / 2, height / 2);
            }
            graphics2D.drawImage(images[i], 0, 0, null);
        }
        graphics2D.dispose();

        return newImage;
    }

    //rotates second image only + animation
    public static BufferedImage[] getBuildRotImg(BufferedImage[] images, BufferedImage secondImage, int rotAngle) {
        int w = images[0].getWidth();
        int h = images[0].getHeight();

        BufferedImage[] arr = new BufferedImage[images.length];

        for (int i = 0; i < images.length; i++) {

            BufferedImage newImg = new BufferedImage(w, h, images[0].getType());
            Graphics2D g2d = newImg.createGraphics();

            g2d.drawImage(images[i], 0, 0, null);
            g2d.rotate(Math.toRadians(rotAngle), w / 2, h / 2);
            g2d.drawImage(secondImage, 0, 0, null);
            g2d.dispose();

            arr[i] = newImg;
        }

        return arr;

    }
}
