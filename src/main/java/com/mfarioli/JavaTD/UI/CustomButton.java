package com.mfarioli.JavaTD.UI;

import java.awt.*;

public class CustomButton {
    private int id;

    private String text;

    public int x, y, width, height;

    private Rectangle bounds;

    private boolean mouseOver, mousePressed;

    public int getId() {
        return id;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMouseOver() {
        if(mouseOver) return true;
        return false;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    //for normal buttons
    public CustomButton(String text, int x, int y, int width, int height) {
        this.id = -1;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    //for ActionBar buttons
    public CustomButton(int id, String text, int x, int y, int width, int height) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    public void resetBooleans() {
        this.mouseOver = false;
        this. mousePressed = false;
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
            g.setColor(new Color(20, 60, 200, 196));
        } else {
            g.setColor(new Color(20, 60, 200, 128));
        }

        g.fillRect(x, y, width, height);
    }

    private void drawBorder(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);

        if (mousePressed) {
            g.drawRect(x + 1, y + 1, width - 2, height - 2);
            g.drawRect(x + 2, y + 2, width - 4, height - 4);
        }

    }

    private void drawText(Graphics g) {
        int w = g.getFontMetrics().stringWidth(text);
        int h = g.getFontMetrics().getHeight();

        g.drawString(text, x - w / 2 + width / 2, y + h / 3 + height / 2);
    }
}
