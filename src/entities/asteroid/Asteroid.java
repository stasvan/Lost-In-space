package entities.asteroid;

import constants.Constants;
import game.GameController;

public class Asteroid extends AsteroidSprite {

    private int score = Constants.ASTEROID_SCORE;
    private int damage = Constants.ASTEROID_DAMAGE;

    public Asteroid(String textureFileName, GameController context) {
        super(textureFileName, context);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String toString() {
        return "Asteroid{" + "score=" + score +
                ", damage=" + damage +
                '}';
    }
}