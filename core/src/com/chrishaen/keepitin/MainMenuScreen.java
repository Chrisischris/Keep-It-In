package com.chrishaen.keepitin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
	
	//	Play Button
	private Stage stage;
    private Texture myTexture;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
	
    int highScore = GameScreen.prefs.getInteger("highScore", 0);
    
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
		font1.setColor(0.1765f, 0.1647f, 0.1490f, 1);
		
		parameter.size = 65;
		parameter.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890:";
		font2 = generator.generateFont(parameter);
		font2.setColor(1f, 1f, 1f, 1);
		
		//	Play Button
		myTexture = new Texture(Gdx.files.internal("play.png"));
        myTextureRegion = new TextureRegion(myTexture);
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable); //Set the button up

        stage = new Stage(viewport); //Set up a stage for the ui
        stage.addActor(button); //Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        stage.addActor(button);
        button.setSize (300, 169);
        button.setPosition(390, 700);
        
        //	Reset Highscore
        //GameScreen.prefs.putInteger("highScore", 0);
        //highScore = GameScreen.prefs.getInteger("highScore", 0);
        //GameScreen.prefs.flush();
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 0.2708f, 0.2235f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		ground.draw(game.batch);
		
		font1.draw(game.batch, "Keep It In", 75, 1500);
		
		font2.draw(game.batch, "Highscore: " + Integer.toString(highScore), 325, 1100);
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		if (button.isPressed()) {
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
