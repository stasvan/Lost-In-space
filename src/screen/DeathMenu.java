package screen;

import IO.FileSystemService;
import constants.Constants;
import game.GameController;
import game.GameState;
import graphics.TextureAtlas;
import hud.HUDStatusBar;
import utils.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DeathMenu implements GameMenu {


    private final Font font;

    private final JFrame parentFrame;
    private final JPanel modalContent;
    private final JDialog dialog;

    private int score;
    private String scorePattern = "%s";
    private final JTextField textField;
    private TextureAtlas gameOverImage;
    private TextureAtlas saveImage;
    private TextureAtlas menuImage;

    private FileSystemService fileSystemService;
    private GameController context;

    public DeathMenu(JFrame parentFrame, GameController context) {

        fileSystemService = new FileSystemService();
        this.context = context;

        gameOverImage = new TextureAtlas("gameOver.png");
        saveImage = new TextureAtlas("save.png");
        menuImage = new TextureAtlas("menu.png");

        this.parentFrame = parentFrame;
        font = new Font("arial", Font.BOLD, 50);
        modalContent = new JPanel(new GridBagLayout());

        dialog = new JDialog(parentFrame, "Enter your name", true);
        dialog.getContentPane().add(modalContent);

        textField = new JTextField("Player", 20);
        textField.setVisible(true);

        JButton jButton = new JButton("Ok");

        jButton.addActionListener(new SaveResultActionListener());

        jButton.setVisible(true);

        modalContent.add(textField);
        modalContent.add(jButton);

    }


    public void render(Graphics2D graphics, int score) {
        this.score = score;

        // render score
        String strScore = String.valueOf(score);

        StringBuilder base = new StringBuilder("");

        for (int i= 0; i < 8 - strScore.length(); i++) {
            base.append("0");
        }
        base.append(strScore);

        graphics.setFont(this.font);
        graphics.setColor(Color.WHITE);

        graphics.drawImage(gameOverImage.cut(0, 0, 314, 182), 140, 130, null);
        graphics.drawImage(saveImage.cut(0, 0, 200, 50), Constants.SCREEN_WIDTH / 2  - 100, 450, null);
        graphics.drawImage(menuImage.cut(0, 0, 200, 50), Constants.SCREEN_WIDTH / 2  - 100, 550, null);

        graphics.drawString(String.format(scorePattern, base), 190, 400);

        dialog.setBounds((int) parentFrame.getLocationOnScreen().getX() + (Constants.SCREEN_WIDTH / 2) - 150,
                (int) parentFrame.getLocationOnScreen().getY() + Constants.SCREEN_HEIGHT / 2 - 100, 300, 200);

    }




    public class SaveResultActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // todo validate name

            //ResourceLoader.resolve("records.txt");

            String text = String.format("%s:%s", textField.getText(), score);



            System.out.println(text);
            fileSystemService.writeToFile(ResourceLoader.resolve("scores.txt"), text.trim());
            dialog.setVisible(false);
        }

    }

    @Override
    public void firstButtonClick() {
        dialog.setVisible(true);
    }

    @Override
    public void secondButtonClick() {
        context.setGameState(GameState.MENU);
    }
}
