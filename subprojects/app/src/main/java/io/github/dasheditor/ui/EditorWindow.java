package io.github.dasheditor.ui;

import javax.swing.*;

public class EditorWindow {

    private final JFrame frame;

    public EditorWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);

        // TODO: Implement menus...
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("File"));
        menuBar.add(new JMenu("Edit"));
        menuBar.add(new JMenu("View"));
        menuBar.add(new JMenu("Help"));
        frame.setJMenuBar(menuBar);

        frame.getContentPane().add(new GraphEditor());
    }

    public void run() {
        // frame.setTitle("Dash Editor");
        frame.setVisible(true);
    }
}
