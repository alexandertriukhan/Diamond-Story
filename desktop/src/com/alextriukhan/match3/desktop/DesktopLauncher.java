package com.alextriukhan.match3.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.alextriukhan.match3.DiamondStoryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Diamond Story");
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 2); // 2x MSAA
		config.useVsync(true);
		int phoneSize = 3;
		config.setWindowedMode(phoneSizes[phoneSize - 1][0], phoneSizes[phoneSize - 1][1]);
		new Lwjgl3Application(new DiamondStoryGame(), config);
	}

	private static int[][] phoneSizes = new int[][] {
			{ 320, 480 },  // iPhone 3gs         // 1
			{ 270, 480 },  // FullHD equivalent  // 2
			{ 540, 960 },  // 2x FullHD          // 3
			{ 270, 860 },  // Ultra Height       // 4
			{ 860, 270 },  // Ultra Width        // 5
	};

}
