package com.pa.view;

import com.gutil.gui.GBC;
import com.gutil.gui.ImageUtil;
import com.gutil.gui.WindowUtil;
import com.gutil.gui.component.CustomSlider;
import com.gutil.gui.component.button.IconButton;
import com.gutil.gui.component.button.RectButton;
import com.gutil.gui.component.button.RoundButton;
import com.gutil.gui.component.button.RoundRectButton;
import com.gutil.gui.component.panel.RoundRectPanel;
import com.pa.controller.GameCreatorController;
import com.pa.model.creator.factory.PieceShape;
import com.pa.model.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

public class GameCreatorPanel extends JPanel {

    private static final Logger LOG = LoggerFactory.getLogger(GameCreatorPanel.class);

    private final AppWindow window;

    private final GameCreatorController creatorController;

    private final JLabel imagePath;
    private final JLabel imageDisplay;

    private final CustomSlider rowsSlider;
    private final CustomSlider columnsSlider;
    private final IconButton rectangleShape;
    private final IconButton classicShape;

    private RoundRectButton browseCatalogButton;
    private RoundRectButton exitButton;
    private RoundRectButton settingsButton;
    private RectButton editPictureButton;

    private PieceShape shapeFlag;

    public GameCreatorPanel(AppWindow window) {
        this.window = window;

        creatorController = new GameCreatorController();

        RoundButton fileChoiceButton = new RoundButton("Upload Image");
        fileChoiceButton.updateButtonColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY);
        fileChoiceButton.setActionListener(this::handleChoiceButton);

        imagePath = new JLabel();
        imagePath.setForeground(Color.LIGHT_GRAY);
        imageDisplay = new JLabel("Upload an image or go back to the photo catalog.");
        imageDisplay.setForeground(Color.LIGHT_GRAY);

        editPictureButton = new RectButton("Edit");
        editPictureButton.updateButtonColors(new Color(80,20,20), Color.LIGHT_GRAY, Color.GRAY);
        editPictureButton.setVisible(false);

        RoundRectPanel displayPanel = new RoundRectPanel(new GridBagLayout());
        displayPanel.setBackground(new Color(20,20,20));
        displayPanel.add(imagePath, new GBC(0,0). setInsets(10));
        displayPanel.add(editPictureButton, new GBC(0,1).setInsets(10).setAnchor(GBC.NORTHEAST));
        displayPanel.add(imageDisplay, new GBC(0,1).setInsets(10));

        JLabel rowsLabel = new JLabel("Set rows");
        rowsLabel.setForeground(Color.LIGHT_GRAY);

        JLabel columnsLabel = new JLabel("Set columns");
        columnsLabel.setForeground(Color.LIGHT_GRAY);

        JLabel shapeLabel = new JLabel("Pick shape");
        shapeLabel.setForeground(Color.LIGHT_GRAY);

        rowsSlider = new CustomSlider(1, 50, 10);
        columnsSlider = new CustomSlider(1, 50, 10);

        Image rectangle = ImageUtil.readImage(new File("C:\\Users\\greew\\Desktop\\rectShape.png"));
        Image classic = ImageUtil.readImage(new File("C:\\Users\\greew\\Desktop\\classicShape.png"));

        rectangleShape = new IconButton(rectangle,50,50);
        rectangleShape.setDefaultColor(Color.GRAY);

        classicShape = new IconButton(classic,50,50);
        classicShape.setDefaultColor(Color.GRAY);

        setShapeIconsColors(false, false);

        rectangleShape.setActionListener((event) -> {
            shapeFlag = shapeFlag != PieceShape.RECTANGULAR ? PieceShape.RECTANGULAR : null;
            setShapeIconsColors(false, shapeFlag == PieceShape.RECTANGULAR);
        });

        classicShape.setActionListener((event) -> {
            shapeFlag = shapeFlag != PieceShape.CLASSIC ? PieceShape.CLASSIC : null;
            setShapeIconsColors(shapeFlag == PieceShape.CLASSIC, false);
        });

        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        RoundRectPanel gameParametersPanel = new RoundRectPanel(new GridBagLayout());
        gameParametersPanel.setBackground(Color.DARK_GRAY);
        gameParametersPanel.setFramed(true);
        gameParametersPanel.add(rowsLabel, new GBC(0,0).setWeight(0.3, 1).setInsets(10, 10, 0, 0));
        gameParametersPanel.add(rowsSlider, new GBC(1,0,3,1).setWeight(0.7, 1).setFill(GBC.HORIZONTAL).setInsets(10, 0, 0, 20));
        gameParametersPanel.add(columnsLabel, new GBC(0,1).setWeight(0.3, 1).setInsets(0, 10, 0, 0));
        gameParametersPanel.add(columnsSlider, new GBC(1,1,3,1).setWeight(0.7, 1).setFill(GBC.HORIZONTAL).setInsets(0, 0, 0, 20));
        gameParametersPanel.add(shapeLabel, new GBC(0,2).setWeight(0.3, 1).setInsets(0, 10, 10, 0));
        gameParametersPanel.add(rectangleShape, new GBC(1,2).setAnchor(GBC.WEST));
        gameParametersPanel.add(classicShape, new GBC(2,2).setAnchor(GBC.WEST));
        gameParametersPanel.add(emptyPanel, new GBC(3,2).setWeight(1,1).setFill(GBC.HORIZONTAL));

        RoundButton buildGameButton = new RoundButton("Build Game");
        buildGameButton.keepSymmetry(true);
        buildGameButton.updateButtonColors(new Color(80,20,20), Color.LIGHT_GRAY, Color.GRAY);
        buildGameButton.setActionListener(this::buildGame);

        browseCatalogButton = new RoundRectButton("Browse Catalog");
        browseCatalogButton.updateButtonColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY);

        exitButton = new RoundRectButton("Exit");
        exitButton.updateButtonColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY);
        exitButton.setActionListener(event -> System.exit(0));

        settingsButton = new RoundRectButton("Settings");
        settingsButton.updateButtonColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY);

        JPanel mainMenuPanel = new JPanel(new GridLayout(1,2));
        mainMenuPanel.add(settingsButton);
        mainMenuPanel.add(exitButton);

        JPanel creatorPanel = new JPanel(new GridBagLayout());
        creatorPanel.add(fileChoiceButton, new GBC(0,0).setAnchor(GBC.WEST));
        creatorPanel.add(displayPanel, new GBC(0,1).setWeight(0.5, 1).setFill(GBC.BOTH));
        creatorPanel.add(gameParametersPanel, new GBC(0, 2).setFill(GBC.BOTH));
        creatorPanel.add(buildGameButton, new GBC(0,3).setAnchor(GBC.NORTH).setInsets(20));
        creatorPanel.add(new JPanel(), new GBC(1,2, 1, 2).setWeight(0.5, 1).setFill(GBC.BOTH));

        setLayout(new GridBagLayout());
        add(mainMenuPanel, new GBC(0,0,2,1).setAnchor(GBC.EAST).setInsets(0,0,50,0));
        add(browseCatalogButton, new GBC(0,1).setAnchor(GBC.NORTH).setInsets(0,0,0,50));
        add(creatorPanel, new GBC(1,1).setWeight(1,1).setFill(GBC.BOTH));

    }

    private void buildGame(ActionEvent actionEvent) {
        try {
            creatorController.setRows(rowsSlider.getValue());
            creatorController.setColumns(columnsSlider.getValue());
            creatorController.setShape(shapeFlag);

            Game game = creatorController.buildGame();
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
        imageDisplay.setText(null);

        editPictureButton.setVisible(image != null);

        creatorController.setImage(image);
    }

    private void setShapeIconsColors (boolean classicOn, boolean rectangularOn) {
        if (classicOn && rectangularOn) {
            return;
        }

        classicShape.setDefaultColor(classicOn ? Color.LIGHT_GRAY : Color.GRAY);
        classicShape.setHighlightColor(classicOn ? Color.WHITE : Color.LIGHT_GRAY);

        rectangleShape.setDefaultColor(rectangularOn ? Color.LIGHT_GRAY : Color.GRAY);
        rectangleShape.setHighlightColor(rectangularOn ? Color.WHITE : Color.LIGHT_GRAY);
    }

}
