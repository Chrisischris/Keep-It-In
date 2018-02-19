package com.chrishaen.keepitin;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener{
	//	Time in MilliSeconds
	long time = System.currentTimeMillis();
	long lastTime = 0;
	@Override
	public void beginContact(Contact contact) {
		time = System.currentTimeMillis();
		if ((time - lastTime) > 10) {
			GameScreen.score += 1;
			lastTime = System.currentTimeMillis();
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
