package com.chrishaen.keepitin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen{
	final KeepItIn game;

	//	Camera/Rendering
	private OrthographicCamera camera;
	private Viewport viewport;
	
	//	World Dimensions
	final float WORLD_WIDTH = 1080;
	final float WORLD_HEIGHT = 1920;
	Sprite ground;
	
	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Organo.ttf"));
	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	BitmapFont font12;
	
	public MainMenuScreen(final KeepItIn game) {
		this.game = game;

		camera = new OrthographicCamera();
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
		
		ground = new Sprite(new Texture(Gdx.files.internal("testBackground.png")));
		ground.setPosition(0, 0);
		ground.setSize(WORLD_WIDTH , WORLD_HEIGHT);
		
		parameter.size = 150;
		parameter.characters = "KepItn";
		font12 = generator.generateFont(parameter);
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		ground.draw(game.batch);
		
		font12.setColor(0.45f, 0.42f, 0.38f, 1);
		font12.draw(game.batch, "Keep It In", 75, 1500);
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}	
	}

	@Override
	public void resize(int width, int height) {
		//Windows Resize Update
		viewport.update(width, height);
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		generator.dispose(); 
		
	}

}
