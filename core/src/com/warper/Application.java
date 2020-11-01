package com.warper;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import kotlin.Unit;

public class Application extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont bitmapFont;
	Menu mainMenu;
	Stage currentStage;
	BattleField battleField;
	public kotlin.Unit startBattleField ()  {
		battleField = new BattleField(this.bitmapFont);
		currentStage= battleField;
		GestureDetector gd = new GestureDetector(currentStage);
		Gdx.input.setInputProcessor(new InputMultiplexer(gd,currentStage));

		return Unit.INSTANCE;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		bitmapFont = new BitmapFont();
		bitmapFont.getData().setScale(3);
		mainMenu = new Menu(bitmapFont,
				this::startBattleField);
		currentStage = mainMenu;
		Gdx.input.setInputProcessor(currentStage);
	}

	@Override
	public void render () {
		currentStage.draw(this.batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		mainMenu.dispose();

	}


}
