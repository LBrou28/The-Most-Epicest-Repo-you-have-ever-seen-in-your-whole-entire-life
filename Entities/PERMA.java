package Entities;

import java.awt.*;

public class PERMA {

    

    public int P = 50;
    public int E = 50;
    public int R = 50;
    public int M = 50;
    public int A = 50;

    private final int MAX = 100;

    public void increase(String type, int amount) {
        switch (type) {
            case "P" -> P = Math.max(0, Math.min(MAX, P + amount));
            case "E" -> E = Math.max(0, Math.min(MAX, E + amount));
            case "R" -> R = Math.max(0, Math.min(MAX, R + amount));
            case "M" -> M = Math.max(0, Math.min(MAX, M + amount));
            case "A" -> A = Math.max(0, Math.min(MAX, A + amount));
        }
    }

    public void draw(Graphics g) {
        int x = 20;
        int y = 60;
        int width = 150;
        int height = 10;
        int spacing = 18;

        drawBar(g, "P", P, x, y, width, height, Color.YELLOW);
        drawBar(g, "E", E, x, y + spacing, width, height, Color.BLUE);
        drawBar(g, "R", R, x, y + spacing * 2, width, height, Color.PINK);
        drawBar(g, "M", M, x, y + spacing * 3, width, height, Color.ORANGE);
        drawBar(g, "A", A, x, y + spacing * 4, width, height, Color.GREEN);
    }

    private void drawBar(Graphics g, String label, int value,
                         int x, int y, int width, int height, Color color) {

        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, width, height);

        int filled = (int)((value / 100.0) * width);

        g.setColor(color);
        g.fillRect(x, y, filled, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        g.drawString(label + ": " + value, x + width + 10, y + height);
    }
        public boolean isMaxed() {
            return P >= 100 && E >= 100 && R >= 100 && M >= 100 && A >= 100;
    }
}