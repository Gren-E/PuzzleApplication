package com.pa.view.game;

import com.gutil.gui.GBC;
import com.gutil.gui.ImageUtil;
import com.gutil.gui.ResizeQuality;
import com.gutil.gui.WindowUtil;
import com.gutil.gui.adapters.HighlightingMouseAdapter;
import com.gutil.gui.component.button.RoundButton;
import com.gutil.gui.component.button.RoundRectButton;
import com.gutil.gui.component.panel.RoundRectPanel;
import com.pa.AppEnv;
import com.pa.view.AppWindow;
import com.pa.view.ComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CatalogPanel extends RoundRectPanel {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogPanel.class);

    private AppWindow window;

    private RoundRectButton exitButton;

    private final JPanel picturesPanel;

    public CatalogPanel(AppWindow window) {
        this.window = window;
        setBackground(Color.LIGHT_GRAY);

        RoundButton fileChoiceButton = ComponentFactory.createStandardRoundButton("Upload Image");
        fileChoiceButton.setActionListener(this::handleChoiceButton);

        RoundRectButton sortButton = ComponentFactory.createStandardAppButton("Sort");

        picturesPanel = new JPanel();
        picturesPanel.setOpaque(false);

        JScrollPane picturesScrollPane = ComponentFactory.createVerticalJScrollPane(picturesPanel);

        setLayout(new GridBagLayout());
        add(sortButton, new GBC(1,0).setInsets(20));
        add(fileChoiceButton, new GBC(0,1).setAnchor(GBC.NORTH).setWeight(0.1,1).setFill(GBC.HORIZONTAL).setInsets(50));
        add(picturesScrollPane, new GBC(1,1).setWeight(0.9,1).setFill(GBC.BOTH).setInsets(10,10,50,10));

        loadPictureCatalog();
    }

    private void handleChoiceButton(ActionEvent event) {
        File imageFile = WindowUtil.selectFile(this, "jpg", "png");
        if (imageFile != null) {
            choosePicture(imageFile);
        }
    }

    private void choosePicture(File pictureFile) {
        window.show(AppWindow.CREATOR_PANEL);
        window.getGameCreatorPanel().setImage(pictureFile);
    }

    public void loadPictureCatalog() {
        List<File> imageFiles = new ArrayList<>();
        try (Stream<Path> files = Files.list(AppEnv.getPictureCatalogDirectory().toPath())) {
            imageFiles = files.filter(path -> path.toString().endsWith(".jpg") || path.toString().endsWith(".png"))
                    .map(path -> new File(path.toUri()))
                    .toList();
        } catch (IOException e) {
            LOG.error("Cannot load the catalog.", e);
        }

        if (imageFiles.isEmpty()) {
            return;
        }

        picturesPanel.setLayout(new GridBagLayout());
        for (int i = 0; i < imageFiles.size(); i++) {
            JLabel label = new JLabel();
            Image image = ImageUtil.readImage(imageFiles.get(i));
            label.setIcon(new ImageIcon(resizeImage(image)));
            label.setBorder(new EmptyBorder(5, 5, 5, 5));
            label.addMouseListener(new HighlightingMouseAdapter(true, false, false));
            final File pictureFile = imageFiles.get(i);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    choosePicture(pictureFile);
                }
            });

            picturesPanel.add(label, new GBC(i % 3, i / 3).setInsets(10));
        }
    }

    public Image resizeImage(Image image) {

        if (image.getWidth(null) > image.getHeight(null)) {
            image = ImageUtil.resize(image, 0, 400, ResizeQuality.HIGH);
            image = ImageUtil.crop(image, 0, (image.getWidth(null) - 400) / 2, 0, (image.getWidth(null) - 400) / 2);
        } else {
            image = ImageUtil.resize(image, 400, 0, ResizeQuality.HIGH);
            image = ImageUtil.crop(image, (image.getHeight(null) - 400) / 2, 0, (image.getHeight(null) - 400) / 2, 0);
        }

        return image;
    }

}
