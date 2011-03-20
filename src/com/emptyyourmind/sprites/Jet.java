package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.path.Direction;

/**
 * @author Self-Less
 *
 */
public class Jet extends Sprite
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

	public void setReferencePoints(int[] indexes)
	{
		this.indexes = indexes;
	}
	
	public int[] getReferencePoints()
	{
		return indexes;
	}
	
	public Path moveTo(int x, int y)
	{
		
		return null;
	}
	
	public int[] getDimension(int cellSideLength)
	{
		int[] dimension = new int[2];
		dimension[0] = (int)getWidth() / cellSideLength;
		dimension[1] = (int)getHeight() / cellSideLength;
		return dimension;
	}
}
