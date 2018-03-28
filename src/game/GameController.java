package game;

import IO.Input;
import collision.CollisionDetector;
import constants.Constants;
import entities.asteroid.Asteroid;
import entities.bullet.BulletSprite;
import entities.plane.Plane;
import graphics.TextureAtlas;
import hud.HUDStatusBar;
import mouse.MenuMouseInput;
import music.Music;
import screen.DeathMenu;
import screen.MainMenu;
import sprite.Sprite;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameController implements Runnable {


    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy bufferStrategy;

    public static final String TITLE = "Lost in Space";


    public static final String ATLAS_FILE_NAME = "plane.png";


    private ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
    private ArrayList<Sprite> asteroidList = new ArrayList<Sprite>();
    private ArrayList<Sprite> bulletList = new ArrayList<Sprite>();


    private HUDStatusBar hudStatusBar;

    private Input input;

    private Plane plane;

    private DeathMenu deathMenu;
    private MainMenu mainMenu;
    private boolean initialRendering = true;


    private long lastShootTime;
    private long lastAsteroidSpawnTime;

    private GameState gameState = GameState.MENU;
    private TextureAtlas bgImage;


    private long desiredFPS = 60;
    private long desiredDeltaLoop = (1000 * 1000 * 1000) / desiredFPS;

    private boolean running = true;


    private GameController() {


        input = new Input();
        bgImage = new TextureAtlas("space.jpg");

        plane = new Plane(ATLAS_FILE_NAME, this);
        spriteList.add(plane);

        frame = new JFrame(TITLE);
        frame.add(input);

        deathMenu = new DeathMenu(frame, this);
        mainMenu = new MainMenu(this, frame);

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, Constants.SCREEN_WIDTH,  Constants.SCREEN_HEIGHT);
        canvas.setIgnoreRepaint(true);

        // attach mouse listener to
        MenuMouseInput menuMouseInput= new MenuMouseInput(this);
        menuMouseInput.setDeathMenu(deathMenu);
        menuMouseInput.setMainMenu(mainMenu);
        canvas.addMouseListener(menuMouseInput);

        panel.add(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);


        canvas.createBufferStrategy(3);
        bufferStrategy = canvas.getBufferStrategy();

        canvas.requestFocus();
    }


    public void run() {

        long beginLoopTime;
        long endLoopTime;
        long deltaLoop;
        System.out.println(running);


        while (running) {


            beginLoopTime = System.nanoTime();

            render();

            update();

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            if (deltaLoop > desiredDeltaLoop) {
                //Do nothing. We are already late.
            } else {
                try {
                    Thread.sleep((desiredDeltaLoop - deltaLoop) / (1000 * 1000));
                } catch (InterruptedException e) {
                    //Do nothing
                }
            }
        }
    }

    private void render() {

        // first render
        if (initialRendering) {
            plane = new Plane(ATLAS_FILE_NAME, this);
            System.out.println("initial rendering");
            spriteList.add(plane);
            initialRendering = false;

            hudStatusBar = new HUDStatusBar(plane.getHealth(), this);
        }

        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        graphics.drawImage(bgImage.cut(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT), 0, 0, null);


        if (gameState == GameState.IN_GAME ) {
            //System.out.println("here");


            // create asteroids
            if (System.currentTimeMillis() - lastAsteroidSpawnTime > Constants.ASTEROID_SPAWN_INTERVAL) {
                Random random = new Random();
                int numberOfAsteroids = random.nextInt(4) + 1;

                for (int i = 0; i < numberOfAsteroids; i++) {
                    Asteroid asteroidSprite = new Asteroid("asteroid.png", this);
                    int xPosition = random.nextInt(Constants.SCREEN_WIDTH - asteroidSprite.getWidth());
                    int yVelocity = random.nextInt(Constants.ASTEROID_VELOCITY) + 2;
                    asteroidSprite.setX(xPosition);
                    asteroidSprite.setY(-100);
                    asteroidSprite.setYVelocity(yVelocity);

                    spriteList.add(asteroidSprite);
                    asteroidList.add(asteroidSprite);
                }

                lastAsteroidSpawnTime = System.currentTimeMillis();
            }


            Iterator<Sprite> iterator = spriteList.iterator();
            // render all sprites
            while (iterator.hasNext()) {
                Sprite sprite = iterator.next();
                sprite.render(graphics);

                // sprite far off the screen -> delete all references
                if (sprite.getY() < -100) {
                    iterator.remove();
                }

            }

            hudStatusBar.render(graphics);
            plane.render(graphics);

        } else if (gameState == GameState.DEATH_MENU) {
            deathMenu.render(graphics, hudStatusBar.getScore());
        } else if (gameState == GameState.MENU) {
            mainMenu.render(graphics);
        }


        graphics.dispose();
        bufferStrategy.show();
    }

    protected void update() {


        if (gameState == GameState.IN_GAME && plane.getHealth() > 0) {
            if (input.getKey(KeyEvent.VK_LEFT)) {
                plane.left();
            }

            if (input.getKey(KeyEvent.VK_RIGHT)) {
                plane.right();
            }

            if (input.getKey(KeyEvent.VK_ESCAPE)) {
                gameState = GameState.MENU;
            }

            if (input.getKey(KeyEvent.VK_SPACE)) {

                if (System.currentTimeMillis() - lastShootTime > Constants.SHOOT_TIME_INTERVAL) {
                    plane.shoot();
                    lastShootTime = System.currentTimeMillis();
                }
            }

            // bullet|asteroid collision detection
            for (Sprite bullet : bulletList) {
                for (Sprite asteroid : asteroidList) {

                    boolean collided = CollisionDetector.checkCollision(bullet, asteroid);
                    if (collided) {


                        ((BulletSprite) bullet).setY(Constants.SCREEN_HEIGHT - 1000);
                        ((BulletSprite) bullet).setX(Constants.SCREEN_WIDTH + 2000);
                        ((Asteroid) asteroid).setY(Constants.SCREEN_HEIGHT - 1000);
                        ((Asteroid) asteroid).setX(Constants.SCREEN_WIDTH + 2000);

                        hudStatusBar.setScore(((Asteroid) asteroid).getScore());

                        bullet.erase();
                        asteroid.erase();
                    }
                }

            }

            // plane|steroid collision detection
            for (Sprite asteroid : asteroidList) {
                boolean collided = CollisionDetector.checkCollision(plane, asteroid);

                if (collided) {
                    asteroid.erase();
                    ((Asteroid) asteroid).setY(Constants.SCREEN_HEIGHT - 1000);
                    ((Asteroid) asteroid).setX(Constants.SCREEN_WIDTH + 2000);
                    plane.takeDamage(((Asteroid) asteroid).getDamage());
                    hudStatusBar.setHealth(plane.getHealth());
                }
            }

            if (plane.getHealth() <= 0) {
                gameState = GameState.DEATH_MENU;
            }
        }

    }


    // clear all sprites
    public void clearGameArea() {
        Iterator<Sprite> iterator = spriteList.iterator();
        hudStatusBar.refresh();

        while (iterator.hasNext()) {
            Sprite sprite = iterator.next();
            sprite.erase();

            // sprite far off the screen -> delete all references
            if (sprite.getY() < -100) {
                iterator.remove();
            }

        }

        spriteList.clear();
        bulletList.clear();
        asteroidList.clear();
        initialRendering = true;
    }

    // create bullet
    public void addBulletToContext(Sprite sprite) {
        this.spriteList.add(sprite);
        this.bulletList.add(sprite);
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Music m = new Music();
        new Thread(m).start();

        GameController ex = new GameController();
        new Thread(ex).start();

    }
}

