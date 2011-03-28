package com.emptyyourmind.activites;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.emptyyourmind.sprites.Jet;
import com.emptyyourmind.utils.JetStrategyUtil;

/**
 * @author Self-Less
 *
 */
public class Main extends BaseGameActivity implements IOnSceneTouchListener
{

	private static final int NUM_OF_LAYERS = 1;
	private static final int CELL_SIDE_LENGTH = 60;
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private static final int NUM_OF_HORIZONTAL_CELLS = CAMERA_WIDTH / CELL_SIDE_LENGTH;
	private static final int NUM_OF_VERTICAL_CELLS = CAMERA_HEIGHT / CELL_SIDE_LENGTH;

	private Camera camera;
	private Texture texture;
	private Texture autoParallaxBackgroundTexturePlayer;
	private Texture autoParallaxBackgroundTextureEnemy;
	private TextureRegion textureRegion;
	private TextureRegion textureRegionPin;
	private TextureRegion textureRegionCross;
	private TextureRegion parallaxLayerBackPlayer;
	private TextureRegion parallaxLayerMiddlePlayer;
	private TextureRegion parallaxLayerBackEnemy;
	private TextureRegion parallaxLayerMiddleEnemy;
	private Scene scene;
	private Jet jet;
	private Sprite pin;
	private Sprite crossFire;
	private static final int INIT_XY_SPRITE = -600;
	private int[] target;

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
		texture = new Texture(512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegionFactory.setAssetBasePath("gfx/");

		textureRegion = TextureRegionFactory.createFromAsset(texture, this, "jet.png", 0, 0);
		textureRegionCross = TextureRegionFactory.createFromAsset(texture, this, "cross.png", 301, 0);
		textureRegionPin = TextureRegionFactory.createFromAsset(texture, this, "pin.png", 361, 0);
		autoParallaxBackgroundTexturePlayer = new Texture(1024, 1024, TextureOptions.DEFAULT);
		autoParallaxBackgroundTextureEnemy = new Texture(1024, 1024, TextureOptions.DEFAULT);
		
		parallaxLayerBackPlayer = TextureRegionFactory.createFromAsset(autoParallaxBackgroundTexturePlayer, this, "bg.png", 0, 0);
		parallaxLayerMiddlePlayer = TextureRegionFactory.createFromAsset(autoParallaxBackgroundTexturePlayer, this, "cloud.png", 0, 0);
		
		parallaxLayerBackEnemy = parallaxLayerBackPlayer.clone();
		parallaxLayerMiddleEnemy = parallaxLayerMiddlePlayer.clone();
		
		mEngine.getTextureManager().loadTextures(texture, autoParallaxBackgroundTexturePlayer, autoParallaxBackgroundTextureEnemy);
	}

	@Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event)
	{
		int[] clickedTarget = target;
		target = JetStrategyUtil.findTargetCellCoordinates(event.getX(), event.getY(), NUM_OF_HORIZONTAL_CELLS, NUM_OF_VERTICAL_CELLS, CELL_SIDE_LENGTH);
		if(!isAnyControlsInTheArea())
		{
			pin.setPosition(target[0] - CELL_SIDE_LENGTH, target[1]);
			crossFire.setPosition(target[0] + CELL_SIDE_LENGTH, target[1]);
			pin.setVisible(true);
			crossFire.setVisible(true);
		}
		else
		{
			moveJet(clickedTarget);
			pin.setVisible(false);
			crossFire.setVisible(false);
		}
		return false;
	}

	private boolean isAnyControlsInTheArea()
	{
		if(target[0] == pin.getX() && target[1] == pin.getY())
		{
			return true;
		}
		else if (target[0] == crossFire.getX() && target[1] == crossFire.getY())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void moveJet(int[] target)
	{
		IEntityModifier moveToModifier = jet.moveTo(target);
		if(moveToModifier != null)
		{
			jet.registerEntityModifier(moveToModifier);
		}
	}

	@Override
	public Scene onLoadScene()
	{
		final AutoParallaxBackground autoParallaxBackgroundPlayer = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - parallaxLayerBackPlayer.getHeight(), parallaxLayerBackPlayer)));
		autoParallaxBackgroundPlayer.attachParallaxEntity(new ParallaxEntity(5.0f, new Sprite(0,  CAMERA_HEIGHT / 2, parallaxLayerMiddlePlayer)));
		
		scene = new Scene(NUM_OF_LAYERS);
		scene.setBackground(autoParallaxBackgroundPlayer);
		scene.setOnSceneTouchListener(this);
		
		drawSystem(scene);
		jet = new Jet(300f, 240f, textureRegion, Jet.JET54_REFERENCE_POINT_UP, CELL_SIDE_LENGTH);
		pin = new Sprite(INIT_XY_SPRITE, INIT_XY_SPRITE, textureRegionPin);
		crossFire = new Sprite(INIT_XY_SPRITE, INIT_XY_SPRITE, textureRegionCross);
		scene.setOnSceneTouchListener(this);
		scene.registerTouchArea(pin);
		scene.registerTouchArea(crossFire);
		scene.getLastChild().attachChild(jet);
		scene.getLastChild().attachChild(pin);
		scene.getLastChild().attachChild(crossFire);
		return scene;
	}

	@SuppressWarnings("unused")
	private void switchToEnemyBackground(Scene scene)
	{
		final AutoParallaxBackground autoParallaxBackgroundEnemy = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackgroundEnemy.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - parallaxLayerBackEnemy.getHeight(), parallaxLayerBackEnemy)));
		autoParallaxBackgroundEnemy.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0,  CAMERA_HEIGHT / 2, parallaxLayerMiddleEnemy)));
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
	
	private IEntity drawSystem(final Scene scene)
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