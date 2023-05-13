package com.mfarioli.JavaTD.UI;

import java.awt.*;

public class CustomButton {
    private String text;

    private int x, y, width, heigth;

    private Rectangle bounds;

    private boolean mouseOver;

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public CustomButton(String text, int x, int y, int width, int heigth) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        bounds = new Rectangle(x, y, width, heigth);
    }

    public void draw(Graphics g) {
        //body
        drawBody(g);
        //border
        drawBorder(g);
        //text
        drawText(g);
    }

    private void drawBody(Graphics g) {
        if(mouseOver) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.WHITE);
        }

        g.fillRect(x, y, width, heigth);
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, heigth);
    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();

        g.drawString(text, x - w / 2 + width / 2, y + h / 3 + heigth / 2);
    }
}
