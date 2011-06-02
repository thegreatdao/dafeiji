package com.emptyyourmind.activites;

import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_BG_SKULL_CITY;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_SKULL;
import static com.emptyyourmind.utils.JetStrategyUtil.SPRITE_SKULL_STAR;

import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

public class SkullSceneActivity extends MainBaseAbstractActivity
{

	@Override
	public void onLoadResources()
	{
		super.onLoadResources();
		textureRandomObject = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureSceneSpecificObject = new Texture(512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		animatedTextureRegionSceneSpecificObject = TextureRegionFactory.createTiledFromAsset(textureSceneSpecificObject, this, SPRITE_SKULL, 0, 0, 5, 1);
		textureRegionBackground = TextureRegionFactory.createFromAsset(textureAutoParallaxBackground, this, SPRITE_BG_SKULL_CITY, 0, 0);
		textureRegionRandomObject = TextureRegionFactory.createFromAsset(textureRandomObject, this, SPRITE_SKULL_STAR, 0, 0);
		mEngine.getTextureManager().loadTextures(textureSceneSpecificObject, textureRandomObject);
	}

	@Override
	protected IEntity getAnimatedSprite()
	{
		AnimatedSprite animatedSpriteSkull = new AnimatedSprite(390, 140, animatedTextureRegionSceneSpecificObject);
		animatedSpriteSkull.animate(new long[]{6000L, 7000L, 8000L, 9000L, 8000L}, true);
		return animatedSpriteSkull;
	}

}
