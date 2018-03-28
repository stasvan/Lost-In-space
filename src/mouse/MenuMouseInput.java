package mouse;

import constants.Constants;
import game.GameController;
import game.GameState;
import screen.DeathMenu;
import screen.GameMenu;
import screen.MainMenu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MenuMouseInput implements MouseListener {

    private final GameController context;
    private GameMenu deathMenu;
    private GameMenu mainMenu;


    public MenuMouseInput(GameController gameController) {
        context = gameController;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();


        if (context.getGameState() == GameState.MENU) {
            System.out.println("Main Menu action");
            performMenuAction(x, y, mainMenu);
        } else if (context.getGameState() == GameState.DEATH_MENU) {
            System.out.println("Death Menu action");
            performMenuAction(x, y, deathMenu);
        }

    }

    private void performMenuAction(int x, int y, GameMenu menu) {
        if (x > Constants.SCREEN_WIDTH / 2  - 100 && x < (Constants.SCREEN_WIDTH / 2  - 100 ) + 200) {
            if (y > 450 && y < 450 + 50) {
                System.out.println("first button click");
                menu.firstButtonClick();
            } else if (y > 550 && y < 600)  {
                System.out.println("second button click");
                menu.secondButtonClick();
            } else if (y > 650 && y < 700) {
                System.out.println("third button click");
                ((MainMenu)menu).thirdButtonClick();;
            }
        }



    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public GameMenu getDeathMenu() {
        return deathMenu;
    }

    public void setDeathMenu(GameMenu deathMenu) {
        this.deathMenu = deathMenu;
    }

    public GameMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(GameMenu mainMenu) {
        this.mainMenu = mainMenu;
    }
}
