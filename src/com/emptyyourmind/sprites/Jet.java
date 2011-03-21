package com.emptyyourmind.sprites;

import org.anddev.andengine.entity.modifier.IEntityModifier;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.modifier.RotationModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

/**
 * @author Self-Less
 *
 */
public class Jet extends Sprite
{
	public static final int[] JET54_REFERENCE_POINT_UP = new int[]{2, 0};
	public static final int[] JET54_REFERENCE_POINT_LEFT = new int[]{3, 2};
	public static final int[] JET54_REFERENCE_POINT_DOWN = new int[]{2, 3};
	public static final int[] JET54_REFERENCE_POINT_RIGHT = new int[]{0, 2};
	
	private int[] indexes;
	private int cellSideLength;
	private int numOfHorCells;
	private int numOfVerCells;

	public Jet(float pX, float pY, TextureRegion textureRegion, int[] indexes, int cellSideLength, int numOfHorCells, int numOfVerCells)
	{
		super(pX, pY, textureRegion);		
		this.indexes = indexes;
		this.cellSideLength = cellSideLength;
		this.numOfHorCells = numOfHorCells;
		this.numOfVerCells = numOfVerCells;
		if(indexes == null)
		{
			throw new RuntimeException("error creating " + Jet.class.getName() + " You will have to specify the reference point");
		}
	}
	
	public void setReferencePoints(int[] indexes)
	{
		this.indexes = indexes;
	}
	
	public int[] getReferencePoints()
	{
		return indexes;
	}
	
	public IEntityModifier moveTo(float x, float y)
	{
		int[] target = findTargetCellCoordinates(x, y, numOfHorCells, numOfVerCells, cellSideLength);
		int[] head = getHeadCoordinates();
		int b = Math.abs(target[0] - head[0]);
		int a = Math.abs(target[1] - head[1]);
		double degree = Math.toDegrees(Math.atan(b / (double)a));
		final Path path = new Path(2).to(300, 240).to(0, 0);
		return new SequenceEntityModifier(new RotationModifier(1,0, (float) -degree), new PathModifier(3f, path),new RotationModifier(1,(float) -degree, 0));
	}
	
	private int[] findTargetCellCoordinates(float clickedX, float clickedY, int numOfHorCells, int numOfVerCells, int cellSideLength)
	{
		int targetX = 0;
		int targetY = 0;
		for(int i = 0; i < numOfHorCells; i++)
		{
			targetX = i * cellSideLength;
			if(targetX <= clickedX && targetX + cellSideLength > clickedX)
			{
				i = numOfHorCells;
			}
		}
		for(int i = 0; i < numOfVerCells; i ++)
		{
			targetY = i * cellSideLength;
			if(targetY <= clickedY && targetY + cellSideLength > clickedY)
			{
				i = numOfVerCells;
			}
		}
		return new int[]{targetX, targetY};
	}
	
	public int[] getHeadCoordinates()
	{
		return new int[]{(int)getX() + indexes[0] * cellSideLength, (int)getY() + indexes[1] * cellSideLength};
	}
}
