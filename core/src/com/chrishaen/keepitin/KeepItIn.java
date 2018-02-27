package com.chrishaen.keepitin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class KeepItIn extends Game {
	
	static boolean iosBuild = true;
	
  	SpriteBatch batch;
	BitmapFont font;
	static IActivityRequestHandler requestHandler;
	public KeepItIn(IActivityRequestHandler requestHandler) {
		KeepItIn.requestHandler = requestHandler;
	}

	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		
		if (iosBuild == true)
			requestHandler.showBanner();
		
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
