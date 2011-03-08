package com.emptyyourmind;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

/**
 * @author Hongli.Li
 * 
 */
public class Main extends BaseGameActivity
{

	private static final int CELL_GAP = 40;
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	private Camera camera;
	private Texture mTexture;
	private TextureRegion textureRegion;

	@Override
	public void onLoadComplete()
	{

	}

	@Override
	public Engine onLoadEngine()
	{
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera));
	}

	@Override
	public void onLoadResources()
	{
		mTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		TextureRegionFactory.setAssetBasePath("gfx/");

		/* TextureRegions. */
		textureRegion = TextureRegionFactory.createFromAsset(mTexture, this, "jet.png", 0, 0);
		mEngine.getTextureManager().loadTexture(this.mTexture);
	}

	@Override
	public Scene onLoadScene()
	{
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));

		final float num_of_vertical_lines = CAMERA_WIDTH / CELL_GAP;
		final float num_of_horizontal_lines = CAMERA_WIDTH / CELL_GAP;
		
		final IEntity lastChild = scene.getLastChild();
		for (int i = 0; i < num_of_vertical_lines; i++)
		{
			final float x1 = i * CELL_GAP;
			final float x2 = x1;
			final float y1 = 0;
			final float y2 = CAMERA_WIDTH;

			final Line line = new Line(x1, y1, x2, y2, 1);

			line.setColor(0.2f, 0.2f, 0.2f);
			lastChild.attachChild(line);
		}
		
		for (int i = 0; i < num_of_horizontal_lines; i++)
		{
			final float x1 = 0;
			final float x2 = CAMERA_WIDTH;
			final float y1 = i * CELL_GAP;
			final float y2 = y1;
			
			final Line line = new Line(x1, y1, x2, y2, 1);
			
			line.setColor(0.2f, 0.2f, 0.2f);
			lastChild.attachChild(line);
		}
		
		lastChild.attachChild(new Sprite(200f, 200f, textureRegion));
		return scene;

	}
}