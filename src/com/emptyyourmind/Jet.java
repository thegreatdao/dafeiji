package com.emptyyourmind;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.path.Direction;

/**
 * @author Self-Less
 *
 */
public abstract class Jet extends Sprite
{
	private Direction direction;
	private int[] indexes;
	
	public Jet(float pX, float pY, TextureRegion textureRegion, Direction direction, int[] indexes)
	{
		super(pX, pY, textureRegion);
		this.direction = direction;
		this.indexes = indexes;
	}
	
	public Direction getDirection()
	{
		return direction;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}

	public int[] getLeftTopCorner(int cameraWidth, int cameraHeight, int cellWidth)
	{
		float x = getX();
		float y = getY();
		
		return null;
	}
	
	public void setReferencePoints(int[] indexes)
	{
		this.indexes = indexes;
	}
	
	public int[] getReferencePoints()
	{
		return indexes;
	}
	
	public abstract void moveTo(int row, int column);
	
}
