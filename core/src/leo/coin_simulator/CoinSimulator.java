package leo.coin_simulator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class CoinSimulator extends Game {
	public AssetManager assetManager;
	public ModelBatch modelBatch;

	public void loadAssets() {
		assetManager = new AssetManager();
		assetManager.load("coin.g3db", Model.class);
		assetManager.finishLoading();
	}

	@Override
	public void create () {
		modelBatch = new ModelBatch();
		loadAssets();

		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
