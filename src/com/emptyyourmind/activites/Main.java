package com.emptyyourmind.activites;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.AlphaModifier;
import org.anddev.andengine.entity.modifier.LoopEntityModifier;
import org.anddev.andengine.entity.modifier.ParallelEntityModifier;
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
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.emptyyourmind.enums.Direction;
import com.emptyyourmind.sprites.ControlIcon;
import com.emptyyourmind.sprites.Jet;
import com.emptyyourmind.utils.JetStrategyUtil;

/**
 * @author Self-Less
 *
 */
public class Main extends BaseGameActivity implements IOnSceneTouchListener
{

	private static final String HUD_HEALTH_BAR = "health.png";
	private static final String HUD_VS = "vs.png";
	private static final String HUD_JET = "jet_thumb.png";
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
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private static final int NUM_OF_HORIZONTAL_CELLS = CAMERA_WIDTH / CELL_SIDE_LENGTH;
	private static final int NUM_OF_VERTICAL_CELLS = CAMERA_HEIGHT / CELL_SIDE_LENGTH;

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
	private TextureRegion textureRegionHealthBar;
	private TextureRegion textureRegionVS;
	private TextureRegion textureRegionJetThumb;
	private TiledTextureRegion textureRegionFlame;
	private Scene scene;
	private Jet jet;
	private Jet jetClone;
	private Sprite healthBar;
	private List<ControlIcon> controlIcons = new ArrayList<ControlIcon>(2);
	private ControlIcon pin;
	private ControlIcon crossFire;
	private static final int INIT_XY_SPRITE = -600;
	private static final int LAYER_OBJECTS = 0;
	private static final int LAYER_HUD = 1;
	private int[] target;
	private int  rotationCount;	
	private Direction direction = Direction.UP;
	
	@Override
	public void onLoadComplete()
	{
	}

	@Override
	public Engine onLoadEngine()
	{
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera));
	}

	@Override
	public void onLoadResources()
	{
		TextureRegionFactory.setAssetBasePath("gfx/");

		textureMain = new Texture(1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureFlame = new Texture(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureVS = new Texture(128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureJetThumb = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegionFlame = TextureRegionFactory.createTiledFromAsset(textureFlame, this, FLAMES[new Random().nextInt(FLAMES.length)], 0, 0, 4, 1);
		textureRegionJet = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_JET, 0, 0);
		textureRegionJetClone = textureRegionJet.clone();
		textureRegionCross = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_CROSS_ICON, 301, 0);
		textureRegionPin = TextureRegionFactory.createFromAsset(textureMain, this, SPRITE_PIN_ICON, 361, 0);
		textureRegionHealthBar = TextureRegionFactory.createFromAsset(textureMain, this, HUD_HEALTH_BAR, 421, 0);
		textureRegionVS = TextureRegionFactory.createFromAsset(textureVS, this, HUD_VS, 0, 0);
		textureRegionJetThumb = TextureRegionFactory.createFromAsset(textureJetThumb, this, HUD_JET, 0, 0);
		textureAutoParallaxBackgroundSmallCloud = new Texture(1024, 512, TextureOptions.DEFAULT);
		textureAutoParallaxBackground = new Texture(1024, 512, TextureOptions.DEFAULT);
		textureAutoParallaxBackgroundBigCloud = new Texture(512, 128, TextureOptions.DEFAULT);
		
		textureRegionParallaxLayerBackLayer = TextureRegionFactory.createFromAsset(textureAutoParallaxBackground, this, BG[new Random().nextInt(BG.length)], 0, 0);
		textureRegionParallaxLayerMiddleLayer = TextureRegionFactory.createFromAsset(textureAutoParallaxBackgroundSmallCloud, this, "cloud.png", 0, 0);
		textureRegionParallaxLayerFrontLayer = TextureRegionFactory.createFromAsset(textureAutoParallaxBackgroundBigCloud, this, "cloud2.png", 0, 0);
		
		textureRegionParallaxLayerBackEnemy = textureRegionParallaxLayerBackLayer.clone();
		textureRegionParallaxLayerMiddleEnemy = textureRegionParallaxLayerMiddleLayer.clone();
		
		mEngine.getTextureManager().loadTextures(textureMain, textureAutoParallaxBackgroundSmallCloud, 
				textureAutoParallaxBackground, textureAutoParallaxBackgroundBigCloud, textureFlame, textureVS, textureJetThumb);
	}

	@Override
	public Scene onLoadScene()
	{
		final AutoParallaxBackground autoParallaxBackgroundPlayer = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - textureRegionParallaxLayerBackLayer.getHeight(), textureRegionParallaxLayerBackLayer)));
		/*autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(3.0f, new Sprite(0,  CAMERA_HEIGHT / 2, textureRegionParallaxLayerMiddleLayer)));
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(1.0f, new Sprite(0,  CAMERA_HEIGHT / 4, textureRegionParallaxLayerFrontLayer)));
		*/
		scene = new Scene(NUM_OF_LAYERS);
		scene.setBackground(autoParallaxBackgroundPlayer);
		scene.setOnSceneTouchListener(this);
		
		drawGrid(scene);
		jet = new Jet(240, 180, textureRegionJet, Jet.JET54_REFERENCE_POINT_UP, CELL_SIDE_LENGTH);
		jet.attachChild(new AnimatedSprite(120, 231, textureRegionFlame).animate(SECOND_PER_FRAME_FLAME));
		createJetClone();
		createControl();
		scene.setOnSceneTouchListener(this);
		healthBar = new Sprite(60, 30, textureRegionHealthBar);
		IEntity hudLayer = scene.getChild(LAYER_HUD);
		hudLayer.attachChild(new Sprite(420, 30, textureRegionHealthBar.clone()));
		hudLayer.attachChild(healthBar);
		hudLayer.attachChild(new Sprite(304, 20, textureRegionVS));
//		hudLayer.attachChild(new Sprite(10, 20, textureRegionJetThumb));
		IEntity objectsLayer = scene.getChild(LAYER_OBJECTS);
		objectsLayer.attachChild(jet);
		objectsLayer.attachChild(jetClone);
		objectsLayer.attachChild(pin);
		objectsLayer.attachChild(crossFire);
		return scene;
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

	@SuppressWarnings("unused")
	private void switchToEnemyBackground(Scene scene)
	{
		final AutoParallaxBackground autoParallaxBackgroundEnemy = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundEnemy.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - textureRegionParallaxLayerBackEnemy.getHeight(), textureRegionParallaxLayerBackEnemy)));
		autoParallaxBackgroundEnemy.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0,  CAMERA_HEIGHT / 2, textureRegionParallaxLayerMiddleEnemy)));
		scene.setBackground(autoParallaxBackgroundEnemy);
	}
	
	@SuppressWarnings("unused")
	private void setMissleTarget(float x, float y, IEntity layer)
	{
		int[] cellCoordinates = JetStrategyUtil.findTargetCellCoordinates(x, y, NUM_OF_HORIZONTAL_CELLS, NUM_OF_VERTICAL_CELLS, CELL_SIDE_LENGTH);
		final Rectangle rect = new Rectangle(cellCoordinates[0], cellCoordinates[1], CELL_SIDE_LENGTH, CELL_SIDE_LENGTH);
		rect.setColor(0.6f, 0.8f, 0.6f, 0.6f);
		layer.attachChild(rect);
	}
	
	private IEntity drawGrid(final Scene scene)
	{
		final float num_of_vertical_lines = CAMERA_WIDTH / CELL_SIDE_LENGTH;
		final float num_of_horizontal_lines = CAMERA_WIDTH / CELL_SIDE_LENGTH;
		
		final IEntity gridLayer = scene.getFirstChild();
		for (int i = 0; i < num_of_vertical_lines; i++)
		{
			final float x1 = i * CELL_SIDE_LENGTH;
			final float x2 = x1;
			final float y1 = 0;
			final float y2 = CAMERA_WIDTH;

			final Line lineLightLeft = new Line(x1-1, y1, x2-1, y2, 1);
			lineLightLeft.setColor(0.2f, 0.4f, 0.4f, 0.2f);
			final Line mainLine = new Line(x1, y1, x2, y2, 1);
			mainLine.setColor(0.8f, 0.8f, 0.8f);
			final Line lineLightRight = new Line(x1+1, y1, x2+1, y2, 1);
			lineLightRight.setColor(0.2f, 0.4f, 0.4f, 0.2f);
			gridLayer.attachChild(lineLightLeft);
			gridLayer.attachChild(mainLine);
			gridLayer.attachChild(lineLightRight);
		}
		
		for (int i = 0; i < num_of_horizontal_lines; i++)
		{
			final float x1 = 0;
			final float x2 = CAMERA_WIDTH;
			final float y1 = i * CELL_SIDE_LENGTH;
			final float y2 = y1;
			
			final Line lineLightTop = new Line(x1, y1-1, x2, y2-1, 1);
			lineLightTop.setColor(0.2f, 0.4f, 0.4f, 0.2f);
			final Line mainLine = new Line(x1, y1, x2, y2, 1);
			mainLine.setColor(0.8f, 0.8f, 0.8f);
			final Line lineLightBottom = new Line(x1, y1+1, x2, y2+1, 1);
			lineLightBottom.setColor(0.2f, 0.4f, 0.4f, 0.2f);
			gridLayer.attachChild(lineLightTop);
			gridLayer.attachChild(mainLine);
			gridLayer.attachChild(lineLightBottom);
		}
		return gridLayer;
	}

}