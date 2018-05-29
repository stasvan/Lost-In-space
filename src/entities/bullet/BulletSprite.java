package entities.bullet;

import constants.Constants;
import graphics.TextureAtlas;
import sprite.Sprite;

import java.awt.*;

public class BulletSprite implements Sprite {

    private TextureAtlas texture;

    private final int WIDTH = 9;
    private final int HEIGHT = 18;

    // default position of plane
    private int x;
    private int y;

    //velocity
    private int velY = Constants.BULLET_VELOCITY_Y;


    public BulletSprite(String textureFileName) {
        texture = new TextureAtlas(textureFileName);
    }



    public void render(Graphics2D graphics2D) {
        y -= velY;
        if (texture != null) {
            graphics2D.drawImage(texture.cut(0, 0, 9, 18), (int) x, (int) y, null);
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
