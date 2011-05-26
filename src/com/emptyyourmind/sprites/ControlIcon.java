package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class ControlIcon extends AnimatedSprite
{
	private String name;
	
	public ControlIcon(float pX, float pY, TiledTextureRegion pTiledTextureRegion, String name)
	{
		super(pX, pY, pTiledTextureRegion);
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
