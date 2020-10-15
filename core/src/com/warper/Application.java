package com.warper;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Application extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont bitmapFont;
	Button button;
	Menu mainMenu;
	GlyphLayout glyphLayout;
	@Override
	public void create () {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.getData().setScale(3);
		mainMenu = new Menu(bitmapFont);

		Gdx.input.setInputProcessor(mainMenu);
	}

	@Override
	public void render () {
		batch.begin();
		mainMenu.draw(this.batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		mainMenu.dispose();
	}

}
