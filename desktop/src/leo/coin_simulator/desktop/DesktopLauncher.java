package leo.coin_simulator.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import leo.coin_simulator.CoinSimulator;
import org.lwjgl.Sys;

import java.util.Random;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//16:9 aspect ratio
		config.width = 1024;
		config.height = 576;
		new LwjglApplication(new CoinSimulator(), config);
	}
}
