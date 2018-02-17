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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
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
	float bodyWidth = 7.5f;
	float bodyHeight = 7.5f;	
	static float bodyPosX = 9;
	static float bodyPosY = 16;
	
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
		
		//	Player Texture and Rectangle TODO round corners on paddle
		playerImage = new Texture(Gdx.files.internal("paddle.png"));
		
		//	Box2d World with no gravity
		world = new World(new Vector2(0, 0), true); 
		
		//	Box2d Renderer
		debugRenderer = new Box2DDebugRenderer();
		
		//	Box2d Body Definition for player
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(bodyPosX, bodyPosY);
		
		playerBox = new PolygonShape();  
		playerBox.setAsBox(bodyWidth, bodyHeight);
		
		// Box2d create player body 
		playerBody = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = playerBox;
		playerFixture = playerBody.createFixture(fixtureDef);
		playerBody.setUserData(playerImage);
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
		batch.draw((Texture) playerBody.getUserData(), playerBody.getPosition().x-bodyWidth, playerBody.getPosition().y-bodyHeight, 7.5f,7.5f, bodyWidth*2f, bodyHeight*2f, 1, 1, ((playerBody.getAngle())*180f)/(float)Math.PI, 0,0,3000,3000, false,false);
		
		batch.end();
		
		//	Box2d render update
		//debugRenderer.render(world, camera.combined);
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
