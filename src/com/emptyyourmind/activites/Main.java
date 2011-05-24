package com.emptyyourmind.activites;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.audio.sound.SoundFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
import org.anddev.andengine.entity.modifier.ScaleAtModifier;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;

import com.emptyyourmind.enums.Direction;
import com.emptyyourmind.sprites.ControlIcon;
import com.emptyyourmind.sprites.Jet;
import com.emptyyourmind.utils.JetStrategyUtil;

/**
 * @author Self-Less
 *
 */
@SuppressWarnings("unused")
public class Main extends BaseGameActivity implements IOnSceneTouchListener
{

	private static final String SPRITE_DISTORTED_STAR = "distorted_star.png";
	private static final String SPRTE_PLAYER_ONE = "p1.png";
	private static final String SPRITE_PLAYER_TWO = "p2.png";
	private static final String HUD_HEALTH_BAR_BORDER = "healthbar_border.png";
	private static final String HUD_HEALTH_BAR = "healthbar.png";
	private static final String HUD_VS = "vs.png";
	private static final long SECOND_PER_FRAME_FLAME = 150L;
	private static final String SPRITE_JET = "jet.png";
	private static final String SPRITE_FLAME_RED = "flame_red.png";
	private static final String SPRITE_FLAME_BLUE = "flame_blue.png";
	private static final String SPRITE_FLAME_PURPLE = "flame_purple.png";
	private static final String[] FLAMES = new String[]{SPRITE_FLAME_RED, SPRITE_FLAME_BLUE, SPRITE_FLAME_PURPLE};
	private static final String BG_TORONTO = "toronto.png";
	private static final String BG_TOKYO = "tokyo.png";
	private static final String BG_SHANGHAI = "shanghai.png";
	private static final String[] BG = new String[]{BG_TORONTO, BG_TOKYO, BG_SHANGHAI};
	private static final String SPRITE_PIN_ICON = "pin.png";
	private static final String SPRITE_CROSS_ICON = "cross.png";
	private static final float CLONE_ALPHA = 0.6f;
	private static final float CLONE_END_ALPHA = 0.4f;
	private static final int NUM_OF_LAYERS = 2;
	private static final int CELL_SIDE_LENGTH = 60;
	private static final int NUM_OF_HORIZONTAL_CELLS = JetStrategyUtil.CAMERA_WIDTH / CELL_SIDE_LENGTH;
	private static final int NUM_OF_VERTICAL_CELLS = JetStrategyUtil.CAMERA_HEIGHT / CELL_SIDE_LENGTH;
	private static final int TIME_INTERVAL_TO_UPDATE_BG = 10;

	private Camera camera;
	private Texture textureMain;
	private Texture textureAutoParallaxBackgroundSmallCloud;
	private Texture textureAutoParallaxBackground;
	private Texture textureAutoParallaxBackgroundBigCloud;
	private Texture textureFlame;
	private Texture textureVS;
	private Texture textureJetThumb;
	private TextureRegion textureRegionJet;
	private TextureRegion textureRegionJetClone;
	private TextureRegion textureRegionPin;
	private TextureRegion textureRegionCross;
	private TextureRegion textureRegionParallaxLayerBackLayer;
	private TextureRegion textureRegionParallaxLayerMiddleLayer;
	private TextureRegion textureRegionParallaxLayerFrontLayer;
	private TextureRegion textureRegionParallaxLayerBackEnemy;
	private TextureRegion textureRegionParallaxLayerMiddleEnemy;
	private TextureRegion textureRegionHealthBarBorder;
	private TextureRegion textureRegionVS;
	private TextureRegion textureRegionJetThumb;
	private TiledTextureRegion textureRegionFlame;
	private Scene scene;
	private Jet jet;
	private Jet jetClone;
	private Sprite healthBarBorder;
	private List<ControlIcon> controlIcons = new ArrayList<ControlIcon>(2);
	private ControlIcon pin;
	private ControlIcon crossFire;
	private static final int INIT_XY_SPRITE = -600;
	private static final int LAYER_OBJECTS = 0;
	private static final int LAYER_HUD = 1;
	private int[] target;
	private int  rotationCount;	
	private Direction direction = Direction.UP;
	private TextureRegion textureRegionHealthBar;
	private Sprite healthBar;
	private Sprite healthBarBorder2;
	private Sprite healthBar2;
	private Music musicBackground;
	private Sound soundJetStart;
	private Sound soundClick;
	private Texture texturePlayerIcons;
	private Texture fontTexture;
	private Texture textureDecorations;
	private TextureRegion textureRegionPlayerOneIcon;
	private TextureRegion textureRegionPlayerTwoIcon;
	private TextureRegion textureRegionStar;
	private Font font;
	private ChangeableText text;
	private long startTimeLong = System.currentTimeMillis();
	private long startTimeShort = System.currentTimeMillis();
	private final List<Sprite> STARS_IN_SHORT_TIME_INTERVAL = new ArrayList<Sprite>();  
	private final List<Sprite> STARS_IN_LONG_TIME_INTERVAL = new ArrayList<Sprite>();  
	
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
				.setNeedsSound(true).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources()
	{
		TextureRegionFactory.setAssetBasePath("gfx/");
		SoundFactory.setAssetBasePath("mfx/");
		MusicFactory.setAssetBasePath("mfx/");
		
		texturePlayerIcons = new Texture(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		textureMain = new Texture(1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureFlame = new Texture(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureVS = new Texture(128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureJetThumb = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureDecorations = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegionPlayerTwoIcon = TextureRegionFactory.createFromAsset(texturePlayerIcons, this, SPRITE_PLAYER_TWO, 91, 0);
		textureAutoParallaxBackgroundSmallCloud = new Texture(1024, 512, TextureOptions.DEFAULT);
		textureAutoParallaxBackground = new Texture(1024, 512, TextureOptions.DEFAULT);
		textureAutoParallaxBackgroundBigCloud = new Texture(512, 128, TextureOptions.DEFAULT);
		textureRegionFlame = TextureRegionFactory.createTiledFromAsset(textureFlame, this, FLAMES[new Random().nextInt(FLAMES.length)], 0, 0, 4, 1);
		textureRegionJet = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_JET, 0, 0);
		textureRegionJetClone = textureRegionJet.clone();
		textureRegionCross = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_CROSS_ICON, 301, 0);
		textureRegionPin = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_PIN_ICON, 361, 0);
		textureRegionHealthBarBorder = TextureRegionFactory.createFromAsset(textureMain, this, HUD_HEALTH_BAR_BORDER, 421, 0);
		textureRegionHealthBar = TextureRegionFactory.createFromAsset(textureMain, this, HUD_HEALTH_BAR, 661, 0);
		textureRegionVS = TextureRegionFactory.createFromAsset(textureVS, this, HUD_VS, 0, 0);
		textureRegionPlayerOneIcon = TextureRegionFactory.createFromAsset(texturePlayerIcons, this, SPRTE_PLAYER_ONE, 0, 0);
		textureRegionStar = TextureRegionFactory.createFromAsset(textureDecorations, this, SPRITE_DISTORTED_STAR, 0, 0);
		
		textureRegionParallaxLayerBackLayer = TextureRegionFactory.createFromAsset(textureAutoParallaxBackground, this, BG[new Random().nextInt(BG.length)], 0, 0);
		textureRegionParallaxLayerMiddleLayer = TextureRegionFactory.createFromAsset(textureAutoParallaxBackgroundSmallCloud, this, "cloud.png", 0, 0);
		textureRegionParallaxLayerFrontLayer = TextureRegionFactory.createFromAsset(textureAutoParallaxBackgroundBigCloud, this, "cloud2.png", 0, 0);
		
		textureRegionParallaxLayerBackEnemy = textureRegionParallaxLayerBackLayer.clone();
		textureRegionParallaxLayerMiddleEnemy = textureRegionParallaxLayerMiddleLayer.clone();
		
		mEngine.getTextureManager().loadTextures(textureMain, textureAutoParallaxBackgroundSmallCloud, 
				textureAutoParallaxBackground, textureAutoParallaxBackgroundBigCloud, 
				textureFlame, textureVS, textureJetThumb, texturePlayerIcons, textureDecorations);
		try {
			musicBackground= MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "game.mp3");
			soundClick= SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "click.mp3");
			musicBackground.setLooping(true);
			musicBackground.setVolume(0.4f);
			musicBackground.play();
			soundJetStart = SoundFactory.createSoundFromAsset(mEngine.getSoundManager(), this, "start.mp3");
			soundJetStart.setVolume(0.8f);
		} catch (final IOException e) {
			Debug.e("Error", e);
		}
		
		fontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = new StrokeFont(fontTexture, Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL), 18, true, Color.BLACK, 2, Color.WHITE, false);

		this.mEngine.getTextureManager().loadTexture(fontTexture);
		this.mEngine.getFontManager().loadFont(font);
	}

	@Override
	public Scene onLoadScene()
	{
		final AutoParallaxBackground autoParallaxBackgroundPlayer = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, JetStrategyUtil.CAMERA_HEIGHT - textureRegionParallaxLayerBackLayer.getHeight(), textureRegionParallaxLayerBackLayer)));
		/*autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(3.0f, new Sprite(0,  CAMERA_HEIGHT / 2, textureRegionParallaxLayerMiddleLayer)));
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(1.0f, new Sprite(0,  CAMERA_HEIGHT / 4, textureRegionParallaxLayerFrontLayer)));
		*/
		scene = new Scene(NUM_OF_LAYERS);
		scene.setBackground(autoParallaxBackgroundPlayer);
		scene.setOnSceneTouchListener(this);
		
		final Random random = new Random();
		drawGrid(scene);
		createDistortedStars(random, false);
		createDistortedStars(random, true);
		jet = new Jet(240, 180, textureRegionJet, Jet.JET54_REFERENCE_POINT_UP, CELL_SIDE_LENGTH);
		jet.attachChild(new AnimatedSprite(120, 231, textureRegionFlame).animate(SECOND_PER_FRAME_FLAME));
		createJetClone();
		createControl();
		creatHUDLayer();
		createObjectsLayer();
		
		scene.setOnSceneTouchListener(this);
		scene.registerUpdateHandler(
			new TimerHandler(1 / 20.0f, true, new ITimerCallback()
			{
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler)
				{
					Main.this.text.setText("Time: " + (int)Main.this.mEngine.getSecondsElapsedTotal());
					long now = System.currentTimeMillis();
					if(now - Main.this.startTimeLong >= 25000)
					{
						Main.this.startTimeLong = now;
						showStar(true, random);
					}
					if(now - Main.this.startTimeShort >= 15000)
					{
						Main.this.startTimeShort = now;
						showStar(false, random);
					}
				}
			})
		);
		
		return scene;
	}

	private void createDistortedStars(Random random, boolean starsWithLongTimeInterval)
	{
		List<Sprite> stars = STARS_IN_LONG_TIME_INTERVAL;
		if(!starsWithLongTimeInterval)
		{
			stars = STARS_IN_SHORT_TIME_INTERVAL;
		}
		for(int i=0; i < 60; i++)
		{
			int x = random.nextInt(JetStrategyUtil.CAMERA_WIDTH);
			int y = random.nextInt(JetStrategyUtil.CAMERA_HEIGHT);
			Sprite star = new Sprite(x, y, i==0?textureRegionStar:textureRegionStar.clone());
			star.setVisible(false);
			scene.getFirstChild().attachChild(star);
			stars.add(star);
		}
	}

	private void showStar(boolean starsWithLongTimeInterval, Random random)
	{
		List<Sprite> stars = STARS_IN_LONG_TIME_INTERVAL;
		if(!starsWithLongTimeInterval)
		{
			stars = STARS_IN_SHORT_TIME_INTERVAL;
		}
		for(int i=0; i<stars.size(); i++)
		{
			Sprite star = stars.get(i);
			star.setVisible(true);
			float startScale = random.nextFloat();
			float startAlpha = random.nextFloat();
			AlphaModifier alphaModifier = new AlphaModifier(2f, 0, startAlpha);
			ScaleModifier scaleModifier = new ScaleModifier(4f, 0, startScale);
			if(starsWithLongTimeInterval)
			{
				alphaModifier = new AlphaModifier(4f, 0, startAlpha);
				scaleModifier = new ScaleModifier(6f, 0, startScale);
			}
			ParallelEntityModifier firstparrallelEnitityModifier = new ParallelEntityModifier(alphaModifier, scaleModifier);
			AlphaModifier alphaModifier2 = new AlphaModifier(4f, startAlpha, 0);
			ScaleModifier scaleModifier2 = new ScaleModifier(4f, startScale, 0);
			if(starsWithLongTimeInterval)
			{
				alphaModifier2 = new AlphaModifier(6f, startAlpha, 0);
				scaleModifier2 = new ScaleModifier(6f, startScale, 0);
			}
			ParallelEntityModifier secondParrallelEnitityModifier = new ParallelEntityModifier(alphaModifier2, scaleModifier2);
			star.registerEntityModifier(new SequenceEntityModifier(firstparrallelEnitityModifier, secondParrallelEnitityModifier));
			star.setPosition(random.nextInt(JetStrategyUtil.CAMERA_WIDTH), random.nextInt(JetStrategyUtil.CAMERA_HEIGHT));
		}
	}
	
	private void createObjectsLayer()
	{
		IEntity objectsLayer = scene.getChild(LAYER_OBJECTS);
		objectsLayer.attachChild(jet);
		objectsLayer.attachChild(jetClone);
		objectsLayer.attachChild(pin);
		objectsLayer.attachChild(crossFire);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		ScaleAtModifier scaleModifier = new ScaleAtModifier(1f, 1, 0.75f, 1, 1, 1, 1);
		healthBar.registerEntityModifier(scaleModifier);
		scene.getChild(LAYER_HUD).registerEntityModifier(new MoveModifier(2f, 0, 0, 0, -120));
		return true;
	}

	private void creatHUDLayer()
	{
		IEntity hudLayer = scene.getChild(LAYER_HUD);
//		hudLayer.registerEntityModifier(new MoveModifier(1f, 0, 0, 100, 300));
		healthBarBorder = new Sprite(60, 30, textureRegionHealthBarBorder);
		healthBar = new Sprite(4, 5, textureRegionHealthBar);
		healthBarBorder.attachChild(healthBar);
		hudLayer.attachChild(healthBarBorder);
		healthBarBorder2 = new Sprite(420, 30, textureRegionHealthBarBorder.clone());
		hudLayer.attachChild(healthBarBorder2);
		healthBar2 = new Sprite(4, 5, textureRegionHealthBar.clone());
		healthBarBorder2.attachChild(healthBar2);
		healthBar2.registerEntityModifier(new ScaleAtModifier(0.25f, 1, 0.75f, 1, 1, 1, 1));
		hudLayer.attachChild(new Sprite(304, 20, textureRegionVS));
		hudLayer.attachChild(new Sprite(60, 65, textureRegionPlayerOneIcon));
		hudLayer.attachChild(new Sprite(575, 65, textureRegionPlayerTwoIcon));
		text =  new ChangeableText(310, 0, font, "Time: ", "Time: XXXX".length());
		hudLayer.attachChild(text);
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event)
	{
		ControlIcon controlIcon = JetStrategyUtil.getControlIcon(event.getX(), event.getY(), NUM_OF_HORIZONTAL_CELLS, NUM_OF_VERTICAL_CELLS, CELL_SIDE_LENGTH, controlIcons);
		if(controlIcon == null)
		{
			target = JetStrategyUtil.findTargetCellCoordinates(event.getX(), event.getY(), NUM_OF_HORIZONTAL_CELLS, NUM_OF_VERTICAL_CELLS, CELL_SIDE_LENGTH);
			showControlIcons();
		}
		else
		{
			soundClick.play();
			if(controlIcon.getName().equals(JetStrategyUtil.ICON_MOVE))
			{
				moveJet(target);
			}
			else
			{
				rotateJetClone();
			}
		}
		return false;
	}

	private void showControlIcons()
	{
		resetIconsAndReference();
		pin.setPosition(target[0] - CELL_SIDE_LENGTH, target[1]);
		crossFire.setPosition(target[0] + CELL_SIDE_LENGTH, target[1]);
		jetClone.setPosition(target[0]-2*CELL_SIDE_LENGTH, target[1]);
	}

	private void rotateJetClone()
	{
		rotationCount = ++rotationCount % 4;
		int rotation = 90 * rotationCount;
		direction = Direction.values()[rotationCount];
		jetClone.setRotation(rotation);
		jetClone.setRotationCenter(jetClone.getWidth()/2, CELL_SIDE_LENGTH/2);
	}

	private void resetIconsAndReference()
	{
		pin.setPosition(INIT_XY_SPRITE, INIT_XY_SPRITE);
		crossFire.setPosition(INIT_XY_SPRITE, INIT_XY_SPRITE);
		jetClone.setPosition(INIT_XY_SPRITE, INIT_XY_SPRITE);
		jetClone.reset();
		jetClone.setAlpha(CLONE_ALPHA);
		rotationCount = 0;
		direction = Direction.UP;
	}

	private void moveJet(int[] target)
	{
		soundJetStart.play();
		jet.moveTo(target, direction);
		resetIconsAndReference();
	}

	private void createControl()
	{
		pin = new ControlIcon(INIT_XY_SPRITE, INIT_XY_SPRITE, textureRegionPin, JetStrategyUtil.ICON_MOVE);
		crossFire = new ControlIcon(INIT_XY_SPRITE, INIT_XY_SPRITE, textureRegionCross, JetStrategyUtil.ICON_ROTATE);
		controlIcons.add(pin);
		controlIcons.add(crossFire);
		scene.registerTouchArea(pin);
		scene.registerTouchArea(crossFire);
	}

	private void createJetClone()
	{
		jetClone = new Jet(INIT_XY_SPRITE, INIT_XY_SPRITE, textureRegionJetClone, Jet.JET54_REFERENCE_POINT_UP, CELL_SIDE_LENGTH);
		jetClone.setAlpha(CLONE_ALPHA);
		ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(new AlphaModifier(1f, CLONE_ALPHA, CLONE_END_ALPHA), new ScaleModifier(1f, 1, 0.6f));
		ParallelEntityModifier parallelEntityModifier2 = new ParallelEntityModifier(new AlphaModifier(1f, CLONE_END_ALPHA, CLONE_ALPHA), new ScaleModifier(1f, 0.6f, 1));
		SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(parallelEntityModifier, parallelEntityModifier2);
		jetClone.registerEntityModifier(new LoopEntityModifier(sequenceEntityModifier));
	}

	private void switchToEnemyBackground(Scene scene)
	{
		final AutoParallaxBackground autoParallaxBackgroundEnemy = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundEnemy.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, JetStrategyUtil.CAMERA_HEIGHT - textureRegionParallaxLayerBackEnemy.getHeight(), textureRegionParallaxLayerBackEnemy)));
		autoParallaxBackgroundEnemy.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0,  JetStrategyUtil.CAMERA_HEIGHT / 2, textureRegionParallaxLayerMiddleEnemy)));
		scene.setBackground(autoParallaxBackgroundEnemy);
	}
	
	private void setMissleTarget(float x, float y, IEntity layer)
	{
		int[] cellCoordinates = JetStrategyUtil.findTargetCellCoordinates(x, y, NUM_OF_HORIZONTAL_CELLS, NUM_OF_VERTICAL_CELLS, CELL_SIDE_LENGTH);
		final Rectangle rect = new Rectangle(cellCoordinates[0], cellCoordinates[1], CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
		rect.setColor(0.6f, 0.8f, 0.6f, 0.6f);
		layer.attachChild(rect);
	}
	
	private IEntity drawGrid(final Scene scene)
	{
		final float num_of_vertical_lines = JetStrategyUtil.CAMERA_WIDTH / CELL_SIDE_LENGTH;
		final float num_of_horizontal_lines = JetStrategyUtil.CAMERA_WIDTH / CELL_SIDE_LENGTH;
		
		final IEntity gridLayer = scene.getFirstChild();
		for (int i = 0; i < num_of_vertical_lines; i++)
		{
			final float x1 = i * CELL_SIDE_LENGTH;
			final float x2 = x1;
			final float y1 = 0;
			final float y2 = JetStrategyUtil.CAMERA_WIDTH;

			/*final Line lineLightLeft = new Line(x1-1, y1, x2-1, y2, 1);
			lineLightLeft.setColor(0.2f, 0.4f, 0.4f, 0.2f);*/
			final Line mainLine = new Line(x1, y1, x2, y2, 1);
			mainLine.setColor(0.8f, 0.8f, 0.8f, 0.6f);
			/*final Line lineLightRight = new Line(x1+1, y1, x2+1, y2, 1);
			lineLightRight.setColor(0.2f, 0.4f, 0.4f, 0.2f);*/
//			gridLayer.attachChild(lineLightLeft);
			gridLayer.attachChild(mainLine);
//			gridLayer.attachChild(lineLightRight);
		}
		
		for (int i = 0; i < num_of_horizontal_lines; i++)
		{
			final float x1 = 0;
			final float x2 = JetStrategyUtil.CAMERA_WIDTH;
			final float y1 = i * CELL_SIDE_LENGTH;
			final float y2 = y1;
			
		/*	final Line lineLightTop = new Line(x1, y1-1, x2, y2-1, 1);
			lineLightTop.setColor(0.2f, 0.4f, 0.4f, 0.2f);*/
			final Line mainLine = new Line(x1, y1, x2, y2, 1);
			mainLine.setColor(0.8f, 0.8f, 0.8f, 0.6f);
	/*		final Line lineLightBottom = new Line(x1, y1+1, x2, y2+1, 1);
			lineLightBottom.setColor(0.2f, 0.4f, 0.4f, 0.2f);*/
//			gridLayer.attachChild(lineLightTop);
			gridLayer.attachChild(mainLine);
//			gridLayer.attachChild(lineLightBottom);
		}
		return gridLayer;
	}

}