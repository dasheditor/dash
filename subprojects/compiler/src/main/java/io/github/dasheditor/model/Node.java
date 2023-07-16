package io.github.dasheditor.model;

public abstract class Node {

    private int positionX;
    private int positionY;
    private String title;

    protected Node(String title) {
        this.title = title;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getTitle() {
        return title;
    }
}
