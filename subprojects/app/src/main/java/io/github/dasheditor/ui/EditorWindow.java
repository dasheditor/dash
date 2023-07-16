package io.github.dasheditor.ui;

import io.github.dasheditor.model.EventNode;
import io.github.dasheditor.model.Graph;
import io.github.dasheditor.ui.component.GraphEditor;

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

        Graph graph = new Graph();
        EventNode event = new EventNode("org/bukkit/event/player/PlayerJoinEvent");
        graph.addNode(event);

        frame.getContentPane().add(new GraphEditor(graph));
    }

    public void run() {
        // frame.setTitle("Dash Editor");
        frame.setVisible(true);
    }
}
