package io.github.dasheditor;

import io.github.dasheditor.ui.EditorWindow;

public class Bootstrap {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        EditorWindow window = new EditorWindow();
        window.run();
    }
}
