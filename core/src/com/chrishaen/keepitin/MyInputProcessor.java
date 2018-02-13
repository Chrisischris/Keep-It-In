package com.chrishaen.keepitin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor{
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.D) 
			Game.playerBody.setAngularVelocity(2f);
		if(keycode == Input.Keys.A) 
			Game.playerBody.setAngularVelocity(-2f);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.D || keycode == Input.Keys.A) 
			Game.playerBody.setAngularVelocity(0f);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(screenX > Gdx.graphics.getWidth()/2)
			Game.playerBody.setAngularVelocity(2f);
		if(screenX < Gdx.graphics.getWidth()/2)
			Game.playerBody.setAngularVelocity(-2f);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Game.playerBody.setAngularVelocity(0f);
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

