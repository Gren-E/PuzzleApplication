package com.pa.gui;

import com.gutil.GBC;
import com.gutil.ImageUtil;
import com.gutil.WindowUtil;
import com.pa.creator.GameCreator;
import com.pa.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

public class CreatorPanel extends JPanel {

    private static final Logger LOG = LoggerFactory.getLogger(CreatorPanel.class);

    private final AppWindow window;

    private final GameCreator creator;

    private final JLabel imagePath;
    private final JLabel imageDisplay;

    private final JTextField rowsField;
    private final JTextField columnsField;

    public CreatorPanel(AppWindow window) {
        this.window = window;

        creator = new GameCreator();

        JButton fileChoiceButton = new JButton("Choose Image");
        fileChoiceButton.addActionListener(this::handleChoiceButton);

        imagePath = new JLabel();
        imageDisplay = new JLabel();

        JLabel rowsLabel = new JLabel("Set rows");
        rowsField = new JTextField("6");

        JLabel columnsLabel = new JLabel("Set columns");
        columnsField = new JTextField("8");

        JButton buildGameButton = new JButton("Build Game");
        buildGameButton.addActionListener(this::buildGame);

        setLayout(new GridBagLayout());
        add(imageDisplay, new GBC(0,0,2,1));
        add(imagePath, new GBC(0,1,2,1));
        add(fileChoiceButton, new GBC(0,2,2,1));
        add(rowsLabel, new GBC(0,3));
        add(columnsLabel, new GBC(1,3));
        add(rowsField, new GBC(0,4));
        add(columnsField, new GBC(1,4));
        add(buildGameButton, new GBC(0,5,2,1));
    }

    private void buildGame(ActionEvent actionEvent) {
        try {
            creator.setRows(Integer.parseInt(rowsField.getText()));
            creator.setColumns(Integer.parseInt(columnsField.getText()));

            Game game = creator.buildGame();
            window.loadGame(game);
        } catch (Exception e) {
            LOG.error("Cannot build a game.", e);
        }
    }

    private void handleChoiceButton(ActionEvent event) {
        File imageFile = WindowUtil.selectFile(this, "jpg", "png");
        Image image = ImageUtil.readImage(imageFile);

        imagePath.setText(imageFile != null ? imageFile.getAbsolutePath() : "No image");
        imageDisplay.setIcon(image != null ? new ImageIcon(image) : null);

        creator.setImage(image);
    }

}
