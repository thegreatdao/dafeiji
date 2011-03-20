package com.emptyyourmind;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
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

/**
 * @author Self-Less
 *
 */
public class Main extends BaseGameActivity implements IOnSceneTouchListener
{

	private static final int NUM_OF_LAYERS = 2;
	private static final int CELL_GAP = 60;
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private static final int NUM_OF_HORIZONTAL_CELLS = CAMERA_WIDTH / CELL_GAP;
	private static final int NUM_OF_VERTICAL_CELLS = CAMERA_HEIGHT / CELL_GAP;

	private Camera camera;
	private Texture texture;
	private Texture autoParallaxBackgroundTexture;
	private TextureRegion textureRegion;
	private TextureRegion parallaxLayerBack;
	private TextureRegion parallaxLayerMiddle;
	private Scene scene;
	private Sprite jet;

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
		autoParallaxBackgroundTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		
		parallaxLayerBack = TextureRegionFactory.createFromAsset(autoParallaxBackgroundTexture, this, "bg.png", 0, 0);
		parallaxLayerMiddle = TextureRegionFactory.createFromAsset(autoParallaxBackgroundTexture, this, "cloud.png", 0, 0);
		mEngine.getTextureManager().loadTextures(texture, autoParallaxBackgroundTexture);
	}

	@Override
	public boolean onSceneTouchEvent(Scene scene, TouchEvent event)
	{
		float x = event.getX();
		float y = event.getY();
		IEntity topLayer = scene.getLastChild();
		setMissleTarget(x, y, topLayer);
		final Path path = new Path(3).to(300, 240).to(10, CAMERA_HEIGHT - 240).to(300, 240);
		jet.registerEntityModifier(new PathModifier(3f, path));
		return false;
	}
	
	private void setMissleTarget(float x, float y, IEntity layer)
	{
		int targetX = 0;
		int targetY = 0;
		for(int i = 0; i < NUM_OF_HORIZONTAL_CELLS; i++)
		{
			targetX = i * CELL_GAP;
			if(targetX <= x && targetX + CELL_GAP > x)
			{
				i = NUM_OF_HORIZONTAL_CELLS;
			}
		}
		for(int i = 0; i < NUM_OF_VERTICAL_CELLS; i ++)
		{
			targetY = i * CELL_GAP;
			if(targetY <= y && targetY + CELL_GAP > y)
			{
				i = NUM_OF_VERTICAL_CELLS;
			}
		}
		final Rectangle rect = new Rectangle(targetX, targetY, CELL_GAP, CELL_GAP);
		rect.setColor(0.6f, 0.8f, 0.6f, 0.6f);
		layer.attachChild(rect);
	}

	@Override
	public Scene onLoadScene()
	{
		final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 10);
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, CAMERA_HEIGHT - parallaxLayerBack.getHeight(), parallaxLayerBack)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(5.0f, new Sprite(0,  CAMERA_HEIGHT / 2, parallaxLayerMiddle)));
		
		scene = new Scene(NUM_OF_LAYERS);
		scene.setBackground(autoParallaxBackground);
		scene.setOnSceneTouchListener(this);
		
		final IEntity gridLayer = drawSystem(scene);
		jet = new Sprite(300f, 240f, textureRegion);
//		jet.registerEntityModifier(new RotationModifier(6, 0, 360));
		gridLayer.attachChild(jet);
		return scene;
	}

	private IEntity drawSystem(final Scene scene)
	{
		final float num_of_vertical_lines = CAMERA_WIDTH / CELL_GAP;
		final float num_of_horizontal_lines = CAMERA_WIDTH / CELL_GAP;
		
		final IEntity gridLayer = scene.getFirstChild();
		for (int i = 0; i < num_of_vertical_lines; i++)
		{
			final float x1 = i * CELL_GAP;
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
			final float y1 = i * CELL_GAP;
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