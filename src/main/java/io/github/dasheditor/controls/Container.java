package io.github.dasheditor.controls;

import java.util.ArrayList;

public class Container extends Control {

    private final ArrayList<Control> children;

    public Container(int x, int y) {
        super(x, y);
        children = new ArrayList<>();
    }

    public void addChild(Control control) {
        children.add(control);
    }

    public ArrayList<Control> getChildren() {
        return children;
    }
}
