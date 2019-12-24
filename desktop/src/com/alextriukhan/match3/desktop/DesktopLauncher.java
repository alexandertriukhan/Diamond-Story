package com.alextriukhan.match3.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.alextriukhan.match3.DiamondStoryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 2;
		config.title = "Diamond Story";
		int phoneSize = 3;
		config.width = phoneSizes[phoneSize - 1][0];
		config.height = phoneSizes[phoneSize - 1][1];
		new LwjglApplication(new DiamondStoryGame(), config);
	}

	private static int[][] phoneSizes = new int[][] {
			{ 320, 480 },  // iPhone 3gs         // 1
			{ 270, 480 },  // FullHD equivalent  // 2
			{ 540, 960 },  // 2x FullHD          // 3
			{ 270, 860 },  // Ultra Height       // 4
			{ 860, 270 },  // Ultra Width        // 5
	};

}
