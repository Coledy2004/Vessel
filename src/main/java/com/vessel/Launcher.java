package com.vessel;

import com.vessel.ui.VesselApp;
import javafx.application.Application;

/**
 * Entry point. Kept separate from {@link VesselApp} so the game can be started straight
 * from IntelliJ's green run arrow without extra JavaFX module flags.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(VesselApp.class, args);
    }
}
