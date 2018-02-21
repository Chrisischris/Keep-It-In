package com.chrishaen.keepitin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class KeepItIn extends Game {

  	SpriteBatch batch;
	BitmapFont font;
	IActivityRequestHandler requestHandler;
	public KeepItIn(IActivityRequestHandler requestHandler) {
		this.requestHandler = requestHandler;
	}

	public void create() {
		batch = new SpriteBatch();
		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		
		//Comment out for android build
		//requestHandler.showBanner();
		
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
