package hud;

import constants.Constants;
import game.GameController;
import sprite.Sprite;

import java.awt.*;

public class HUDStatusBar implements Sprite{

    private int targetHealth;

    private GameController context;

    private String scorePattern = "%s";
    private int score;

    private final int HEIGHT = 32;

    // default position of hud
    private int x = 10;
    private int y = 10;

    public HUDStatusBar(Integer health, GameController context) {
        this.context = context;
        this.targetHealth = health;
    }


    public void render(Graphics2D graphics2D) {
        // render health bar
        graphics2D.setColor(Color.RED);
        graphics2D.fillRect(x, y, (int)(targetHealth * 2.4), HEIGHT);

        // render score
        String strScore = String.valueOf(score);

        StringBuilder base = new StringBuilder("");

        for (int i= 0; i < 8 - strScore.length(); i++) {
            base.append("0");
        }

        base.append(strScore);

        graphics2D.setColor(Color.WHITE);
        Font font = new Font("arial", Font.BOLD, 40);
        graphics2D.setFont(font);
        graphics2D.drawString(String.format(scorePattern, base), (x + Constants.SCREEN_WIDTH - 200), y + 30);
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {

            this.score += score;

    }

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public void erase() {
    }

    public void setHealth(int health) {
        this.targetHealth = health;
    }

    public void refresh() {
        this.score = 0;
    }
}
