package com.emptyyourmind.activites;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.emptyyourmind.utils.JetStrategyUtil;

/**
 * @author Self-Less
 *
 */
public class MainScreen extends BaseGameActivity
{
	private static final String BG_MAIN = "main_screen.png";
	private static final String BUTTON_NEW_GAME = "new_game.png";
	private Camera camera;
	private Scene scene;
	private Texture textureMain;
	private Texture textureButton;
	private TextureRegion textureRegionMain;
	private TextureRegion textureRegionButton;
	
	@Override
	public void onLoadComplete()
	{
	}

	@Override
	public Engine onLoadEngine()
	{
		camera = new Camera(0, 0, JetStrategyUtil.CAMERA_WIDTH, JetStrategyUtil.CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(JetStrategyUtil.CAMERA_WIDTH, JetStrategyUtil.CAMERA_HEIGHT), camera));
	}

	@Override
	public void onLoadResources()
	{
		TextureRegionFactory.setAssetBasePath("gfx/");
		textureMain = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureButton = new Texture(512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegionMain = TextureRegionFactory.createFromAsset(textureMain, this, BG_MAIN, 0, 0);
		textureRegionButton = TextureRegionFactory.createFromAsset(textureButton, this, BUTTON_NEW_GAME, 0, 0);
		
		mEngine.getTextureManager().loadTextures(textureMain, textureButton);
				
	}

	@Override
	public Scene onLoadScene()
	{
		scene = new Scene(1);
		scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureRegionMain)));
		scene.attachChild(new Sprite(200, 160, textureRegionButton));
		scene.attachChild(new Sprite(200, 223, textureRegionButton.clone()));
		scene.attachChild(new Sprite(200, 286, textureRegionButton.clone()));
		scene.attachChild(new Sprite(200, 349, textureRegionButton.clone()));
		return scene;
	}


}