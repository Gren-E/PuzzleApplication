package com.pa.view.game;

import com.pa.view.AppWindow;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanelConsole extends JPanel {

    private final GamePanel gamePanel;

    private final JLabel progressLabel;
    private final JLabel timeLabel;

    public GamePanelConsole(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setBackground(new Color(50, 50, 50));

        JButton regularize = new JButton("Regularize Pieces");
        regularize.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegularizeButton();
            }
        });

        JButton exit = new JButton("Exit");
        exit.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExitButton();
            }
        });

        progressLabel = new JLabel("PROGRESS", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        progressLabel.setForeground(Color.WHITE);
        progressLabel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
        timeLabel = new JLabel("TIME", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 85));

        add(regularize);
        add(progressLabel);
        add(timeLabel);
        add(exit);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateLabels();
                gamePanel.checkWinCondition();
            }
        }, 1_000, 200);
    }

    public void updateLabels() {
        int setPieces = gamePanel.getPuzzleController().countSetPieces();
        int allPieces = gamePanel.getPuzzleController().countPieces();
        progressLabel.setText(setPieces + " / " + allPieces);

        long millisecondsPassed = gamePanel.getPuzzleController().getTimeSpendInMillis();
        long minutes = millisecondsPassed / 1000 / 60;
        long seconds = millisecondsPassed / 1000 % 60;
        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private void handleRegularizeButton() {
        gamePanel.regularize();
    }

    private void handleExitButton() {
        gamePanel.getWindow().show(AppWindow.CREATOR_PANEL);
    }

}
