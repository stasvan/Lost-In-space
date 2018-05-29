package entities.plane;

import constants.Constants;
import game.GameController;
import graphics.TextureAtlas;
import sprite.Sprite;

import java.awt.*;

public class PlaneSprite implements Sprite {


    protected TextureAtlas texture;
    protected Graphics2D graphics2D;

    protected GameController context;


    protected final int WIDTH = 70;
    protected final int HEIGHT = 78;

    // default position of plane
    protected int x = Constants.SCREEN_WIDTH / 2 - (WIDTH / 2);
    protected int y = Constants.SCREEN_HEIGHT - HEIGHT;

    //velocity
    protected int velX = Constants.PLANE_VELOCITY_X;


    public PlaneSprite(String textureFileName, GameController context) {
        texture = new TextureAtlas(textureFileName);
        this.context = context;
    }

    // 60 times in sec
    public void render(Graphics2D graphics2D) {
        if (texture != null) {
            graphics2D.drawImage(texture.cut(0, 0, WIDTH, HEIGHT), x, y, null);
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

    public void left() {

        // left wall collision detection
        if (!(x <= 0)) {
            x -= velX;
        }
    }

    public void right() {

        // right wall collision detection
        if (!(x >= (Constants.SCREEN_WIDTH - WIDTH))) {
            x += velX;
        }
    }




    public int getWidth() {
        return WIDTH;
    }


    public int getHeight() {
        return HEIGHT;
    }


    public void erase() {
        this.texture = null;
    }
}
