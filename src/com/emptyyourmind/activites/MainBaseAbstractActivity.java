package com.emptyyourmind.activites;

import static com.emptyyourmind.utils.JetStrategyUtil.ALPHA_CLONE_END;
import static com.emptyyourmind.utils.JetStrategyUtil.ALPHA_JET_CLONE;
import static com.emptyyourmind.utils.JetStrategyUtil.CELL_SIDE_LENGTH;
import static com.emptyyourmind.utils.JetStrategyUtil.FLAMES;
import static com.emptyyourmind.utils.JetStrategyUtil.INIT_MENU_ITEM_POSITION_Y;
import static com.emptyyourmind.utils.JetStrategyUtil.INIT_POSITION_X_AND_Y_FOR_JETS;
import static com.emptyyourmind.utils.JetStrategyUtil.LAYER_BASE;
import static com.emptyyourmind.utils.JetStrategyUtil.LAYER_HUD;
import static com.emptyyourmind.utils.JetStrategyUtil.LAYER_MAIN_MENU;
import static com.emptyyourmind.utils.JetStrategyUtil.LAYER_OBJECTS;
import static com.emptyyourmind.utils.JetStrategyUtil.NUM_OF_HORIZONTAL_CELLS;
import static com.emptyyourmind.utils.JetStrategyUtil.NUM_OF_LAYERS;
import static com.emptyyourmind.utils.JetStrategyUtil.NUM_OF_VERTICAL_CELLS;
import static com.emptyyourmind.utils.JetStrategyUtil.SECOND_PER_FRAME_FLAME;
import static com.emptyyourmind.utils.JetStrategyUtil.SPACING_MAIN_MENU_ITEM;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_GRID_MENU_ITEM;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_HEALTH_BAR_MENU_ITEM;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_HUD_HEALTH_BAR;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_HUD_HEALTH_BAR_BORDER;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_HUD_VS;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_JET;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_MENU_ITEM_BASE;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_MOVE_ICON;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_PLAYER_TWO;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_ROTATE_ICON;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_SUB_MENU_MENU_ITEM;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRTE_PLAYER_ONE;
import static com.emptyyourmind.utils.JetStrategyUtil.WIDTH_OF_MAIN_MENU_ITEM;
import static com.emptyyourmind.utils.JetStrategyUtil.Y_UPPER_BOUND;

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
import org.anddev.andengine.entity.scene.Scene.IOnAreaTouchListener;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
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
import android.widget.Toast;

import com.emptyyourmind.enums.Direction;
import com.emptyyourmind.sprites.ControlIcon;
import com.emptyyourmind.sprites.Jet;
import com.emptyyourmind.utils.JetStrategyUtil;

/**
 * @author Self-Less
 *
 */
public abstract class MainBaseAbstractActivity extends BaseGameActivity implements IOnSceneTouchListener, IOnAreaTouchListener
{
	private Camera camera;
	protected Texture textureAutoParallaxBackground;
	protected Texture textureSceneSpecificObject;
	protected TextureRegion textureRegionBackground;
	protected TiledTextureRegion animatedTextureRegionSceneSpecificObject;
	private Texture textureMain;
	private Texture textureFlame;
	private Texture textureVS;
	private Texture textureControlRotate;
	private Texture textureControlMove;
	private Texture texturePlayerIcons;
	private Texture textureFont;
	protected Texture textureRandomObject;
	private Texture textureHealthBarMenuItem;
	private Texture textureGridMenuItem;
	private Texture textureSubmenuMenuItem;
	private Texture textureMenu;
	private TextureRegion textureRegionJet;
	private TextureRegion textureRegionJetClone;
	private TextureRegion textureRegionHealthBarBorder;
	private TextureRegion textureRegionVS;
	private TextureRegion textureRegionPlayerOneIcon;
	private TextureRegion textureRegionPlayerTwoIcon;
	protected TextureRegion textureRegionRandomObject;
	private TextureRegion textureRegionHealthBar;
	private TextureRegion textureRegionMenu;
	private TiledTextureRegion animatedTextureRegionRotate;
	private TiledTextureRegion animatedTextureRegionMove;
	private TiledTextureRegion animatedTextureRegionFlame;
	private TiledTextureRegion animatedTextureRegionHealthBarMenuItem;
	private TiledTextureRegion animatedTextureRegionGridMenuItem;
	private TiledTextureRegion animatedTextureRegionSubMenuMenuItem;
	private Scene scene;
	private List<ControlIcon> controlIcons = new ArrayList<ControlIcon>(2);
	private int[] target;
	private int  rotationCount;	
	private Direction direction = Direction.UP;
	private Music musicBackground;
	private Sound soundJetStart;
	private Sound soundClick;
	private Font font;
	private ChangeableText text;
	private long startTimeLong = System.currentTimeMillis();
	private long startTimeShort = System.currentTimeMillis();
	private final List<Sprite> OBJECTS_IN_SHORT_TIME_INTERVAL = new ArrayList<Sprite>();  
	private final List<Sprite> OBJECTS_IN_LONG_TIME_INTERVAL = new ArrayList<Sprite>(); 
	private int menuOptionClickedTime;
	private Sprite spriteHealthBar;
	private Sprite spriteHealthBar2;
	private Sprite spriteHealthBarBorder2;
	private Sprite spriteHealthBarBorder;
	private Jet jet;
	private Jet jetClone;
	private ControlIcon controlRotate;
	private ControlIcon controlMove;
	
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
		textureRegionPlayerTwoIcon = TextureRegionFactory.createFromAsset(texturePlayerIcons, this, SPRITE_PLAYER_TWO, 91, 0);
		textureAutoParallaxBackground = new Texture(1024, 512, TextureOptions.DEFAULT);
		textureControlMove = new Texture(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureControlRotate = new Texture(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureMenu = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureHealthBarMenuItem = new Texture(512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureGridMenuItem = new Texture(512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureSubmenuMenuItem = new Texture(512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		textureRegionJet = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_JET, 0, 0);
		textureRegionJetClone = textureRegionJet.clone();
		textureRegionHealthBarBorder = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_HUD_HEALTH_BAR_BORDER, 421, 0);
		textureRegionHealthBar = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_HUD_HEALTH_BAR, 661, 0);
		textureRegionVS = TextureRegionFactory.createFromAsset(textureVS, this, SPRITE_HUD_VS, 0, 0);
		textureRegionPlayerOneIcon = TextureRegionFactory.createFromAsset(texturePlayerIcons, this, SPRTE_PLAYER_ONE, 0, 0);
		textureRegionMenu = TextureRegionFactory.createFromAsset(textureMenu, this, SPRITE_MENU_ITEM_BASE, 0, 0);
		animatedTextureRegionFlame = TextureRegionFactory.createTiledFromAsset(textureFlame, this, FLAMES[new Random().nextInt(FLAMES.length)], 0, 0, 4, 1);
		animatedTextureRegionRotate = TextureRegionFactory.createTiledFromAsset(textureControlRotate, this, SPRITE_ROTATE_ICON, 0, 0, 3, 1);
		animatedTextureRegionMove = TextureRegionFactory.createTiledFromAsset(textureControlMove, this, SPRITE_MOVE_ICON, 0, 0, 3, 1);
		animatedTextureRegionHealthBarMenuItem = TextureRegionFactory.createTiledFromAsset(textureHealthBarMenuItem, this, SPRITE_HEALTH_BAR_MENU_ITEM, 0, 0, 2, 1);
		animatedTextureRegionGridMenuItem = TextureRegionFactory.createTiledFromAsset(textureGridMenuItem, this, SPRITE_GRID_MENU_ITEM, 0, 0, 2, 1);
		animatedTextureRegionSubMenuMenuItem = TextureRegionFactory.createTiledFromAsset(textureSubmenuMenuItem, this, SPRITE_SUB_MENU_MENU_ITEM, 0, 0, 2, 1);
		
		mEngine.getTextureManager().loadTextures(textureMain, 
				textureAutoParallaxBackground, 
				textureFlame, textureVS, texturePlayerIcons,
				textureControlMove, textureControlRotate, textureMenu, 
				textureHealthBarMenuItem, textureGridMenuItem, textureSubmenuMenuItem);
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
		
		textureFont = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = new StrokeFont(textureFont, Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL), 18, true, Color.BLACK, 2, Color.WHITE, false);

		this.mEngine.getTextureManager().loadTexture(textureFont);
		this.mEngine.getFontManager().loadFont(font);
	}

	@Override
	public Scene onLoadScene()
	{
		scene = new Scene(NUM_OF_LAYERS);
		scene.setOnSceneTouchListener(this);
		
		final Random random = new Random();
		createRandomObjects(random, false);
		createRandomObjects(random, true);
		jet = new Jet(240, 180, textureRegionJet, Jet.JET54_REFERENCE_POINT_UP, CELL_SIDE_LENGTH);
		jet.attachChild(new AnimatedSprite(120, 231, animatedTextureRegionFlame).animate(SECOND_PER_FRAME_FLAME));
		
		createJetClone();
		createControls();
		creatHUDLayer();
		createObjectsLayer();
		createBaseLayer();
		createMenuItems();
		
		scene.setOnSceneTouchListener(this);
		scene.setOnAreaTouchListener(this);
		scene.registerUpdateHandler(
			new TimerHandler(1 / 20.0f, true, new ITimerCallback()
			{
				@Override
				public void onTimePassed(final TimerHandler pTimerHandler)
				{
					MainBaseAbstractActivity.this.text.setText("Time: " + (int)MainBaseAbstractActivity.this.mEngine.getSecondsElapsedTotal());
					long now = System.currentTimeMillis();
					if(now - MainBaseAbstractActivity.this.startTimeLong >= 25000)
					{
						MainBaseAbstractActivity.this.startTimeLong = now;
						showObjects(true, random);
					}
					if(now - MainBaseAbstractActivity.this.startTimeShort >= 15000)
					{
						MainBaseAbstractActivity.this.startTimeShort = now;
						showObjects(false, random);
					}
				}
			})
		);
		final AutoParallaxBackground autoParallaxBackgroundPlayer = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, JetStrategyUtil.CAMERA_HEIGHT - textureRegionBackground.getHeight(), textureRegionBackground)));
		scene.setBackground(autoParallaxBackgroundPlayer);
		return scene;
	}

	private void createMenuItems()
	{
		addMenuItems(3, WIDTH_OF_MAIN_MENU_ITEM, SPACING_MAIN_MENU_ITEM, JetStrategyUtil.CAMERA_WIDTH);
	}
	
	private void addMenuItems(int numOfMenuItems, int widthOfItem, int spacing, int totalAvailableWidth)
	{
		int widthOfAllItems = widthOfItem * numOfMenuItems + (numOfMenuItems - 1) * spacing;
		int halfWidthOfAllItems = widthOfAllItems / 2;
		int startX = totalAvailableWidth / 2 - halfWidthOfAllItems;
		AnimatedSprite menuItemHealthBar = new AnimatedSprite(0, 0, animatedTextureRegionHealthBarMenuItem);
		scene.registerTouchArea(menuItemHealthBar);
		AnimatedSprite menuItemGrid = new AnimatedSprite(0, 0, animatedTextureRegionGridMenuItem);
		scene.registerTouchArea(menuItemGrid);
		AnimatedSprite menuItemSubMenu = new AnimatedSprite(0, 0, animatedTextureRegionSubMenuMenuItem);
		scene.registerTouchArea(menuItemSubMenu);
		AnimatedSprite[] animatedMenuItems = new AnimatedSprite[]{menuItemHealthBar, menuItemGrid, menuItemSubMenu};
		for(int i=0; i<numOfMenuItems; i++)
		{
			Sprite menuItemBase = new Sprite(startX + i * (widthOfItem + spacing), INIT_MENU_ITEM_POSITION_Y, i==0?textureRegionMenu:textureRegionMenu);
			menuItemBase.attachChild(animatedMenuItems[i]);
			scene.getChild(LAYER_MAIN_MENU).attachChild(menuItemBase);
		}
	}
	
	private void createRandomObjects(Random random, boolean starsWithLongTimeInterval)
	{
		List<Sprite> objects = OBJECTS_IN_LONG_TIME_INTERVAL;
		if(!starsWithLongTimeInterval)
		{
			objects = OBJECTS_IN_SHORT_TIME_INTERVAL;
		}
		for(int i=0; i < 60; i++)
		{
			int x = random.nextInt(JetStrategyUtil.CAMERA_WIDTH);
			int y = random.nextInt(Y_UPPER_BOUND);
			Sprite object = new Sprite(x, y, i==0?textureRegionRandomObject:textureRegionRandomObject.clone());
			object.setVisible(false);
			scene.getFirstChild().attachChild(object);
			objects.add(object);
		}
	}

	private void showObjects(boolean starsWithLongTimeInterval, Random random)
	{
		List<Sprite> objects = OBJECTS_IN_LONG_TIME_INTERVAL;
		if(!starsWithLongTimeInterval)
		{
			objects = OBJECTS_IN_SHORT_TIME_INTERVAL;
		}
		for(int i=0; i<objects.size(); i++)
		{
			Sprite object = objects.get(i);
			object.setVisible(true);
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
			object.registerEntityModifier(new SequenceEntityModifier(firstparrallelEnitityModifier, secondParrallelEnitityModifier));
			object.setPosition(random.nextInt(JetStrategyUtil.CAMERA_WIDTH), random.nextInt(Y_UPPER_BOUND));
		}
	}
	
	private void createObjectsLayer()
	{
		IEntity objectsLayer = scene.getChild(LAYER_OBJECTS);
		objectsLayer.attachChild(jet);
		objectsLayer.attachChild(jetClone);
		objectsLayer.attachChild(controlRotate);
		objectsLayer.attachChild(controlMove);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if(menuOptionClickedTime++ % 2 == 0 )
		{
			scene.getChild(LAYER_MAIN_MENU).registerEntityModifier(new MoveModifier(1f, 0, 0, 0, -120));
		}
		else
		{
			scene.getChild(LAYER_MAIN_MENU).registerEntityModifier(new MoveModifier(1f, 0, 0, -120, 0));
		}
		return false;
	}

	private void creatHUDLayer()
	{
		IEntity hudLayer = scene.getChild(LAYER_HUD);
		spriteHealthBarBorder = new Sprite(60, 30, textureRegionHealthBarBorder);
		spriteHealthBar = new Sprite(4, 5, textureRegionHealthBar);
		spriteHealthBarBorder.attachChild(spriteHealthBar);
		hudLayer.attachChild(spriteHealthBarBorder);
		spriteHealthBarBorder2 = new Sprite(420, 30, textureRegionHealthBarBorder.clone());
		hudLayer.attachChild(spriteHealthBarBorder2);
		spriteHealthBar2 = new Sprite(4, 5, textureRegionHealthBar.clone());
		spriteHealthBarBorder2.attachChild(spriteHealthBar2);
		spriteHealthBar2.registerEntityModifier(new ScaleAtModifier(0.25f, 1, 0.75f, 1, 1, 1, 1));
		hudLayer.attachChild(new Sprite(304, 20, textureRegionVS));
		hudLayer.attachChild(new Sprite(60, 65, textureRegionPlayerOneIcon));
		hudLayer.attachChild(new Sprite(575, 65, textureRegionPlayerTwoIcon));
		text =  new ChangeableText(310, 0, font, "Time: ", "Time: XXXX".length());
		hudLayer.attachChild(text);
	}
	
	@Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event)
	{
		return useControl(event);
	}

	private boolean useControl(TouchEvent event)
	{
		if(event.isActionDown()) 
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
				if(controlIcon.getName().equals(JetStrategyUtil.ACTION_MOVE))
				{
					moveJet(target);
				}
				else
				{
					rotateJetClone();
				}
			}
			return true;
		}
		return false;
	}

	private void showControlIcons()
	{
		resetIconsAndReference();
		controlMove.setPosition(target[0] - CELL_SIDE_LENGTH, target[1]);
		controlRotate.setPosition(target[0] + CELL_SIDE_LENGTH, target[1]);
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
		controlRotate.setPosition(INIT_POSITION_X_AND_Y_FOR_JETS, INIT_POSITION_X_AND_Y_FOR_JETS);
		controlMove.setPosition(INIT_POSITION_X_AND_Y_FOR_JETS, INIT_POSITION_X_AND_Y_FOR_JETS);
		jetClone.setPosition(INIT_POSITION_X_AND_Y_FOR_JETS, INIT_POSITION_X_AND_Y_FOR_JETS);
		jetClone.reset();
		jetClone.setAlpha(ALPHA_JET_CLONE);
		rotationCount = 0;
		direction = Direction.UP;
	}

	private void moveJet(int[] target)
	{
		soundJetStart.play();
		jet.moveTo(target, direction);
		resetIconsAndReference();
	}

	private void createControls()
	{
		controlRotate = new ControlIcon(INIT_POSITION_X_AND_Y_FOR_JETS, INIT_POSITION_X_AND_Y_FOR_JETS, animatedTextureRegionRotate, JetStrategyUtil.ACTION_ROTATE);
		controlRotate.animate(300L);
		controlMove = new ControlIcon(INIT_POSITION_X_AND_Y_FOR_JETS, INIT_POSITION_X_AND_Y_FOR_JETS, animatedTextureRegionMove, JetStrategyUtil.ACTION_MOVE);
		controlMove.animate(300L);
		controlIcons.add(controlRotate);
		controlIcons.add(controlMove);
	}

	private void createJetClone()
	{
		jetClone = new Jet(INIT_POSITION_X_AND_Y_FOR_JETS, INIT_POSITION_X_AND_Y_FOR_JETS, textureRegionJetClone, Jet.JET54_REFERENCE_POINT_UP, CELL_SIDE_LENGTH);
		jetClone.setAlpha(ALPHA_JET_CLONE);
		ParallelEntityModifier parallelEntityModifier = new ParallelEntityModifier(new AlphaModifier(1f, ALPHA_JET_CLONE, ALPHA_CLONE_END), new ScaleModifier(1f, 1, 0.6f));
		ParallelEntityModifier parallelEntityModifier2 = new ParallelEntityModifier(new AlphaModifier(1f, ALPHA_CLONE_END, ALPHA_JET_CLONE), new ScaleModifier(1f, 0.6f, 1));
		SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(parallelEntityModifier, parallelEntityModifier2);
		jetClone.registerEntityModifier(new LoopEntityModifier(sequenceEntityModifier));
	}

	@SuppressWarnings("unused")
	private void setMissleTarget(float x, float y, IEntity layer)
	{
		int[] cellCoordinates = JetStrategyUtil.findTargetCellCoordinates(x, y, NUM_OF_HORIZONTAL_CELLS, NUM_OF_VERTICAL_CELLS, CELL_SIDE_LENGTH);
		final Rectangle rect = new Rectangle(cellCoordinates[0], cellCoordinates[1], CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
		rect.setColor(0.6f, 0.8f, 0.6f, 0.6f);
		layer.attachChild(rect);
	}
	
	private IEntity createBaseLayer()
	{
		final IEntity baseLayer = scene.getChild(LAYER_BASE);
		IEntity animatedSprite = getAnimatedSprite();
		if(animatedSprite != null)
		{
			baseLayer.attachChild(animatedSprite);
		}
		final float num_of_vertical_lines = JetStrategyUtil.CAMERA_WIDTH / CELL_SIDE_LENGTH;
		final float num_of_horizontal_lines = JetStrategyUtil.CAMERA_WIDTH / CELL_SIDE_LENGTH;
		for (int i = 0; i < num_of_vertical_lines; i++)
		{
			final float x1 = i * CELL_SIDE_LENGTH;
			final float x2 = x1;
			final float y1 = 0;
			final float y2 = JetStrategyUtil.CAMERA_WIDTH;

			final Line mainLine = new Line(x1, y1, x2, y2, 1);
			mainLine.setColor(0.8f, 0.8f, 0.8f, 0.6f);
			baseLayer.attachChild(mainLine);
		}
		
		for (int i = 0; i < num_of_horizontal_lines; i++)
		{
			final float x1 = 0;
			final float x2 = JetStrategyUtil.CAMERA_WIDTH;
			final float y1 = i * CELL_SIDE_LENGTH;
			final float y2 = y1;
			
			final Line mainLine = new Line(x1, y1, x2, y2, 1);
			mainLine.setColor(0.8f, 0.8f, 0.8f, 0.6f);
			baseLayer.attachChild(mainLine);
		}
		return baseLayer;
	}

	protected abstract IEntity getAnimatedSprite();

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY)
	{
		if(pSceneTouchEvent.isActionDown())
		{
			Toast.makeText(this, "SSSS", Toast.LENGTH_SHORT).show();
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}