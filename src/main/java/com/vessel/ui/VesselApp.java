package com.vessel.ui;

import com.vessel.cards.Card;
import com.vessel.cards.Deck;
import com.vessel.combat.CombatEncounter;
import com.vessel.dungeon.DungeonBuilder;
import com.vessel.dungeon.Room;
import com.vessel.model.Enemy;
import com.vessel.model.Hero;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

/**
 * First playable prototype: walks the hero through the dungeon's rooms in order,
 * one fight per room. The UI is built in code for now; it only reads game state and
 * calls into {@link CombatEncounter}, so the rules stay out of the view.
 */
public class VesselApp extends Application {
    private final Random random = new Random();

    private Hero hero;
    private List<Room> rooms;
    private int roomIndex;
    private CombatEncounter combat;

    private final Label roomLabel = new Label();
    private final Label heroLabel = new Label();
    private final Label enemyLabel = new Label();
    private final Label intentLabel = new Label();
    private final HBox handBox = new HBox(8);
    private final HBox actionBox = new HBox(8);
    private final TextArea log = new TextArea();

    @Override
    public void start(Stage stage) {
        roomLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        enemyLabel.setStyle("-fx-font-size: 16px;");
        log.setEditable(false);
        log.setWrapText(true);
        log.setPrefRowCount(8);
        handBox.setAlignment(Pos.CENTER);
        actionBox.setAlignment(Pos.CENTER);

        VBox top = new VBox(6, roomLabel, heroLabel);
        VBox center = new VBox(6, enemyLabel, intentLabel);
        center.setAlignment(Pos.CENTER);
        VBox bottom = new VBox(10, handBox, actionBox, log);

        BorderPane root = new BorderPane(center, top, null, bottom, null);
        root.setPadding(new Insets(16));

        stage.setTitle("Vessel");
        stage.setScene(new Scene(root, 900, 600));
        stage.show();

        newRun();
    }

    private void newRun() {
        hero = new Hero("The Vessel", 60, Deck.starter(random));
        rooms = DungeonBuilder.buildDefault().getRooms();
        roomIndex = 0;
        log.clear();
        write("You wake in the dark. Somewhere above is the way out.");
        enterRoom();
    }

    private void enterRoom() {
        Room room = rooms.get(roomIndex);
        if (room.isExit()) {
            combat = null;
            write("You climb into the light. You escaped... but how much of you is left? Humanity: "
                    + hero.getHumanity());
            showEndScreen("ESCAPED");
            return;
        }
        combat = new CombatEncounter(hero, room.getEnemy());
        combat.start();
        write("--- " + room.getName() + ": a " + room.getEnemy().getName() + " appears! ---");
        refresh();
    }

    private void refresh() {
        Room room = rooms.get(roomIndex);
        Deck deck = hero.getDeck();
        roomLabel.setText(room.getName() + "   (room " + (roomIndex + 1) + " of " + rooms.size() + ")");
        heroLabel.setText(String.format("%s   HP %d/%d   Block %d   Energy %d/%d   Humanity %d   Draw %d   Discard %d",
                hero.getName(), hero.getHealth(), hero.getMaxHealth(), hero.getBlock(),
                combat.getEnergy(), Hero.ENERGY_PER_TURN, hero.getHumanity(),
                deck.getDrawPileSize(), deck.getDiscardPileSize()));

        Enemy enemy = combat.getEnemy();
        enemyLabel.setText(enemy.getName() + "   HP " + enemy.getHealth() + "/" + enemy.getMaxHealth()
                + "   Block " + enemy.getBlock());

        handBox.getChildren().clear();
        actionBox.getChildren().clear();

        switch (combat.getOutcome()) {
            case IN_PROGRESS -> {
                intentLabel.setText("Intent: " + enemy.getIntent());
                for (Card card : deck.getHand()) {
                    handBox.getChildren().add(cardButton(card));
                }
                actionBox.getChildren().add(button("End Turn", () -> write(combat.endTurn())));
            }
            case VICTORY -> {
                intentLabel.setText("The " + enemy.getName() + " falls.");
                actionBox.getChildren().addAll(
                        button("Possess (-" + enemy.getHumanityCost() + " humanity)", this::possessAndContinue),
                        button("Leave the body", this::nextRoom));
            }
            case DEFEAT -> {
                intentLabel.setText("");
                showEndScreen("YOU DIED");
            }
        }
    }

    private Button cardButton(Card card) {
        Button button = new Button(card.getName() + " [" + card.getCost() + "]\n" + card.getDescription());
        button.setWrapText(true);
        button.setPrefSize(150, 80);
        button.setDisable(!combat.canPlay(card));
        button.setOnAction(e -> {
            write(combat.playCard(card));
            refresh();
        });
        return button;
    }

    private Button button(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> {
            action.run();
            if (combat != null) {
                refresh();
            }
        });
        return button;
    }

    private void possessAndContinue() {
        write(combat.possess());
        if (hero.isLost()) {
            write("Nothing of you remains. The Vessel belongs to the dungeon now.");
            combat = null;
            showEndScreen("LOST");
            return;
        }
        nextRoom();
    }

    private void nextRoom() {
        roomIndex++;
        enterRoom();
    }

    private void showEndScreen(String title) {
        roomLabel.setText(title);
        intentLabel.setText("");
        handBox.getChildren().clear();
        actionBox.getChildren().setAll(button("New Run", this::newRun));
    }

    private void write(String line) {
        log.appendText(line + "\n");
    }
}
