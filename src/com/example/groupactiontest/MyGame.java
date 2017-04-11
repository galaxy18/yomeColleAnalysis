//http://blog.csdn.net/hjd_love_zzt/article/details/21159153
package com.example.groupactiontest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MyGame implements ApplicationListener {

	TextureAtlas atlas;
	Sprite sprite;
	Animation alienAnimation;
	SpriteBatch batch;
	float statetime = 0;
	
	
	@Override
	public void create() {
		atlas = new TextureAtlas(Gdx.files.internal("pack.atlas"));
		sprite = atlas.createSprite("ALIEN");
		sprite.setSize(480, 320);
		sprite.setPosition(50, 150);
		
		alienAnimation = new Animation(0.25f, atlas.findRegions("ALIEN"));
		
		batch = new SpriteBatch();
		
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		statetime += Gdx.graphics.getDeltaTime();
		
		batch.begin();
		
		sprite.draw(batch);
		
		batch.draw(alienAnimation.getKeyFrame(statetime, true), 0,0,270,150);
		
		batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}