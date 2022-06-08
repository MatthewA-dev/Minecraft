package com.matthewadev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.matthewadev.Main;
import com.matthewadev.input.InputHandler;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(61);
		config.setWindowedMode(1080, 720);
		config.setTitle("Minecraft but worse");
		config.setResizable(false);
		new Lwjgl3Application(new Main(), config);
	}
}
