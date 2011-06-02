package com.emptyyourmind.activites;

import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_BG_UFO_CITY;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_UFO;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_UFO_FIRE;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

public class UFOSceneActivity extends MainBaseAbstractActivity
{

	@Override
	public void onLoadResources()
	{
		super.onLoadResources();
		textureRandomObject = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureSceneSpecificObject = new Texture(1024, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		animatedTextureRegionSceneSpecificObject = TextureRegionFactory.createTiledFromAsset(textureSceneSpecificObject, this, SPRITE_UFO, 0, 0, 5, 1);
		textureRegionBackground = TextureRegionFactory.createFromAsset(textureAutoParallaxBackground, this, SPRITE_BG_UFO_CITY, 0, 0);
		textureRegionRandomObject = TextureRegionFactory.createFromAsset(textureRandomObject, this, SPRITE_UFO_FIRE, 0, 0);
		mEngine.getTextureManager().loadTextures(textureSceneSpecificObject, textureRandomObject);
	}
	
	@Override
	protected IEntity getAnimatedSprite()
	{
		AnimatedSprite animatedSpriteUFO = new AnimatedSprite(240, 0, animatedTextureRegionSceneSpecificObject);
		animatedSpriteUFO.animate(new long[]{5000L, 6000L, 7000L, 8000L, 9000L}, true);
		return animatedSpriteUFO;
	}

}
