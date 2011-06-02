package com.emptyyourmind.activites;

import java.io.IOException;
import java.util.Random;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.scene.background.SpriteBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import android.content.Intent;

import com.emptyyourmind.utils.JetStrategyUtil;

/**
 * @author Self-Less
 *
 */
public class MainScreen extends BaseGameActivity implements IOnAreaTouchListener
{
	private static final String BG_MAIN = "main_screen.png";
	private static final String BUTTON_NEW_GAME = "new_game.png";
	private static final String BUTTON_CONTINUE_GAME = "continue_game.png";
	private static final String BUTTON_SETTINGS = "settings.png";
	private static final String BUTTON_EXIT = "exit.png";
	private static final String SPRITE_JET = "jet_thumb.png";
	private static final String SPRITE_FLAME = "flame_red.png";
	private static final String SPRITE_JET_SHADOW = "jet_thumb_shadow.png";
	private Camera camera;
	private Scene scene;
	private Texture textureMain;
	private Texture textureButtons;
	private TextureRegion textureRegionMain;
	private TiledTextureRegion textureRegionButtonNewGame;
	private AnimatedSprite buttonNewGame;
	private TiledTextureRegion textureRegionButtonContinueGame;
	private AnimatedSprite buttonContinueGame;
	private AnimatedSprite buttonSettings;
	private AnimatedSprite buttonExit;
	private TiledTextureRegion textureRegionButtonSettings;
	private TiledTextureRegion textureRegionButtonExit;
	private Texture textureButtonExit;
	private TextureRegion textureRegionJet;
	private Texture textureJet;
	private Texture textureJetShadow;
	private Texture textureFlame;
	private TiledTextureRegion textureRegionFlame;
	private TextureRegion textureRegionJetShadow;
	private Sound clickSound;
	private Music bGMusic;
	
	@Override
	public void onLoadComplete()
	{
	}

	@Override
	public Engine onLoadEngine()
	{
		camera = new Camera(0, 0, JetStrategyUtil.CAMERA_WIDTH, JetStrategyUtil.CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(JetStrategyUtil.CAMERA_WIDTH, JetStrategyUtil.CAMERA_HEIGHT), camera)
				.setNeedsMusic(true).setNeedsSound(true));
	}

	@Override
	public void onLoadResources()
	{
		TextureRegionFactory.setAssetBasePath("gfx/");
		SoundFactory.setAssetBasePath("mfx/");
		MusicFactory.setAssetBasePath("mfx/");
		textureMain = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureButtons = new Texture(2048, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureButtonExit = new Texture(1024, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureFlame = new Texture(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureJet = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureJetShadow = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegionMain = TextureRegionFactory.createFromAsset(textureMain, this, BG_MAIN, 0, 0);
		textureRegionButtonNewGame = TextureRegionFactory.createTiledFromAsset(textureButtons, this, BUTTON_NEW_GAME, 0, 0, 2, 1);
		textureRegionButtonContinueGame = TextureRegionFactory.createTiledFromAsset(textureButtons, this, BUTTON_CONTINUE_GAME, 569, 0, 2, 1);
		textureRegionButtonSettings = TextureRegionFactory.createTiledFromAsset(textureButtons, this, BUTTON_SETTINGS, 1137, 0, 2, 1);
		textureRegionFlame = TextureRegionFactory.createTiledFromAsset(textureFlame, this, SPRITE_FLAME, 0, 0, 4, 1);
		textureRegionJet = TextureRegionFactory.createFromAsset(textureJet, this, SPRITE_JET, 0, 0);
		textureRegionJetShadow = TextureRegionFactory.createFromAsset(textureJetShadow, this, SPRITE_JET_SHADOW, 0, 0);
		textureRegionButtonExit = TextureRegionFactory.createTiledFromAsset(textureButtonExit, this, BUTTON_EXIT, 0, 0, 2, 1);
		
		mEngine.getTextureManager().loadTextures(textureMain, textureButtons, textureButtonExit, textureJet, textureFlame, textureJetShadow);
		try
		{
			clickSound= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "click.mp3");
			bGMusic = MusicFactory.createMusicFromAsset(getMusicManager(), this, "main.mp3");
			bGMusic.setLooping(true);
			bGMusic.play();
		}
		catch (final IOException e)
		{
			Debug.e("Error", e);
		}
	}
				

	@Override
	public Scene onLoadScene()
	{
		scene = new Scene(1);
		scene.setBackground(new SpriteBackground(new Sprite(0, 0, textureRegionMain)));
		buttonNewGame = new AnimatedSprite(200, 160, textureRegionButtonNewGame);	
		scene.attachChild(buttonNewGame);
		scene.registerTouchArea(buttonNewGame);
		buttonContinueGame = new AnimatedSprite(200, 218, textureRegionButtonContinueGame);
		scene.attachChild(buttonContinueGame);
		scene.registerTouchArea(buttonContinueGame);
		buttonSettings = new AnimatedSprite(200, 276, textureRegionButtonSettings);
		scene.attachChild(buttonSettings);
		scene.registerTouchArea(buttonSettings);
		buttonExit = new AnimatedSprite(200, 334, textureRegionButtonExit);
		scene.attachChild(buttonExit);
		scene.registerTouchArea(buttonExit);
		scene.setOnAreaTouchListener(this);
		createFloatingJet();
		return scene;
	}

	private void createFloatingJet()
	{
		final Sprite spriteJet = new Sprite(0, 0, textureRegionJet);
		AnimatedSprite spriteFlame = new AnimatedSprite(15, 51, textureRegionFlame);
		Sprite spriteJetShadow = new Sprite(20, 20, textureRegionJetShadow);
		spriteJetShadow.setAlpha(0.6f);
		spriteJetShadow.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1f, 1, 0.6f), new ScaleModifier(2f, 0.6f, 1))));
		spriteJet.attachChild(spriteFlame);
		spriteFlame.attachChild(spriteJetShadow);
		spriteFlame.setScale(0.37f, 0.37f);
		spriteFlame.animate(200L);
		spriteJet.setRotation(180);
		final Path path = new Path(5).to(100, 60)
							.to(100, 400)
							.to(500, 400)
							.to(500, 60)
							.to(100, 60);
		spriteJet.registerEntityModifier(new LoopEntityModifier(new PathModifier(20, path, null, new IPathModifierListener() {
			@Override
			public void onWaypointPassed(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
				switch(pWaypointIndex) {
					case 0:
						spriteJet.registerEntityModifier(new RotationModifier(0.4f, 180, 180));
						break;
					case 1:
						spriteJet.registerEntityModifier(new RotationModifier(0.4f, 180, 90));
						break;
					case 2:
						spriteJet.registerEntityModifier(new RotationModifier(0.4f, 90, 0));
						break;
					case 3:
						spriteJet.registerEntityModifier(new RotationModifier(0.4f, 0, -90));
						break;
				}
			}
		})));
		scene.attachChild(spriteJet);
	}

	@Override
	public boolean onAreaTouched(TouchEvent touchEvent, ITouchArea touchArea, float touchAreaLocalX, float touchAreaLocalY)
	{	
		AnimatedSprite currentButton = (AnimatedSprite)touchArea;
		if (touchEvent.isActionDown()) 
		{
			currentButton.setCurrentTileIndex(1, 0);
			clickSound.play();
		}
		if (touchEvent.isActionUp())
		{
			if(currentButton == buttonNewGame)
			{
				bGMusic.pause();
				Class<?>[] activites = new Class<?>[]{BuddhaSceneActivity.class, SkullSceneActivity.class, UFOSceneActivity.class};
				Random random = new Random();
				startActivity(new Intent(this, activites[random.nextInt(activites.length)]));
			}
			currentButton.setCurrentTileIndex(0, 0);
		}
		return true;
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		if(bGMusic != null && !bGMusic.isPlaying())
		{
			bGMusic.resume();
		}
	}

}