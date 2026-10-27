package com.vessel.ui;

import com.vessel.cards.Card;
import com.vessel.cards.Deck;
import com.vessel.combat.CombatEncounter;
import com.vessel.factory.CryptEnemyFactory;
import com.vessel.factory.EnemyFactory;
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
 * First playable prototype: the hero fights a short list of enemies in order.
 * The UI is built in code for now; it only reads game state and calls into
 * {@link CombatEncounter}, so the rules stay out of the view.
 *
 * Used AI to help create this file
 */
public class VesselApp extends Application {
    private final Random random = new Random();

    private Hero hero;
    private List<Enemy> enemies;
    private int enemyIndex;
    private CombatEncounter combat;

    private final Label titleLabel = new Label();
    private final Label heroLabel = new Label();
    private final Label enemyLabel = new Label();
    private final Label intentLabel = new Label();
    private final HBox handBox = new HBox(8);
    private final HBox actionBox = new HBox(8);
    private final TextArea log = new TextArea();

    @Override
    public void start(Stage stage) {
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        enemyLabel.setStyle("-fx-font-size: 16px;");
        log.setEditable(false);
        log.setWrapText(true);
        log.setPrefRowCount(8);
        handBox.setAlignment(Pos.CENTER);
        actionBox.setAlignment(Pos.CENTER);

        VBox top = new VBox(6, titleLabel, heroLabel);
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
        EnemyFactory factory = new CryptEnemyFactory();
        hero = new Hero("The Vessel", 60, Deck.starter(random));
        enemies = List.of(factory.createGrunt(), factory.createElite(), factory.createBoss());
        enemyIndex = 0;
        log.clear();
        write("You wake in the dark. Somewhere above is the way out.");
        startFight();
    }

    private void startFight() {
        if (enemyIndex >= enemies.size()) {
            combat = null;
            write("You climb into the light. You escaped!");
            showEndScreen("ESCAPED");
            return;
        }
        Enemy enemy = enemies.get(enemyIndex);
        combat = new CombatEncounter(hero, enemy);
        combat.start();
        write("--- A " + enemy.getName() + " appears! ---");
        refresh();
    }

    private void refresh() {
        Deck deck = hero.getDeck();
        titleLabel.setText("Fight " + (enemyIndex + 1) + " of " + enemies.size());
        heroLabel.setText(String.format("%s   HP %d/%d   Block %d   Energy %d/%d   Draw %d   Discard %d",
                hero.getName(), hero.getHealth(), hero.getMaxHealth(), hero.getBlock(),
                combat.getEnergy(), Hero.ENERGY_PER_TURN,
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
                        button("Possess", this::possessAndContinue),
                        button("Leave the body", this::nextFight));
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
        nextFight();
    }

    private void nextFight() {
        enemyIndex++;
        startFight();
    }

    private void showEndScreen(String title) {
        titleLabel.setText(title);
        intentLabel.setText("");
        handBox.getChildren().clear();
        actionBox.getChildren().setAll(button("New Run", this::newRun));
    }

    private void write(String line) {
        log.appendText(line + "\n");
    }
}
