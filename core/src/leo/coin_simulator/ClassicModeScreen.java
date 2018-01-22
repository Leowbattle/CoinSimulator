package leo.coin_simulator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Random;

public class ClassicModeScreen implements Screen {
	public CoinSimulator coinSimulator;

	public Random random;

	public Stage stage;
	public Label headsLabel;
	public Label tailsLabel;

	public PerspectiveCamera camera;
	public Environment environment;

	public ArrayList<ModelInstance> modelInstances;
	boolean coinJustFinishedSpinning = false;
	int coinSpinDegreesLeft = 0;

	long numHeads = 0;
	long numTails = 0;

	public ClassicModeScreen(CoinSimulator coinSimulator) {
		this.coinSimulator = coinSimulator;

		this.random = new Random();

		this.stage = new Stage(new ScreenViewport());

		camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(50, 0, 0);
		camera.lookAt(0, 0, 0);
		camera.near = 1;
		camera.far = 300;
		camera.update();

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


		modelInstances = new ArrayList<ModelInstance>();
		ModelInstance coin = new ModelInstance(this.coinSimulator.assetManager.get("coin.g3db", Model.class));
		coin.transform.rotate(new Vector3(0, 0, 1), 90);
		modelInstances.add(coin);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		Skin skin = new Skin(Gdx.files.internal("skins/sgx/skin/sgx-ui.json"));

		Table table = new Table();
		table.align(Align.topLeft);
		table.setFillParent(true);

		TextButton back = new TextButton("Back", skin);
		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				coinSimulator.setScreen(new MenuScreen(coinSimulator));
			}
		});
		table.add(back).row();

		headsLabel = new Label("Heads: " + String.valueOf(numHeads), skin);
		tailsLabel = new Label("Tails: " + String.valueOf(numTails), skin);

		table.add(headsLabel).row();
		table.add(tailsLabel).row();

		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && coinSpinDegreesLeft < 1) {
			coinSpinDegreesLeft = 360;
			coinJustFinishedSpinning = false;
			if (random.nextInt() % 2 == 0) {
				coinSpinDegreesLeft += 180;
			}
		}
		if (coinSpinDegreesLeft > 0) {
			coinSpinDegreesLeft -= 2;
			modelInstances.get(0).transform.rotate(new Vector3(0, 0, 1), 2);

			coinJustFinishedSpinning = coinSpinDegreesLeft <= 0;
		}
		//System.out.println(modelInstances.get(0).transform.getRotation(new Quaternion()).getPitch());
		if (coinJustFinishedSpinning) {
			coinJustFinishedSpinning = false;
			if (modelInstances.get(0).transform.getRotation(new Quaternion()).getPitch() == 90) {
				tailsLabel.setText("Tails: " + String.valueOf(++numTails));
				System.out.println("Tails");
			} else {
				headsLabel.setText("Heads: " + String.valueOf(++numHeads));
				System.out.println("Heads");
			}
		}

		//System.out.println(Gdx.input.getDeltaX());
		//camera.rotateAround(new Vector3(0, 0, 0), new Vector3(0, Gdx.input.getDeltaX(), 0), 1);

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		coinSimulator.modelBatch.begin(camera);
		coinSimulator.modelBatch.render(modelInstances, environment);
		coinSimulator.modelBatch.end();

		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		/*
		 * I don't know why I need the next two lines of code, but if I don't the camera's position appears to change
		 * whenever you resize the window
		*/
		camera.position.set(50, 0, 0);
		camera.lookAt(0, 0, 0);
		camera.update();

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
