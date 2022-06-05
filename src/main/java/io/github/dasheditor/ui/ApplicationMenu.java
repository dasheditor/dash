package io.github.dasheditor.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ApplicationMenu extends MenuBar {

    private final Menu fileMenu;
    private final Menu editMenu;

    public ApplicationMenu() {
        fileMenu = new Menu("File");
        fileMenu.getItems().addAll(
                createMenuItem("Quit", event -> Platform.exit())
        );

        editMenu = new Menu("Edit");

        getMenus().addAll(fileMenu, editMenu);
    }

    private MenuItem createMenuItem(String text, EventHandler<ActionEvent> handler) {
        MenuItem item = new MenuItem(text);
        item.setOnAction(handler);
        return item;
    }
}
