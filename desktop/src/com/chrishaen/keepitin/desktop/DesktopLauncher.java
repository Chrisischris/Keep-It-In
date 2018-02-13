package com.chrishaen.keepitin.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.chrishaen.keepitin.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	    config.title = "Shooter";
	    config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
	    config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
	    config.fullscreen = false;
		new LwjglApplication(new Game(), config);
	}
}
