package entities.plane;

import constants.Constants;
import entities.bullet.BulletSprite;
import game.GameController;
import game.GameController;
import hud.HUDStatusBar;

import java.awt.*;

public class Plane extends PlaneSprite {



    private int health = 99;

    public Plane(String textureFileName, GameController context) {
        super(textureFileName, context);
    }

    public void render(Graphics2D graphics2D) {
        super.render(graphics2D);
    }


    // shoot bullet
    public void shoot() {
        BulletSprite bulletSprite = new BulletSprite(Constants.BULLET_TEXTURE_NAME);
        bulletSprite.setX(x + (WIDTH / 2) - (bulletSprite.getWidth() / 2));
        bulletSprite.setY(y - bulletSprite.getHeight());

        context.addBulletToContext(bulletSprite);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }
}
