package com.chrishaen.keepitin;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor{
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.W) 
			Game.playerBody.setAngularVelocity(1f);
		if(keycode == Input.Keys.A) 
			Game.playerBody.setLinearVelocity(-5f, Game.playerBody.getLinearVelocity().y);
		if(keycode == Input.Keys.S) 
			Game.playerBody.setLinearVelocity(Game.playerBody.getLinearVelocity().x, -5f);
		if(keycode == Input.Keys.D) 
			Game.playerBody.setLinearVelocity(5f, Game.playerBody.getLinearVelocity().y);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.W) 
			Game.playerBody.setLinearVelocity(Game.playerBody.getLinearVelocity().x, 0);
		if(keycode == Input.Keys.A) 
			Game.playerBody.setLinearVelocity(0, Game.playerBody.getLinearVelocity().y);
		if(keycode == Input.Keys.S) 
			Game.playerBody.setLinearVelocity(Game.playerBody.getLinearVelocity().x, 0);
		if(keycode == Input.Keys.D) 
			Game.playerBody.setLinearVelocity(0, Game.playerBody.getLinearVelocity().y);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}

