package com.pa;

import com.pa.gui.AppWindow;

import java.awt.EventQueue;

public class AppInitializer {

    public static void main(String... args) {
        EventQueue.invokeLater(() -> {
            AppWindow window = new AppWindow();
        });
    }

}
