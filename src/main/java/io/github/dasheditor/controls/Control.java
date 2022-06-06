package io.github.dasheditor.controls;

public class Control {

    private int x;
    private int y;

    public Control(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveRelative(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
