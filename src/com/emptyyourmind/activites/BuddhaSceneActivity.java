package com.emptyyourmind.activites;

import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_BG_BUDDHA_CITY;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_BUDDHA;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

public class BuddhaSceneActivity extends MainBaseAbstractActivity
{
	private AnimatedSprite animatedSpriteBuddha;

	@Override
	public void onLoadResources()
	{
		super.onLoadResources();
		textureSceneSpecificObject = new Texture(1024, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		animatedTextureRegionSceneSpecificObject = TextureRegionFactory.createTiledFromAsset(textureSceneSpecificObject, this, SPRITE_BUDDHA, 0, 0, 6, 1);
		textureRegionBackground = TextureRegionFactory.createFromAsset(textureAutoParallaxBackground, this, SPRITE_BG_BUDDHA_CITY, 0, 0);
		mEngine.getTextureManager().loadTextures(textureSceneSpecificObject);
	}

	@Override
	protected IEntity getAnimatedSprite()
	{
		animatedSpriteBuddha = new AnimatedSprite(0, 0, animatedTextureRegionSceneSpecificObject);
		animatedSpriteBuddha.animate(new long[]{5000L, 6000L, 7000L, 8000L, 9000L, 8000L}, true);
		return animatedSpriteBuddha;
	}

	
}
