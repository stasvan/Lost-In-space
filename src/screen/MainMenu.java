package screen;

import constants.Constants;
import game.GameController;
import game.GameState;
import graphics.TextureAtlas;
import record.Record;
import record.RecordService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu implements GameMenu{


    private final DefaultListModel model;

    private final JDialog dialog;
    private TextureAtlas logoImage;
    private TextureAtlas startImage;
    private TextureAtlas scoresImage;
    private TextureAtlas exitImage;
    private final RecordService recordService;
    private final JFrame parentFrame;

    private GameController context;

    public MainMenu(GameController context, JFrame parentFrame) {
        recordService = new RecordService();

        logoImage = new TextureAtlas("logo.png");
        startImage = new TextureAtlas("start.png");
        scoresImage = new TextureAtlas("scores.png");
        exitImage = new TextureAtlas("exit.png");

        this.context = context;
        this.parentFrame = parentFrame;
        dialog = new JDialog(parentFrame, "Scores", true);


        // default empty list
        model = new DefaultListModel<String>();
        JList<String> list = new JList(model);
        list.setVisible(true);

        // create list view
        JScrollPane modalContent = new JScrollPane(list);
        dialog.getContentPane().add(modalContent);

    }

    public void render(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);

        graphics.drawImage(logoImage.cut(0, 0, 313, 331), 150, 70, null);
        graphics.drawImage(startImage.cut(0, 0, 200, 50), Constants.SCREEN_WIDTH / 2  - 100, 450, null);
        graphics.drawImage(scoresImage.cut(0, 0, 200, 50), Constants.SCREEN_WIDTH / 2  - 100, 550, null);
        graphics.drawImage(exitImage.cut(0, 0, 200, 50), Constants.SCREEN_WIDTH / 2  - 100, 650, null);

        dialog.setBounds((int) parentFrame.getLocationOnScreen().getX() + (Constants.SCREEN_WIDTH / 2) - 150,
                (int) parentFrame.getLocationOnScreen().getY() + Constants.SCREEN_HEIGHT / 2 - 100, 300, 200);

    }

    @Override
    public void firstButtonClick() {
        context.clearGameArea();
        context.setGameState(GameState.IN_GAME);
    }

    @Override
    public void secondButtonClick() {

        // find all records
        model.clear();
        ArrayList<Record> records = recordService.findAllRecords();
//        DefaultListModel<String> model = new DefaultListModel<String>();
        for (Record record : records) {
            System.out.println(record);
            model.addElement(record.toString());
        }

        dialog.setVisible(true);

    }

    public void thirdButtonClick() {
        System.exit(0);
    }
}