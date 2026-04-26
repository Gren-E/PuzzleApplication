package com.pa.view;

import com.gutil.gui.component.SimpleScrollBarUI;
import com.gutil.gui.component.button.RoundButton;
import com.gutil.gui.component.button.RoundRectButton;

import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class ComponentFactory {

    public static JScrollPane createVerticalJScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scrollPane.getVerticalScrollBar().setUI(new SimpleScrollBarUI());
        scrollPane.getVerticalScrollBar().setForeground(Color.DARK_GRAY);
        scrollPane.getVerticalScrollBar().setBackground(new Color(0,0,0,0));

        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }

    public static RoundRectButton createStandardAppButton() {
        return createStandardAppButton(null);
    }

    public static RoundRectButton createStandardAppButton(String buttonText) {
        RoundRectButton button = new RoundRectButton(buttonText);
        button.updateButtonColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY);
        button.setFont(new Font("Cambria", Font.BOLD, 16));
        return button;
    }

    public static RoundButton createStandardRoundButton() {
        return createStandardRoundButton(null);
    }

    public static RoundButton createStandardRoundButton(String buttonText) {
        RoundButton button = new RoundButton(buttonText);
        button.updateButtonColors(Color.DARK_GRAY, Color.LIGHT_GRAY, Color.GRAY);
        button.setFont(new Font("Cambria", Font.BOLD, 16));
        return button;
    }

}
