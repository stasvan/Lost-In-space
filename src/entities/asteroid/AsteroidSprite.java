package entities.asteroid;

import constants.Constants;
import game.GameController;
import graphics.TextureAtlas;
import sprite.Sprite;

import java.awt.*;

public class AsteroidSprite implements Sprite {


    private TextureAtlas texture;

    private GameController context;


    private final int WIDTH = 80;
    private final int HEIGHT = 64;

    // default position of plane
    private int x = Constants.SCREEN_WIDTH / 2 - (WIDTH / 2);
    private int y = 0;

    //velocity
    private int velY = Constants.ASTEROID_VELOCITY;


    public AsteroidSprite(String textureFileName, GameController context) {
        texture = new TextureAtlas(textureFileName);
        this.context = context;
    }

    // 60 times in sec

    public void render(Graphics2D graphics2D) {
        y += velY;
        if (texture != null) {
            graphics2D.drawImage(texture.cut(0, 0, WIDTH, HEIGHT), (int) x, (int) y, null);
        }
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setYVelocity(int YVelocity) {
        this.velY = YVelocity;
    }


    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }


    public void erase() {
        this.texture = null;
    }
}
