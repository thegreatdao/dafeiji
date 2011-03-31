package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class ControlIcon extends Sprite
{
	private String name;
	
	public ControlIcon(float pX, float pY, TextureRegion pTextureRegion, String name)
	{
		super(pX, pY, pTextureRegion);
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
