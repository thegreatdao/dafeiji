package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public abstract class JetMainMenuItem extends AnimatedSprite implements OnAreaTouchListener
{
	private boolean on;
	
	public JetMainMenuItem(float pX, float pY, TiledTextureRegion pTiledTextureRegion)
	{
		super(pX, pY, pTiledTextureRegion);
	}

	@Override
	public void onAreaTouch()
	{
		if(on)
		{
			setCurrentTileIndex(0);
		}
		else
		{
			setCurrentTileIndex(1);
		}
		on = !on;
	}

	public boolean isOn()
	{
		return on;
	}

	public void setOn(boolean on)
	{
		this.on = on;
	}

}
