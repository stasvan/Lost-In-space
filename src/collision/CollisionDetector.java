package collision;

import sprite.Sprite;

public class CollisionDetector {


    public static boolean checkCollision(Sprite target1, Sprite target2) {


        return target2.getY() + target2.getHeight() >= target1.getY()
                && target2.getY() <= target1.getY()
                && target1.getX() + target1.getWidth() >= target2.getX()
                && target1.getX() <= target2.getX() + target2.getWidth();

    }

}
