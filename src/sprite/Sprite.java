package sprite;

import java.awt.*;

public interface Sprite {
    void render(Graphics2D graphics2D);

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    void erase();

}