package leo.coin_simulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class MenuScreen implements Screen {
	public CoinSimulator coinSimulator;

	public Stage stage;
	public TextButton classicModeButton;
	public TextButton quitButton;

	public MenuScreen(CoinSimulator coinSimulator) {
		this.coinSimulator = coinSimulator;

		this.stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		Skin skin = new Skin(Gdx.files.internal("skins/sgx/skin/sgx-ui.json"));

		Image logo = new Image(new Texture(Gdx.files.internal("logo.png")));
		table.add(logo).row();

		classicModeButton = new TextButton("Classic Mode", skin);
		classicModeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				coinSimulator.setScreen(new ClassicModeScreen(coinSimulator));
			}
		});
		table.add(classicModeButton).row();

		quitButton = new TextButton("Quit", skin);
		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		table.add(quitButton).row();

		Label credits = new Label("Thanks to libGDX: https://libgdx.badlogicgames.com/ \nThanks to Picture To People: http://www.picturetopeople.org/", skin);
		table.add(credits).row();

		Label githubRepo = new Label("You can find this project on GitHub at: https://github.com/Leowbattle/CoinSimulator/", skin);
		table.add(githubRepo).row();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setScreenSize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
