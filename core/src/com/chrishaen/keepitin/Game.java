package com.chrishaen.keepitin;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter implements ApplicationListener{
	
	//	Camera/Rendering
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	
	//	World Dimensions
	final float WORLD_WIDTH = 18;
	final float WORLD_HEIGHT = 32;
	
	//	Assets
	Sprite ground;
	private Texture playerImage;
	
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
	float ballRadius = 7.5f;	
	static float ballPosX = 9;
	static float ballPosY = 16;
	
	//	Creates Input Processor from class
	MyInputProcessor inputProcessor = new MyInputProcessor();
	
	@Override
	public void create () {
		//	Background Setup
		batch = new SpriteBatch();
		ground = new Sprite(new Texture(Gdx.files.internal("testBackground.png")));
		ground.setPosition(0, 0);
		ground.setSize(WORLD_WIDTH , WORLD_HEIGHT);
		
		//	Camera and Viewport Setup, Native aspect ratio is 16:9 otherwise black bars are added
		camera = new OrthographicCamera();
		float aspectRatio = (float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
		viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();
		camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);
		
		//	Box2d World with no gravity
		world = new World(new Vector2(0, -50), true); 
		
		//	Box2d Renderer
		debugRenderer = new Box2DDebugRenderer();
		
		//	Player Texture
		playerImage = new Texture(Gdx.files.internal("paddle.png"));
		//	Box2d Body Definition for player
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(playerPosX, playerPosY);
		
		//	OLD HITBOX CODECreating hit box for paddle
		//playerBox = new PolygonShape();  
		/*Vector2[] vertices = new Vector2[7];
        vertices[0] = new Vector2(-5.6f, -4.75f);
		vertices[1] = new Vector2(-5.65f, -5f);
		vertices[2] = new Vector2(-2.55f, -7f);
		vertices[3] = new Vector2(2.55f, -7f);
		vertices[4] = new Vector2(5.65f, -5f);
		vertices[5] = new Vector2(5.6f, -4.75f);
		vertices[6] = new Vector2(0f, -2f);*/
		//playerBox.set(vertices);
		// Box2d create player body 
		//fixtureDef.shape = playerBox;
		//playerFixture = playerBody.createFixture(fixtureDef);
		
		playerBody = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		playerBody.setUserData(playerImage);
		
		Array<Vector2> vertices = new Array<Vector2>();
		vertices.add(new Vector2(-5.6f, -4.75f));
		vertices.add(new Vector2(-5.65f, -5f));
		vertices.add(new Vector2(-2.55f, -7f));
		vertices.add(new Vector2(2.55f, -7f));
		vertices.add(new Vector2(5.65f, -5f));
		vertices.add(new Vector2(5.6f, -4.75f));
		vertices.add(new Vector2(0f, -6f));
		Box2DSeparator.separate(playerBody, fixtureDef, vertices, 30f);
		
		
		//	Ball Box2d
		BodyDef ballDef = new BodyDef();
		ballDef.type = BodyType.DynamicBody;
		ballDef.position.set(ballPosX, ballPosY);
		ballBox = new CircleShape();
		ballBox.setRadius(1f);
		ballBody = world.createBody(ballDef);
		FixtureDef ballFixtureDef = new FixtureDef();
		ballFixtureDef.shape = ballBox;
		ballFixture = ballBody.createFixture(ballFixtureDef);
		//ballBody.setUserData(playerImage);
	}

	@Override
	public void render () {
		//	Sets input 
		Gdx.input.setInputProcessor(inputProcessor);
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//	Render Background
		ground.draw(batch);
		
		//	Render Player
		batch.draw((Texture) playerBody.getUserData(), playerBody.getPosition().x-playerWidth, playerBody.getPosition().y-playerHeight, 7.5f,7.5f, playerWidth*2f, playerHeight*2f, 1, 1, ((playerBody.getAngle())*180f)/(float)Math.PI, 0,0,3000,3000, false,false);
		
		batch.end();
		
		//	Box2d render update
		debugRenderer.render(world, camera.combined);
		world.step(1/60f, 6, 6);
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

}
