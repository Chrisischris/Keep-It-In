package com.chrishaen.keepitin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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
	FreeTypeFontParameter parameter1 = new FreeTypeFontParameter();
	BitmapFont font1;
	
	FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
	BitmapFont font2;
	
	FreeTypeFontParameter parameter3 = new FreeTypeFontParameter();
	BitmapFont font3;
	
	//	Play Button
	private Stage stage;
    private TextureRegion myTextureRegion;
    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button;
    
    private TextureRegion ball1Region;
    private TextureRegionDrawable ball1Drawable;
    private ImageButton ball1Button;
    
    private TextureRegion ball2Region;
    private TextureRegionDrawable ball2Drawable;
    private ImageButton ball2Button;
    
    private TextureRegion ball3Region;
    private TextureRegionDrawable ball3Drawable;
    private ImageButton ball3Button;
    
    private TextureRegion ball4Region;
    private TextureRegionDrawable ball4Drawable;
    private ImageButton ball4Button;
    
    private TextureRegion ball5Region;
    private TextureRegionDrawable ball5Drawable;
    private ImageButton ball5Button;
    
    boolean ball2Owned;
    boolean ball3Owned;
    boolean ball4Owned;
    boolean ball5Owned;
    
    int highScore;
    int points;
    
    Sound click = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
    Sound wrongClick = Gdx.audio.newSound(Gdx.files.internal("wrongClick.wav"));

	long time = System.currentTimeMillis();
	long lastTime = 0;
    
	public MainMenuScreen(final KeepItIn game) {
		this.game = game;

		camera = new OrthographicCamera();
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
		
		ground = new Sprite(new Texture(Gdx.files.internal("testBackground.png")));
		ground.setPosition(0, 0);
		ground.setSize(WORLD_WIDTH , WORLD_HEIGHT);
		
		parameter1.size = 150;
		parameter1.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
		font1 = generator.generateFont(parameter1);
		font1.setColor(0.1765f, 0.1647f, 0.1490f, 1);
		
		parameter2.size = 65;
		parameter2.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890:";
		font2 = generator.generateFont(parameter2);
		font2.setColor(1f, 1f, 1f, 1);
		
		parameter3.size = 40;
		parameter3.characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789,";
		font3 = generator.generateFont(parameter3);
		font3.setColor(0.1765f, 0.1647f, 0.1490f, 1);
		
		stage = new Stage(viewport); //Set up a stage for the ui
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
        
        myTextureRegion = new TextureRegion(new Texture(Gdx.files.internal("play.png")));
        myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        button = new ImageButton(myTexRegionDrawable); 
        stage.addActor(button);
        button.setSize (300, 169);
        button.setPosition(390, 700);
        stage.addActor(button);
        
        ball1Region = new TextureRegion(new Texture(Gdx.files.internal("ball1.png")));
        ball1Drawable = new TextureRegionDrawable(ball1Region);
        ball1Button = new ImageButton(ball1Drawable); 
        stage.addActor(ball1Button);
        ball1Button.setSize (80, 80);
        ball1Button.setPosition(540-340, 500);
        stage.addActor(ball1Button);
        
        ball2Region = new TextureRegion(new Texture(Gdx.files.internal("ball2.png")));
        ball2Drawable = new TextureRegionDrawable(ball2Region);
        ball2Button = new ImageButton(ball2Drawable); 
        stage.addActor(ball2Button);
        ball2Button.setSize (80, 80);
        ball2Button.setPosition(540-190, 500);
        stage.addActor(ball2Button);
        
        ball3Region = new TextureRegion(new Texture(Gdx.files.internal("ball3.png")));
        ball3Drawable = new TextureRegionDrawable(ball3Region);
        ball3Button = new ImageButton(ball3Drawable); 
        stage.addActor(ball3Button);
        ball3Button.setSize (80, 80);
        ball3Button.setPosition(540-40, 500);
        stage.addActor(ball3Button);
        
        ball4Region = new TextureRegion(new Texture(Gdx.files.internal("ball4.png")));
        ball4Drawable = new TextureRegionDrawable(ball4Region);
        ball4Button = new ImageButton(ball4Drawable); 
        stage.addActor(ball4Button);
        ball4Button.setSize (80, 80);
        ball4Button.setPosition(540+110, 500);
        stage.addActor(ball4Button);
        
        ball5Region = new TextureRegion(new Texture(Gdx.files.internal("ball5.png")));
        ball5Drawable = new TextureRegionDrawable(ball5Region);
        ball5Button = new ImageButton(ball5Drawable); 
        stage.addActor(ball5Button);
        ball5Button.setSize (80, 80);
        ball5Button.setPosition(540+260, 500);
        stage.addActor(ball5Button);
        
        ball2Owned = GameScreen.prefs.getBoolean("ball2Owned", false);
        ball3Owned = GameScreen.prefs.getBoolean("ball3Owned", false);
        ball4Owned = GameScreen.prefs.getBoolean("ball4Owned", false);
        ball5Owned = GameScreen.prefs.getBoolean("ball5Owned", false);
        
        highScore = GameScreen.prefs.getInteger("highScore", 0); 
        points = GameScreen.prefs.getInteger("points", 0);       
        //	Reset High score
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
		time = System.currentTimeMillis();

		Gdx.gl.glClearColor(1f, 0.2708f, 0.2235f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		ground.draw(game.batch);
		
		font1.draw(game.batch, "Keep It In", 75, 1500);
		
		font2.draw(game.batch, "Highscore: " + Integer.toString(highScore), 325, 1200);
		font2.draw(game.batch, "Points: " + Integer.toString(points), 375, 1050);
		
		font3.draw(game.batch, "Cost", 10, 450);
		font3.draw(game.batch, "0    100   500  1000 10000", 225, 450);
		
		game.batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
		if (ball1Button.isPressed() && (time - lastTime) > 500) {
			lastTime = System.currentTimeMillis();
			click.play();
			GameScreen.prefs.putInteger("setBall", 1);
			GameScreen.prefs.flush();
			if (KeepItIn.iosBuild == true)
				KeepItIn.requestHandler.showInterstitial();
		}
		if (ball2Button.isPressed() && (time - lastTime) > 500) {
			lastTime = System.currentTimeMillis();
			if(ball2Owned == true) {
				click.play();
				GameScreen.prefs.putInteger("setBall", 2);
				GameScreen.prefs.flush();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}else if(ball2Owned == false && points >= 100) {
				click.play();
				GameScreen.prefs.putInteger("points", points - 100);
				points = GameScreen.prefs.getInteger("points");
				GameScreen.prefs.putBoolean("ball2Owned", true);
				GameScreen.prefs.putInteger("setBall", 2);
				GameScreen.prefs.flush();
			}else {
				wrongClick.play();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}
		}
		if (ball3Button.isPressed() && (time - lastTime) > 500) {
			lastTime = System.currentTimeMillis();
			if(ball3Owned == true) {
				click.play();
				GameScreen.prefs.putInteger("setBall", 3);
				GameScreen.prefs.flush();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}else if(ball3Owned == false && points >= 500) {
				click.play();
				GameScreen.prefs.putInteger("points", points - 500);
				points = GameScreen.prefs.getInteger("points");
				GameScreen.prefs.putBoolean("ball3Owned", true);
				GameScreen.prefs.putInteger("setBall", 3);
				GameScreen.prefs.flush();
			}else {
				wrongClick.play();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}	
		}
		if (ball4Button.isPressed() && (time - lastTime) > 500) {
			lastTime = System.currentTimeMillis();
			if(ball4Owned == true) {
				click.play();
				GameScreen.prefs.putInteger("setBall", 4);
				GameScreen.prefs.flush();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}else if(ball4Owned == false && points >= 1000) {
				click.play();
				GameScreen.prefs.putInteger("points", points - 1000);
				points = GameScreen.prefs.getInteger("points");
				GameScreen.prefs.putBoolean("ball4Owned", true);
				GameScreen.prefs.putInteger("setBall", 4);
				GameScreen.prefs.flush();
			}else {
				wrongClick.play();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}
		}
		if (ball5Button.isPressed() && (time - lastTime) > 500) {
			lastTime = System.currentTimeMillis();
			if(ball5Owned == true) {
				click.play();
				GameScreen.prefs.putInteger("setBall", 5);
				GameScreen.prefs.flush();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}else if(ball5Owned == false && points >= 10000) {
				click.play();
				GameScreen.prefs.putInteger("points", points - 10000);
				points = GameScreen.prefs.getInteger("points");
				GameScreen.prefs.putBoolean("ball5Owned", true);
				GameScreen.prefs.putInteger("setBall", 5);
				GameScreen.prefs.flush();
			}else {
				wrongClick.play();
				if (KeepItIn.iosBuild == true)
					KeepItIn.requestHandler.showInterstitial();
			}	
		}
		if (button.isPressed() && (time - lastTime) > 500) {
			lastTime = System.currentTimeMillis();
			click.play();
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
