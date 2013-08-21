package org.castelodelego.ludum27;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ld27";
		cfg.useGL20 = true;
		cfg.width = Constants.SCREEN_W;
		cfg.height = Constants.SCREEN_H;
		
		new LwjglApplication(new GdxGameMain(), cfg);
	}
}
