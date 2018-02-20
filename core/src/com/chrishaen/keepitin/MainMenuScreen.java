package com.chrishaen.keepitin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
	BitmapFont font1;
	
	FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Organo.ttf"));
	FreeTypeFontParameter parameter1 = new FreeTypeFontParameter();
	BitmapFont font2;
	
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
		parameter.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		font1 = generator.generateFont(parameter);
		font1.setColor(0.20f, 0.17f, 0.13f, 1);
		
		parameter.size = 65;
		parameter.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		font2 = generator.generateFont(parameter);
		font2.setColor(1f, 1f, 1f, 1);
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
		
		font1.draw(game.batch, "Keep It In", 75, 1500);
		font2.draw(game.batch, "Tap Anywhere to Begin", 70, 900);
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
