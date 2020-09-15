package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class Modal {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    public Modal(Player player, GameDatabaseManager gameDatabaseManager, GameState gameState) {
        Stage popupWindow=new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(null);
        Label label1= new Label("Save username " + player.getName() + " + game state?" );

        TextField textField = new TextField();
        textField.setMaxWidth(150);
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        save.setOnAction(e -> {
            if (!gameDatabaseManager.checkPlayerDb(player)) {
                SaveDB.saveDb(player, gameDatabaseManager, gameState);
                player.setName(textField.getText());
           } else {
                alert.setTitle("Confirmation");
                alert.setContentText("Would you like to overwrite the already existing state?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    SaveDB.saveDb(player, gameDatabaseManager, gameState);
                }
            }
            popupWindow.close();
        });
        cancel.setOnAction(e -> popupWindow.close());

        HBox buttons = new HBox();
        buttons.getChildren().addAll(save, cancel);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,textField, buttons);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
}
