package com.chrishaen.keepitin;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends Game implements Screen{
	final KeepItIn game;
	//	Camera/Rendering
	SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthographicCamera textCamera;
	private Viewport viewport;
	BitmapFont font;
	
	//	World Dimensions
	final float WORLD_WIDTH = 18;
	final float WORLD_HEIGHT = 32;
	
	//	Assets
	Sprite ground;
	private Texture playerImage;
	private Texture ballImage;
	
	// Box 2d world object and renderer
	private World world;
	Box2DDebugRenderer debugRenderer;
	
	//	Box2d Create Player
	PolygonShape playerBox;
	static Body playerBody;
	static Fixture playerFixture;
	float playerWidth = 7.5f;
	float playerHeight = 7.5f;	
	static float playerPosX = 9;
	static float playerPosY = 16;
	
	//	Box2d Create Ball
	CircleShape ballBox;
	static Body ballBody;
	static Fixture ballFixture;
	float ballRadius = 0.5f;	
	static float ballPosX = 9;
	static float ballPosY = 16;
	static float ballVelocity = 12f;
	
	//	Current ball velocity
	Vector2 ballLinearVelocity;
	
	//	True When Main Menu Button Is Hit
	public static boolean goMainMenu = false;
	
	//	Creates Input Processor from class
	MyInputProcessor inputProcessor = new MyInputProcessor();
	ContactListener contactListener = new MyContactListener();
	
	//	Score
	static int score;
	int lastIncrement = 0;
	static Preferences prefs = Gdx.app.getPreferences("My Preferences");
	
	FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Organo.ttf"));
	FreeTypeFontParameter parameter1 = new FreeTypeFontParameter();
	BitmapFont font1;
	
	public GameScreen(final KeepItIn game) {
		this.game = game;
		//	Background Setup
		batch = new SpriteBatch();
		ground = new Sprite(new Texture(Gdx.files.internal("testBackground.png")));
		ground.setPosition(0, 0);
		ground.setSize(WORLD_WIDTH , WORLD_HEIGHT);
		
		//	Camera and Viewport Setup, Native aspect ratio is 16:9 otherwise black bars are added
		camera = new OrthographicCamera();
		textCamera = new OrthographicCamera(1080,1920);
		//float aspectRatio = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
		
		//	Box2d World with no gravity
		world = new World(new Vector2(0, 0), true); 
		
		//	Box2d Renderer
		debugRenderer = new Box2DDebugRenderer();
		
		//	Player Texture
		playerImage = new Texture(Gdx.files.internal("Paddle.png"));
		//	Box2d Body Definition for player
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(playerPosX, playerPosY);		
		playerBody = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.friction = 0f;
		playerBody.setUserData(playerImage);
		
		Array<Vector2> vertices = new Array<Vector2>();
		//	Bottom
		vertices.add(new Vector2(-5.6f, -4.75f));
		vertices.add(new Vector2(-5.65f, -5f));
		vertices.add(new Vector2(-2.55f, -7.5f));
		vertices.add(new Vector2(2.55f, -7.5f));
		vertices.add(new Vector2(5.65f, -5f));
		vertices.add(new Vector2(5.6f, -4.75f));
		//	Top
		vertices.add(new Vector2(5.3f, -4.7f));
		vertices.add(new Vector2(5f, -5f));
		vertices.add(new Vector2(4.7f, -5.3f));
		vertices.add(new Vector2(4.4f, -5.59f));
		vertices.add(new Vector2(4.1f, -5.8f));
		vertices.add(new Vector2(3.8f, -6f));
		vertices.add(new Vector2(3.5f, -6.15f));
		vertices.add(new Vector2(3.2f, -6.3f));
		vertices.add(new Vector2(2.9f, -6.45f));
		vertices.add(new Vector2(2.6f, -6.6f));
		vertices.add(new Vector2(2.3f, -6.75f));
		vertices.add(new Vector2(2f, -6.83f));
		vertices.add(new Vector2(1.7f, -6.9f));
		vertices.add(new Vector2(1.4f, -6.98f));
		vertices.add(new Vector2(1.1f, -7.02f));
		vertices.add(new Vector2(0.8f, -7.08f));
		vertices.add(new Vector2(0.5f, -7.09f));
		vertices.add(new Vector2(0.2f, -7.09f));
		//	Center Point
		vertices.add(new Vector2(0f, -7.1f));
		vertices.add(new Vector2(-0.2f, -7.09f));
		vertices.add(new Vector2(-0.5f, -7.09f));
		vertices.add(new Vector2(-0.8f, -7.08f));
		vertices.add(new Vector2(-1.1f, -7.02f));
		vertices.add(new Vector2(-1.4f, -6.98f));
		vertices.add(new Vector2(-1.7f, -6.9f));
		vertices.add(new Vector2(-2f, -6.83f));
		vertices.add(new Vector2(-2.3f, -6.75f));
		vertices.add(new Vector2(-2.6f, -6.6f));
		vertices.add(new Vector2(-2.9f, -6.45f));
		vertices.add(new Vector2(-3.2f, -6.3f));
		vertices.add(new Vector2(-3.5f, -6.15f));
		vertices.add(new Vector2(-3.8f, -6f));
		vertices.add(new Vector2(-4.1f, -5.8f));
		vertices.add(new Vector2(-4.4f, -5.59f));
		vertices.add(new Vector2(-4.7f, -5.3f));
		vertices.add(new Vector2(-5f, -5f));
		vertices.add(new Vector2(-5.3f, -4.7f));
		//	Converts Concave Shapes to Convex
		Box2DSeparator.separate(playerBody, fixtureDef, vertices, 30f);
		
		//	Ball Texture
		ballImage = new Texture(Gdx.files.internal("ball.png"));
		//	Ball Box2d
		BodyDef ballDef = new BodyDef();
		ballDef.type = BodyType.DynamicBody;
		ballDef.position.set(ballPosX, ballPosY);
		ballBox = new CircleShape();
		ballBox.setRadius(ballRadius);
		ballBody = world.createBody(ballDef);
		FixtureDef ballFixtureDef = new FixtureDef();
		ballFixtureDef.restitution = 1f;
		ballFixtureDef.friction = 0f;
		ballFixtureDef.shape = ballBox;
		ballFixture = ballBody.createFixture(ballFixtureDef);
		ballBody.setUserData(ballImage);
		
		ballBody.setLinearVelocity(0f, -ballVelocity);
		score = 0;
		parameter1.size = 150;
		parameter1.characters = "0123456789";
		font1 = generator1.generateFont(parameter1);
		font1.setColor(0.20f, 0.17f, 0.13f, 1);
	}

	@Override
	public void render (float delta) {
		//	Sets input 
		Gdx.input.setInputProcessor(inputProcessor);
		//	Collision Listener
		world.setContactListener(contactListener);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//	Render Background
		ground.draw(batch);
		
		//	Render Player
		batch.draw((Texture) playerBody.getUserData(), playerBody.getPosition().x-playerWidth, playerBody.getPosition().y-playerHeight, 7.5f,7.5f, playerWidth*2f, playerHeight*2f, 1, 1, ((playerBody.getAngle())*180f)/(float)Math.PI, 0,0,3000,3000, false,false);
		batch.draw((Texture) ballBody.getUserData(), ballBody.getPosition().x-ballRadius, ballBody.getPosition().y-ballRadius, ballRadius*2, ballRadius*2);
		
		//	Render Score
		batch.setProjectionMatrix(textCamera.combined);
		font1.draw(batch, Integer.toString(score), -70, 800);
		batch.end();
		
		//	Increase ball speed
		if (score - lastIncrement == 10) {
			lastIncrement = score;
			ballVelocity += 0.5;
		}
		
		//	Maintains Balls Set Velocity 
		ballLinearVelocity = ballBody.getLinearVelocity();
		if (Math.sqrt(Math.pow(ballLinearVelocity.x, 2) + Math.pow(ballLinearVelocity.y, 2)) != ballVelocity) {
			float theta = (float) Math.atan(Math.abs(ballLinearVelocity.y)/Math.abs(ballLinearVelocity.x));
			float x = (float) (ballVelocity * Math.cos(theta));
			float y = (float) (ballVelocity * Math.sin(theta));
			if (ballLinearVelocity.x < 0) {
				x = -x;
			}
			if (ballLinearVelocity.y < 0) {
				y = -y;
			}
			ballBody.setLinearVelocity(x, y);
		}
		
		//	Box2d render update
		//debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 6);
		
		if(ballBody.getPosition().x > 18 || ballBody.getPosition().x < 0 || ballBody.getPosition().y > 32 || ballBody.getPosition().y < 0) {
			goMainMenu = true;
		}
		if(goMainMenu == true) {
			if(score > prefs.getInteger("highScore", 0)) {
				prefs.putInteger("highScore", score);
				prefs.flush();
			}
			goMainMenu = false;
			game.setScreen(new MainMenuScreen(game));
			dispose();
		}
		
		System.out.println(score);
	}
	
	@Override
	public void resize(int width, int height) {
		//Windows Resize Update
		viewport.update(width, height);
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

}
