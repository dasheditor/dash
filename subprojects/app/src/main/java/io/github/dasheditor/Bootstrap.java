package io.github.dasheditor;

import io.github.dasheditor.ui.EditorWindow;

public class Bootstrap {

    public static void main(String[] args) {
        // Enable hardware acceleration for host platform.
        System.setProperty("sun.java2d." + getHardwareAcceleration(), "true");

        EditorWindow window = new EditorWindow();
        window.run();
    }

    private static String getHardwareAcceleration() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return "d3d";
        if (os.contains("macos")) return "metal";
        return "opengl";
    }
}
