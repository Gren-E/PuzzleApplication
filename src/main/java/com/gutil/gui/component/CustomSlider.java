package com.gutil.gui.component;

import javax.swing.JComponent;
import javax.swing.JLabel;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CustomSlider extends JComponent {

    private int value;
    private float position;
    private final int minimumValue;
    private final int maximumValue;
    private ActionListener actionListener;

    private JLabel display;
    private boolean highlighted;
    private Color highlightColor;

    public CustomSlider(int min, int max, int initialValue) {

        minimumValue = min;
        maximumValue = max;

        int range = maximumValue - minimumValue;

        value = initialValue;
        if (initialValue > minimumValue) {
            position = (float) (initialValue - minimumValue) / range;
        } else {
            position = minimumValue;
        }

        display = new JLabel(String.valueOf(value), SwingConstants.CENTER);
        display.setFont(new Font("Pricedown Bl", Font.PLAIN, 16));
        display.setForeground(Color.white);

        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                highlighted = true;
                if (highlightColor != null) repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                highlighted = false;
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if(SwingUtilities.isLeftMouseButton(e) && (e.getX() >= 0 && e.getX() <= getWidth())) {
                    position = (float) e.getX() / getWidth();
                    repaint();
                    value = (int) (position * range) + minimumValue;
                    display.setText(String.valueOf(value));

                    if(actionListener != null) {
                        actionListener.actionPerformed(null);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        highlightColor = new Color(220,120,120);

        int width = getWidth();
        int height = getHeight();
        if(width <= 0 || height <= 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Polygon rectangle1 = new Polygon(new int[] {0, 0, (int)(position * width), (int)(position * width)}, new int[] {0, height, height, 0}, 4);
        Polygon rectangle2 = new Polygon(new int[] {0, 0, width, width}, new int[] {0, height, height, 0}, 4);

        g2.setColor(new Color(120,50,50));
        g2.fillPolygon(rectangle1);

        if (highlighted && highlightColor != null) {
            g2.setColor(highlightColor);
            g2.setStroke(new BasicStroke(7));
        } else {
            g2.setColor(getBackground());
            g2.setStroke(new BasicStroke(5));
        }

        g2.drawPolygon(rectangle2);
    }

    public int getValue() {
        return value;
    }

    public void setActionListener(ActionListener action) {
        this.actionListener = action;
    }

}
