package com.chrishaen.keepitin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor{
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.D)	{
			GameScreen.playerBody.setAngularVelocity(5f);
		}	
		if(keycode == Input.Keys.A) {
			GameScreen.playerBody.setAngularVelocity(-5f);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.D) {
			GameScreen.playerBody.setAngularVelocity(0f);
		}
		if(keycode == Input.Keys.A) {
			GameScreen.playerBody.setAngularVelocity(0f);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(screenX > Gdx.graphics.getWidth()/2 && screenY > (Gdx.graphics.getHeight()*0.1)) {
			GameScreen.playerBody.setAngularVelocity(5f);
		}
		if(screenX < Gdx.graphics.getWidth()/2 && screenY > (Gdx.graphics.getHeight()*0.1)) {
			GameScreen.playerBody.setAngularVelocity(-5f);
		}
		if(screenY < (Gdx.graphics.getHeight()*0.1)) {
			GameScreen.goMainMenu = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(screenX > Gdx.graphics.getWidth()/2 && screenY < (Gdx.graphics.getHeight()*0.9)) {
			GameScreen.playerBody.setAngularVelocity(0f);
		}
		if(screenX < Gdx.graphics.getWidth()/2 && screenY < (Gdx.graphics.getHeight()*0.9)) {
			GameScreen.playerBody.setAngularVelocity(0f);
		}
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

