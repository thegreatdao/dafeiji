package com.emptyyourmind;

import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.path.Direction;

/**
 * @author Self-Less
 *
 */
public class SingleHeadedJet extends Jet
{

	public SingleHeadedJet(float pX, float pY, TextureRegion textureRegion, Direction direction, int[] indexes)
	{
		super(pX, pY, textureRegion, direction, indexes);
	}

	@Override
	public void moveTo(int row, int column)
	{
		
	}

}
